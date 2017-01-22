package com.sk.controller.admin;

import com.sk.model.Dish;
import com.sk.service.DishCategoryService;
import com.sk.service.DishService;
import com.sk.service.IngredientService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.stream.Collectors;

@Controller
public class DishController {

    @Resource(name = "dishService")
    private DishService dishService;

    @Resource(name = "dishCategoryService")
    private DishCategoryService dishCategoryService;

    @Resource(name = "ingredientService")
    private IngredientService ingredientService;

    @RequestMapping(value = "/dish/{id}/photo", method = RequestMethod.GET)
    @ResponseBody
    public byte[] dishPhoto(@PathVariable("id") int id) {
        return dishService.findById(id).getPhoto();
    }

    @RequestMapping(value = "/admin/dish", method = RequestMethod.GET)
    public ModelAndView showAll(ModelAndView modelAndView) {
        modelAndView.setViewName("admin/dish/dish.html");
        modelAndView.addObject("dishList", dishService.findAllActive());
        modelAndView.addObject("newDish", new Dish());
        return modelAndView;
    }

    @RequestMapping(value = "/admin/dish/remove", method = RequestMethod.GET)
    public String remove(@RequestParam(value = "id") int id) {
        dishService.remove(dishService.findById(id));
        return "redirect:/admin/dish";
    }

    @RequestMapping(value = "/admin/dish/create", method = RequestMethod.GET)
    public ModelAndView create(ModelAndView modelAndView) {
        modelAndView.addObject("dishCategories", dishCategoryService.findAll());
        modelAndView.addObject("ingredients", ingredientService.findAll());
        modelAndView.addObject("newDish", new Dish());
        modelAndView.setViewName("/admin/dish/createDish.html");
        return modelAndView;
    }

    @RequestMapping(value = "/admin/dish/create", method = RequestMethod.POST)
    public ModelAndView createSave(ModelAndView modelAndView,
                                   HttpServletRequest request,
                                   @Valid @ModelAttribute("newDish") Dish dish,
                                   BindingResult result) {

        if (result.hasErrors()) {
            modelAndView.setViewName("/admin/dish/createDish.html");
            modelAndView.addObject("ingredients", ingredientService.findAll());
            modelAndView.addObject("dishCategories", dishCategoryService.findAll());
            return modelAndView;
        }
        dishService.add(dish);
        modelAndView.setViewName("redirect:/admin/dish");
        return modelAndView;
    }

    @Singleton
    @RequestMapping(value = "/admin/dish/edit", method = RequestMethod.GET)
    public ModelAndView edit(ModelAndView modelAndView, @RequestParam("id") int id) {
        modelAndView.setViewName("/admin/dish/editDish.html");
        modelAndView.addObject("editableDish", dishService.findById(id));
        modelAndView.addObject("dishCategories", dishCategoryService.findAll());
        modelAndView.addObject("ingredients", ingredientService.findAll());
        return modelAndView;
    }

    @RequestMapping(value = "/admin/dish/edit", method = RequestMethod.POST)
    public ModelAndView editSave(ModelAndView modelAndView,
                                 @Valid @ModelAttribute("editableDish") Dish dish,
                                 BindingResult result) {

//        dish.setCategory(dish.getCategory());

        if (result.hasErrors()) {
            modelAndView.addObject("editableDish", dish);
            modelAndView.addObject("dishCategories", dishCategoryService.findAll());
            modelAndView.addObject("ingredients", ingredientService.findAll());
            modelAndView.setViewName("/admin/dish/editDishError.html");
            return modelAndView;
        }

        dishService.update(dish);
        modelAndView.setViewName("redirect:/admin/dish");
        return modelAndView;
    }


}
