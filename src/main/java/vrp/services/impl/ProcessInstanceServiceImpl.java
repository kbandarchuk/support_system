package vrp.services.impl;

import org.apache.commons.lang.StringUtils;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vrp.dto.CommentDTO;
import vrp.dto.UserRequestDTO;
import vrp.services.CustomProcessService;
import java.util.*;

@Service
public class ProcessInstanceServiceImpl implements CustomProcessService {

    private final RuntimeService runtimeService;
    private static final String PROCESS_DEFINITION_KEY = "SupportSystem";

    @Autowired
    public ProcessInstanceServiceImpl( final RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    @Override
    public ProcessInstance startProcessInstance(final UserRequestDTO userRequestDTO) {
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(PROCESS_DEFINITION_KEY, getProcessVariables(userRequestDTO));
        setCommentsAsProcessVariables(userRequestDTO.getComment(), processInstance);
        return processInstance;
    }

    protected Map<String, Object> getProcessVariables(final UserRequestDTO userRequestDTO){
        final Map<String, Object> variables = new HashMap<>();
        variables.put("requestType", userRequestDTO.getRequestType());
        variables.put("theme", userRequestDTO.getTheme());
        variables.put("description", userRequestDTO.getDescription());
        variables.put("importance", userRequestDTO.getImportance());
        return variables;
    }

    protected void setCommentsAsProcessVariables(final String comment, final ProcessInstance processInstance){

        if(StringUtils.isEmpty(comment)){
            return;
        }

        final String userName = (String) runtimeService.getVariable(processInstance.getId(), "initiator");
        runtimeService.setVariable( processInstance.getId()
                                  , "comments"
                                  , Arrays.asList(new CommentDTO( comment
                                                                , userName
                                                                , new Date())));
    }
}
