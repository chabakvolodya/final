package com.sk.controller.admin;

import com.sk.model.Employee;
import com.sk.service.EmployeeService;
import com.sk.service.PositionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
public class EmployeeController {

    private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

    @Resource(name = "employeeService")
    private EmployeeService employeeService;

    @Resource(name = "positionService")
    private PositionService positionService;

    @RequestMapping(value = "/employee/{id}/photo", method = RequestMethod.GET)
    @ResponseBody
    public byte[] employeePhoto(@PathVariable("id") int id) {
        return employeeService.findById(id).getPhoto();
    }

    @RequestMapping(value = "/admin/employee", method = RequestMethod.GET)
    public ModelAndView showAll(ModelAndView modelAndView) {
        modelAndView.setViewName("admin/employee/employee.html");
        modelAndView.addObject("employees", employeeService.findAll());
        return modelAndView;
    }

    @RequestMapping(value = "/admin/employee/edit", method = RequestMethod.GET, params = "id")
    public ModelAndView edit(ModelAndView modelAndView,
                             @RequestParam("id") int id) {

        modelAndView.addObject("positions", positionService.findAll());
        modelAndView.addObject("employee", employeeService.findById(id));
        modelAndView.setViewName("admin/employee/employeeEdit.html");
        return modelAndView;
    }

    @RequestMapping(value = "/admin/employee/edit", method = RequestMethod.POST)
    public ModelAndView editSave(ModelAndView modelAndView,
                                 @Valid @ModelAttribute("employee") Employee employee,
                                 BindingResult result) {

        if (result.hasErrors()) {
            modelAndView.addObject("positions", positionService.findAll());
            modelAndView.addObject("employee", employee);
            modelAndView.setViewName("admin/employee/employeeEdit.html");
            return modelAndView;
        }

        modelAndView.addObject("positions", positionService.findAll());
        modelAndView.setViewName("admin/employee/employeeEdit.html");
        modelAndView.addObject("employee", employee);
        modelAndView.addObject("msg", "Saved");

        employeeService.update(employee);

        return modelAndView;
    }


    @RequestMapping(value = "/admin/employee/create", method = RequestMethod.GET)
    public ModelAndView create(ModelAndView modelAndView) {
        modelAndView.setViewName("admin/employee/employeeCreate.html");
        modelAndView.addObject("positions", positionService.findAll());
        modelAndView.addObject("employee", new Employee());
        return modelAndView;
    }

    @RequestMapping(value = "/admin/employee/create", method = RequestMethod.POST)
    public ModelAndView saveNew(ModelAndView modelAndView,
                                @Valid @ModelAttribute("employee") Employee employee,
                                BindingResult result) {

        log.debug("employee from request: {}", employee);

        if (result.hasErrors()) {
            modelAndView.addObject("positions", positionService.findAll());
            modelAndView.addObject("employee", employee);
            modelAndView.setViewName("admin/employee/employeeCreate.html");
            return modelAndView;
        }
        employeeService.add(employee);
        modelAndView.setViewName("redirect: ../employee");
        return modelAndView;
    }

}
