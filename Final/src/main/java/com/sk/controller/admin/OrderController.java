package com.sk.controller.admin;

import com.sk.service.BoardService;
import com.sk.service.EmployeeService;
import com.sk.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Controller
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    private static final String WAITER = "waiter";

    @Resource(name = "orderService")
    private OrderService orderService;

    @Resource(name = "employeeService")
    private EmployeeService employeeService;

    @Resource(name = "boardServiceImpl")
    private BoardService boardService;

    @RequestMapping(value = "/admin/orders", method = RequestMethod.GET)
    public ModelAndView showAll(ModelAndView modelAndView) {
        modelAndView.addObject("orders", orderService.findAll());
        modelAndView.addObject("waiters", employeeService.findByPositionDescription(WAITER));
        modelAndView.addObject("tables", boardService.findAll());
        modelAndView.setViewName("admin/order/orders.html");
        return modelAndView;
    }

    @RequestMapping(value = "/admin/orders/view", method = RequestMethod.GET, params = "id")
    public ModelAndView findByName(ModelAndView modelAndView,
                                   @RequestParam(value = "id") int id) {
        log.debug("requested id: [{}]", id);
        modelAndView.setViewName("admin/order/orderView.html");
        modelAndView.addObject("order", orderService.findById(id));
        return modelAndView;
    }

    @RequestMapping(value = "/admin/orders", method = RequestMethod.GET,
            params = {"dateFrom", "dateTo", "waiterId", "tableId"})
    public ModelAndView showFiltered(ModelAndView modelAndView,
                                     @RequestParam(value = "dateFrom", required = false) LocalDateTime dateFrom,
                                     @RequestParam(value = "dateTo", required = false) LocalDateTime dateTo,
                                     @RequestParam(value = "waiterId", required = false, defaultValue = "0") int waiterId,
                                     @RequestParam(value = "tableId", required = false, defaultValue = "0") int tableId) {

        log.debug("Date from: {}; date to: {}; waiterId: {}; tableId: {}",
                new Object[]{dateFrom, dateTo, waiterId, tableId});

        modelAndView.addObject("orders", orderService.findByCriteria(dateFrom, dateTo, waiterId, tableId));


        modelAndView.addObject("waiters", employeeService.findByPositionDescription(WAITER));
        modelAndView.addObject("tables", boardService.findAll());
        modelAndView.setViewName("admin/order/orders.html");
        return modelAndView;
    }


//
//    @RequestMapping(value = "/admin/warehouse/delete", method = RequestMethod.GET, params = "id")
//    public ModelAndView delete(ModelAndView modelAndView,
//                               @RequestParam(value = "id") int id) {
//        log.debug("id to delete: [{}]", id);
//        ingredientService.deleteById(id);
//        modelAndView.setViewName("redirect: ../warehouse");
//        return modelAndView;
//    }
//
//    @RequestMapping(value = "/admin/warehouse/activate", method = RequestMethod.GET, params = "id")
//    public ModelAndView activate(ModelAndView modelAndView,
//                                 @RequestParam(value = "id") int id) {
//        log.debug("id to activate: [{}]", id);
//        ingredientService.activateById(id);
//        modelAndView.setViewName("redirect: ../warehouse");
//        return modelAndView;
//    }
//
//    @RequestMapping(value = "/admin/warehouse/edit", method = RequestMethod.GET, params = "id")
//    public ModelAndView edit(ModelAndView modelAndView,
//                             @RequestParam("id") int id) {
//        modelAndView.addObject("ingredient", ingredientService.findById(id));
//        modelAndView.setViewName("admin/warehouse/ingredientEdit.html");
//        return modelAndView;
//    }
//
//    @RequestMapping(value = "/admin/warehouse/edit", method = RequestMethod.POST)
//    public ModelAndView editSave(ModelAndView modelAndView,
//                                 @Valid @ModelAttribute("ingredient") Ingredient ingredient,
//                                 BindingResult result) {
//
//        log.debug("incoming ingredient: [{}]", ingredient);
//        log.debug("binding result: [{}]", result);
//
//        if (result.hasErrors()) {
//            modelAndView.setViewName("admin/warehouse/ingredientEdit.html");
//            return modelAndView;
//        }
//
//        ingredientService.update(ingredient);
//        modelAndView.addObject("msg", "Saved");
//
//        modelAndView.setViewName("admin/warehouse/ingredientEdit.html");
//        return modelAndView;
//    }
//
//    @RequestMapping(value = "/admin/warehouse/create", method = RequestMethod.GET)
//    public ModelAndView create(ModelAndView modelAndView) {
//        modelAndView.addObject("ingredient", new Ingredient());
//        modelAndView.setViewName("admin/warehouse/ingredientCreate.html");
//        return modelAndView;
//    }
//
//    @RequestMapping(value = "/admin/warehouse/create", method = RequestMethod.POST)
//    public ModelAndView createSave(ModelAndView modelAndView,
//                                   @Valid @ModelAttribute("ingredient") Ingredient ingredient,
//                                   BindingResult result) {
//
//        log.debug("incoming ingredient: [{}]", ingredient);
//        log.debug("binding result: [{}]", result);
//        log.debug("field errors: [{}]", result.getFieldErrors());
//
//        if (result.hasErrors()) {
//            modelAndView.setViewName("admin/warehouse/ingredientCreate.html");
//            return modelAndView;
//        }
//
//        ingredientService.insert(ingredient);
//        modelAndView.addObject("msg", "Created");
//
//        modelAndView.setViewName("admin/warehouse/ingredientCreate.html");
//        return modelAndView;
//    }
//
//    @InitBinder
//    public void changeDefaultErrorMessage(WebDataBinder binder) {
//        binder.setBindingErrorProcessor(new DefaultBindingErrorProcessor() {
//
//            private static final String QUANTITY = "quantity";
//
//            @Override
//            public void processPropertyAccessException(PropertyAccessException ex,
//                                                       BindingResult bindingResult) {
//                if (ex.getPropertyName().equals(QUANTITY)) {
//                    FieldError fieldError = new FieldError(bindingResult.getObjectName(),
//                            QUANTITY, ex.getValue() + " - not valid number");
//                    bindingResult.addError(fieldError);
//                } else {
//                    super.processPropertyAccessException(ex, bindingResult);
//                }
//            }
//        });
//    }

}
