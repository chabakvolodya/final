package com.sk.controller.admin;

import com.sk.model.Dish;
import com.sk.model.Menu;
import com.sk.service.DishService;
import com.sk.service.MenuService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MenuController {

    @Resource(name = "menuService")
    private MenuService menuService;

    @Resource(name = "dishService")
    private DishService dishService;

    @RequestMapping(value = {"/admin/menu", "/admin"}, method = RequestMethod.GET)
    public ModelAndView showAll(ModelAndView modelAndView) {
        modelAndView.setViewName("admin/menu/menu.html");
        modelAndView.addObject("menuList", menuService.findAll());
        modelAndView.addObject("newMenu", new Menu());
        return modelAndView;
    }

    @RequestMapping(value = "/admin/menu/remove", method = RequestMethod.GET)
    public String remove(@RequestParam(value = "id") int id) {
        menuService.remove(new Menu(id, null, null));
        return "redirect:/admin/menu";
    }

    @RequestMapping(value = "/admin/menu/create", method = RequestMethod.POST)
    public ModelAndView create(@Valid @ModelAttribute("newMenu") Menu menu,
                               BindingResult result, ModelAndView modelAndView) {

        if (result.hasErrors()) {
            modelAndView.addObject("menuList", menuService.findAll());
            modelAndView.setViewName("admin/menu/menu.html");
            return modelAndView;
        }
        menuService.add(menu);
        modelAndView.setViewName("redirect:/admin/menu");
        return modelAndView;
    }

    @RequestMapping(value = "/admin/menu/edit", method = RequestMethod.GET)
    public ModelAndView edit(ModelAndView modelAndView, @RequestParam("id") int id) {
        modelAndView.setViewName("/admin/menu/editMenu.html");

        Menu editableMenu = menuService.findById(id);
        modelAndView.addObject("editableMenu", editableMenu);


        List<Dish> availableDishes = dishService.findAll();
        if (availableDishes.size() > 0) {
            availableDishes.removeIf(d -> editableMenu.getDishes().contains(d));
        }

        modelAndView.addObject("availableDishes", availableDishes);

        return modelAndView;
    }


    @RequestMapping(value = "/admin/menu/{id}/removedish", method = RequestMethod.POST)
    public ModelAndView removeDishFromMenu(ModelAndView modelAndView,
                                           @PathVariable("id") int menuId,
                                           @RequestParam(value = "dish", required = false) List<Integer> dishIdToDelete) {

        if (dishIdToDelete != null) {
            Menu menu = menuService.findById(menuId);
            menu.getDishes()
                    .removeIf(dish -> dishIdToDelete.contains(dish.getId()));
            menuService.update(menu);
        }

        modelAndView.setViewName("redirect:/admin/menu/edit?id=" + menuId);
        return modelAndView;
    }

    @RequestMapping(value = "/admin/menu/{id}/adddish", method = RequestMethod.POST)
    public ModelAndView addDishToMenu(ModelAndView modelAndView,
                                      @PathVariable("id") int menuId,
                                      @RequestParam(value = "dish", required = false) List<Dish> dishIdToAddInput) {

        ArrayList<Dish> dishIdToAdd = new ArrayList<>();
        dishIdToAdd.addAll(dishIdToAddInput);

        if (dishIdToAdd != null) {
            Menu menu = menuService.findById(menuId);
            menu.getDishes().addAll(dishIdToAdd);
            menuService.update(menu);
        }

        modelAndView.setViewName("redirect:/admin/menu/edit?id=" + menuId);
        return modelAndView;
    }
}
