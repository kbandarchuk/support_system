package vrp.services.impl;

import org.apache.commons.lang.StringUtils;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vrp.dto.CommentDTO;
import vrp.services.CustomTaskService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CustomTaskServiceImpl implements CustomTaskService {

    private final TaskService taskService;
    private final RuntimeService runtimeService;
    private final HistoryService historyService;

    @Autowired
    public CustomTaskServiceImpl( final TaskService taskService
                                , final HistoryService historyService
                                , final RuntimeService runtimeService) {
        this.taskService = taskService;
        this.historyService = historyService;
        this.runtimeService = runtimeService;
    }

    @Override
    public void completeTask(final String taskId, final Map<String, Object> taskVariables) {

        taskService.complete(taskId, getVariables(taskId, taskVariables));
        setCommentsAsProcessVariable((String) taskVariables.get("comment"), taskId);
    }

    @Override
    public Map<String, Object> getTaskVariables(final String taskId) {
        return taskService.getVariables(taskId);
    }

    protected Map<String, Object> getVariables(final String taskId, final Map<String, Object> taskVariables){
        final Map<String, Object> variables = taskService.getVariables(taskId);
        variables.put("status", taskVariables.get("status"));

        return variables;
    }

    protected void setCommentsAsProcessVariable(final String comment, final String taskId ){

        if(StringUtils.isEmpty(comment)){
            return;
        }

        final String processInstanceId = historyService.createHistoricTaskInstanceQuery()
                                                       .taskId(taskId)
                                                       .singleResult()
                                                       .getProcessInstanceId();
        final Object commentsVariable = runtimeService.getVariable(processInstanceId, "comments");
        final List<CommentDTO> comments  = commentsVariable != null
                                                             ? new ArrayList<>((List<CommentDTO>)commentsVariable)
                                                             : new ArrayList<>();
        comments.add(new CommentDTO( comment
                                   , historyService.createHistoricTaskInstanceQuery()
                                                   .taskId(taskId)
                                                   .singleResult()
                                                   .getAssignee()
                                   , new Date()));
        runtimeService.setVariable(processInstanceId, "comments", comments);
    }
}
