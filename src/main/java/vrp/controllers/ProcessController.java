package vrp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import vrp.dto.UserRequestDTO;
import vrp.services.CustomProcessService;
import vrp.services.CustomTaskService;
import java.util.*;

@Controller
public class ProcessController {

    private final CustomProcessService customProcessService;
    private final CustomTaskService customTaskService;

    @Autowired
    public ProcessController( final CustomProcessService customProcessService
                            , final CustomTaskService customTaskService) {
        this.customProcessService = customProcessService;
        this.customTaskService = customTaskService;
    }

    @RequestMapping(value = "/process/start_process", method = RequestMethod.POST)
    public String startProcessInstance( @RequestParam(name = "requestType") final String requestType
                                      , @RequestParam(name = "theme") final String theme
                                      , @RequestParam(name = "description") final String description
                                      , @RequestParam(name = "importance") final String importance
                                      , @RequestParam(name = "comment") final String comment) {

       customProcessService.startProcessInstance(new UserRequestDTO( requestType
                                                                   , theme
                                                                   , description
                                                                   , importance
                                                                   , comment));
        return "redirect:/app/tasklist/default/#";
    }

    @RequestMapping(value = "/process/view_new_task", method = RequestMethod.GET)
    public ModelAndView newTaskView(@RequestParam("taskId") final String taskId) {

        final ModelAndView mav = new ModelAndView();
        mav.setViewName("support_new_task");
        mav.addObject("variables", customTaskService.getTaskVariables(taskId));
        mav.addObject("taskId", taskId);

        return mav;
    }

    @RequestMapping(value = "/process/view_pause_task", method = RequestMethod.GET)
    public ModelAndView pauseTaskView(@RequestParam("taskId") final String taskId) {

        final ModelAndView mav = new ModelAndView();
        mav.setViewName("support_pause_task");
        mav.addObject("variables", customTaskService.getTaskVariables(taskId));
        mav.addObject("taskId", taskId);

        return mav;
    }

    @RequestMapping(value = "/process/additional_info", method = RequestMethod.GET)
    public ModelAndView addInfo(@RequestParam("taskId") final String taskId) {

        final ModelAndView mav = new ModelAndView();
        mav.setViewName("additional_info");
        mav.addObject("variables", customTaskService.getTaskVariables(taskId));
        mav.addObject("taskId", taskId);

        return mav;
    }

    @RequestMapping(value = "/process/complete_task", method = RequestMethod.POST)
    public String completeTask( @RequestParam("comment") final String comment
                              , @RequestParam(value = "status", required = false) final String status
                              , @RequestParam("taskId") final String taskId){

        final Map<String, Object> taskVariables = new HashMap<>();
        taskVariables.put("status", status);
        taskVariables.put("comment", comment);
        customTaskService.completeTask(taskId, taskVariables);

        return "redirect:/app/tasklist/default/#";
    }

    @RequestMapping(value = "/process/close_task", method = RequestMethod.GET)
    public ModelAndView closeTask(@RequestParam("taskId") final String taskId) {

        final ModelAndView mav = new ModelAndView();
        mav.setViewName("support_close_task");
        mav.addObject("variables", customTaskService.getTaskVariables(taskId));
        mav.addObject("taskId", taskId);

        return mav;
    }

}
