package com.sk.controller.general;

import com.sk.model.Dish;
import com.sk.service.DishService;
import com.sk.service.EmployeeService;
import com.sk.service.MenuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.stream.Collectors;

@Controller
public class IndexController {

    @Resource(name = "menuService")
    private MenuService menuService;

    @Resource(name = "dishService")
    private DishService dishService;

    @Resource(name = "employeeService")
    private EmployeeService employeeService;

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public ModelAndView index(ModelAndView modelAndView) {
        modelAndView.setViewName("index.html");
        modelAndView.addObject("menus", menuService.findAll());
        modelAndView.addObject("dish", new Dish());
        return modelAndView;
    }

    @RequestMapping(value = "/dish", method = RequestMethod.GET)
    public ModelAndView dishDetail(@RequestParam("id") int id, ModelAndView modelAndView) {
        modelAndView.setViewName("dishDetail.html");
        modelAndView.addObject("dish", dishService.findById(id));
        return modelAndView;
    }

    @RequestMapping(value = "/dish/findByName", method = RequestMethod.POST)
    public ModelAndView findDishByName(ModelAndView modelAndView, @ModelAttribute Dish dish) {
        modelAndView.setViewName("dishFindResults.html");
        modelAndView.addObject("dishes", dishService.findByName(dish.getName()));
        return modelAndView;
    }

    @RequestMapping(value = "/scheme", method = RequestMethod.GET)
    public String restaurantScheme() {
        return "restaurantScheme.html";
    }

    @RequestMapping(value = "/contacts", method = RequestMethod.GET)
    public String contacts() {
        return "contacts.html";
    }

    @RequestMapping(value = "/team", method = RequestMethod.GET)
    public ModelAndView team(ModelAndView modelAndView) {
        modelAndView.setViewName("team.html");
        modelAndView.addObject("employees",
                employeeService.findAll().stream()
                        .filter(emp -> emp.getPosition().getDescription().equals("waiter"))
                        .collect(Collectors.toList()));
        return modelAndView;
    }

    @RequestMapping("/login")
    public String login() {
        return "login.html";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login.html";
    }
}
