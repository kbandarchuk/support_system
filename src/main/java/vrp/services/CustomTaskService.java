package vrp.services;

import java.util.Map;

public interface CustomTaskService {
    void completeTask(String taskId, Map<String, Object> taskVariables);
    Map<String, Object> getTaskVariables(String taskId);
}
