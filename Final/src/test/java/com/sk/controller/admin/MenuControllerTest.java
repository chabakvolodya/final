package com.sk.controller.admin;

import com.sk.config.TestControllerConfig;
import com.sk.controller.general.IndexControllerTest;
import com.sk.model.Dish;
import com.sk.model.Menu;
import com.sk.service.DishService;
import com.sk.service.MenuService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.servlet.View;

import javax.annotation.Resource;

import java.util.ArrayList;

import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestControllerConfig.class)
@WebAppConfiguration
public class MenuControllerTest {

    @Mock private DishService dishServiceMock;
    @Mock private MenuService menuService;
    @Mock private View viewMock;
    @InjectMocks private MenuController menuController;

    private MockMvc mockMvc;

    @Resource(name = "conversionService")
    private FormattingConversionService conversionService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(menuController)
                .setSingleView(viewMock)
                .setConversionService(conversionService)
                .build();
    }

    @Test
    public void showAll() throws Exception {
        mockMvc.perform(get("/admin/menu"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("admin/menu/menu.html"))
                .andExpect(model().attributeExists("menuList", "newMenu"));

        verify(menuService, times(1)).findAll();
    }

    @Test
    public void remove() throws Exception {

        doNothing().when(menuService).remove(any(Menu.class));

        mockMvc.perform(get("/admin/menu/remove").param("id", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("redirect:/admin/menu"));

        verify(menuService, times(1)).remove(any(Menu.class));
    }

    @Test
    public void create() throws Exception {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("description", "testDescription");

        mockMvc.perform(post("/admin/menu/create")
                .params(paramMap))
                .andDo(print())
                .andExpect(model().hasNoErrors())
                .andExpect(view().name("redirect:/admin/menu"));

        verify(menuService, times(1)).add(any(Menu.class));
    }

    @Test
    public void createWrongParam() throws Exception {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("description", "");

        mockMvc.perform(post("/admin/menu/create")
                .params(paramMap))
                .andDo(print())
                .andExpect(model().hasErrors())
                .andExpect(model().attributeExists("menuList"))
                .andExpect(view().name("admin/menu/menu.html"));

        verify(menuService, times(0)).add(any(Menu.class));
        verify(menuService, times(1)).findAll();
    }

    @Test
    public void edit() throws Exception {

        Menu menuTest = new IndexControllerTest.MenuBuilder()
                .id(1)
                .name("testMenu")
                .dishes(asList(new Dish()))
                .build();
        when(menuService.findById(anyInt())).thenReturn(menuTest);

        mockMvc.perform(get("/admin/menu/edit").param("id", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("editableMenu", "availableDishes"))
                .andExpect(view().name("/admin/menu/editMenu.html"));

        verify(menuService, times(1)).findById(anyInt());
    }

    @Test
    public void removeDishFromMenu() throws Exception {

        String menuId = "1";

        Menu menuTest = new IndexControllerTest.MenuBuilder()
                .id(1)
                .name("testMenu")
                .dishes(asList(new Dish()))
                .build();

        when(menuService.findById(anyInt())).thenReturn(menuTest);

        mockMvc.perform(post("/admin/menu/{id}/removedish", menuId).param("dish", "1", "2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("redirect:/admin/menu/edit?id=" + menuId));

        verify(menuService, times(1)).update(any(Menu.class));
    }

    @Test
    public void addDishToMenu() throws Exception {

        String menuId = "1";

        Menu menuTest = new IndexControllerTest.MenuBuilder()
                .id(1)
                .name("testMenu")
                .dishes(new ArrayList<>())
                .build();
        when(menuService.findById(anyInt())).thenReturn(menuTest);

        mockMvc.perform(post("/admin/menu/{id}/adddish", menuId)
                .param("dish", "1").param("dish", "2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("redirect:/admin/menu/edit?id=" + menuId));

        verify(menuService, times(1)).update(any(Menu.class));
    }

}