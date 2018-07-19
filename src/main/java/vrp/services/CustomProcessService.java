package vrp.services;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import vrp.dto.UserRequestDTO;

public interface CustomProcessService {
    ProcessInstance startProcessInstance(UserRequestDTO userRequestDTO);
}
