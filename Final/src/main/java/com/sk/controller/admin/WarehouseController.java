package com.sk.controller.admin;

import com.sk.model.Ingredient;
import com.sk.service.IngredientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.PropertyAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DefaultBindingErrorProcessor;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
public class WarehouseController {

    private static final Logger log = LoggerFactory.getLogger(WarehouseController.class);

    @Resource(name = "ingredientService")
    private IngredientService ingredientService;

    @RequestMapping(value = "/admin/warehouse", method = RequestMethod.GET)
    public ModelAndView showAll(ModelAndView modelAndView) {
        modelAndView.setViewName("admin/warehouse/warehouse.html");
        modelAndView.addObject("ingredients", ingredientService.findAll());
        return modelAndView;
    }

    @RequestMapping(value = "/admin/warehouse", method = RequestMethod.GET, params = "find")
    public ModelAndView findByName(ModelAndView modelAndView,
                                   @RequestParam(value = "find") String ingredientName) {
        log.debug("requested name: [{}]", ingredientName);
        modelAndView.setViewName("admin/warehouse/warehouse.html");
        modelAndView.addObject("ingredients", ingredientService.findByName(ingredientName));
        return modelAndView;
    }

    @RequestMapping(value = "/admin/warehouse/delete", method = RequestMethod.GET, params = "id")
    public ModelAndView delete(ModelAndView modelAndView,
                               @RequestParam(value = "id") int id) {
        log.debug("id to delete: [{}]", id);
        ingredientService.deleteById(id);
        modelAndView.setViewName("redirect: ../warehouse");
        return modelAndView;
    }

    @RequestMapping(value = "/admin/warehouse/activate", method = RequestMethod.GET, params = "id")
    public ModelAndView activate(ModelAndView modelAndView,
                                 @RequestParam(value = "id") int id) {
        log.debug("id to activate: [{}]", id);
        ingredientService.activateById(id);
        modelAndView.setViewName("redirect: ../warehouse");
        return modelAndView;
    }

    @RequestMapping(value = "/admin/warehouse/edit", method = RequestMethod.GET, params = "id")
    public ModelAndView edit(ModelAndView modelAndView,
                             @RequestParam("id") int id) {
        modelAndView.addObject("ingredient", ingredientService.findById(id));
        modelAndView.setViewName("admin/warehouse/ingredientEdit.html");
        return modelAndView;
    }

    @RequestMapping(value = "/admin/warehouse/edit", method = RequestMethod.POST)
    public ModelAndView editSave(ModelAndView modelAndView,
                                 @Valid @ModelAttribute("ingredient") Ingredient ingredient,
                                 BindingResult result) {

        log.debug("incoming ingredient: [{}]", ingredient);
        log.debug("binding result: [{}]", result);

        if (result.hasErrors()) {
            modelAndView.setViewName("admin/warehouse/ingredientEdit.html");
            return modelAndView;
        }

        ingredientService.update(ingredient);
        modelAndView.addObject("msg", "Saved");

        modelAndView.setViewName("admin/warehouse/ingredientEdit.html");
        return modelAndView;
    }

    @RequestMapping(value = "/admin/warehouse/create", method = RequestMethod.GET)
    public ModelAndView create(ModelAndView modelAndView) {
        modelAndView.addObject("ingredient", new Ingredient());
        modelAndView.setViewName("admin/warehouse/ingredientCreate.html");
        return modelAndView;
    }

    @RequestMapping(value = "/admin/warehouse/create", method = RequestMethod.POST)
    public ModelAndView createSave(ModelAndView modelAndView,
                                   @Valid @ModelAttribute("ingredient") Ingredient ingredient,
                                   BindingResult result) {

        log.debug("incoming ingredient: [{}]", ingredient);
        log.debug("binding result: [{}]", result);
        log.debug("field errors: [{}]", result.getFieldErrors());

        if (result.hasErrors()) {
            modelAndView.setViewName("admin/warehouse/ingredientCreate.html");
            return modelAndView;
        }

        ingredientService.insert(ingredient);
        modelAndView.addObject("msg", "Created");

        modelAndView.setViewName("admin/warehouse/ingredientCreate.html");
        return modelAndView;
    }

    @InitBinder
    public void changeDefaultErrorMessage(WebDataBinder binder) {
        binder.setBindingErrorProcessor(new DefaultBindingErrorProcessor() {

            private static final String QUANTITY = "quantity";

            @Override
            public void processPropertyAccessException(PropertyAccessException ex,
                                                       BindingResult bindingResult) {
                if (ex.getPropertyName().equals(QUANTITY)) {
                    FieldError fieldError = new FieldError(bindingResult.getObjectName(),
                            QUANTITY, ex.getValue() + " - not valid number");
                    bindingResult.addError(fieldError);
                } else {
                    super.processPropertyAccessException(ex, bindingResult);
                }
            }
        });
    }

}
