package com.sk.controller.admin;

import com.sk.config.TestControllerConfig;
import com.sk.model.Dish;
import com.sk.model.DishCategory;
import com.sk.service.DishCategoryService;
import com.sk.service.DishService;
import com.sk.service.IngredientService;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.servlet.View;

import javax.annotation.Resource;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasProperty;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestControllerConfig.class)
@WebAppConfiguration
public class DishControllerTest {

    @Mock private DishService dishServiceMock;
    @Mock private DishCategoryService dishCategoryServiceMock;
    @Mock private IngredientService ingredientServiceMock;
    @Mock private View mockView;
    @InjectMocks private DishController dishController;

    private MockMvc mockMvc;
    private static final String EMPTY_STRING = "";

    @Resource(name = "conversionService")
    private FormattingConversionService conversionService;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(dishController)
                .setSingleView(mockView)
                .setConversionService(conversionService)
                .build();
    }

    @Test
    public void dishPhoto() throws Exception {
        Dish expectedDish = new DishBuilder()
                .photo(new byte[]{})
                .build();

        when(dishServiceMock.findById(anyInt())).thenReturn(expectedDish);

        mockMvc.perform(get("/dish/{id}/photo", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(content().bytes(expectedDish.getPhoto()));
    }

    @Test
    public void showAll() throws Exception {
        Dish firstDish = new DishBuilder()
                .id(1)
                .name("test1")
                .price(50)
                .build();

        Dish secondDish = new DishBuilder()
                .id(2)
                .name("test2")
                .price(100)
                .build();

        List<Dish> expectedDish = asList(firstDish, secondDish);
        when(dishServiceMock.findAllActive()).thenReturn(expectedDish);

        mockMvc.perform(get("/admin/dish"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/dish/dish.html"))
                .andExpect(model().attribute("dishList", expectedDish));


        verify(dishServiceMock, times(1)).findAllActive();
        verifyNoMoreInteractions(dishServiceMock);
    }

    @Test
    public void remove() throws Exception {
        mockMvc.perform(get("/admin/dish/remove").param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(model().hasNoErrors())
                .andExpect(view().name("redirect:/admin/dish"));

        verify(dishServiceMock, times(1)).remove(any(Dish.class));
        verify(dishServiceMock, times(1)).findById(anyInt());
        verifyNoMoreInteractions(dishServiceMock);
    }

    @Test
    public void create() throws Exception {
        mockMvc.perform(get("/admin/dish/create"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(view().name("/admin/dish/createDish.html"))
                .andExpect(model().attributeExists("dishCategories", "ingredients", "newDish"));

        verify(dishCategoryServiceMock, times(1)).findAll();
        verify(ingredientServiceMock, times(1)).findAll();
    }

    @Test
    public void createSave() throws Exception {

        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("name", "testName");
        paramMap.add("category", "1,testCategory");
        paramMap.add("price", "10");
        paramMap.add("weight", "150");
        paramMap.add("deleted", "false");

        when(dishServiceMock.add(new Dish())).thenReturn(any(Dish.class));

        mockMvc.perform(post("/admin/dish/create")
                .params(paramMap))
                .andDo(print())
                .andExpect(model().hasNoErrors())
                .andExpect(view().name("redirect:/admin/dish"));

        verify(dishServiceMock, times(1)).add(any(Dish.class));
        verifyNoMoreInteractions(dishServiceMock);

    }

    @Test
    public void createSaveWrongParam() throws Exception {

        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("name", "");
        paramMap.add("category", "");
        paramMap.add("price", "10");
        paramMap.add("weight", "-150");
        paramMap.add("deleted", "false");

        mockMvc.perform(post("/admin/dish/create")
                .params(paramMap))
                .andDo(print())
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("newDish", "name", "category", "weight"))
                .andExpect(view().name("/admin/dish/createDish.html"));

        verifyNoMoreInteractions(dishServiceMock);
        verify(ingredientServiceMock, times(1)).findAll();
        verify(dishCategoryServiceMock, times(1)).findAll();

    }

    @Test
    public void edit() throws Exception {
        Dish dishForEdit = new DishBuilder()
                .id(1)
                .category("testCategory")
                .name("dishTestName")
                .price(100D)
                .weight(10)
                .build();

        when(dishServiceMock.findById(anyInt())).thenReturn(dishForEdit);

        mockMvc.perform(get("/admin/dish/edit").param("id", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/dish/editDish.html"))
                .andExpect(model().attributeExists("editableDish", "dishCategories", "ingredients"))
                .andExpect(model().attribute(
                        "editableDish", allOf(
                                hasProperty("id", is(1)),
                                hasProperty("category", hasProperty("description", is("testCategory"))),
                                hasProperty("name", is("dishTestName")))));

        verify(dishServiceMock, times(1)).findById(dishForEdit.getId());
        verify(ingredientServiceMock, times(1)).findAll();
        verify(dishCategoryServiceMock, times(1)).findAll();

    }

    @Test
    public void editSave() throws Exception {

        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("id", "1");
        paramMap.add("name", "dishTestName");
        paramMap.add("category", "1,testCategory");
        paramMap.add("price", "10");
        paramMap.add("weight", "150");
        paramMap.add("deleted", "false");

        doNothing().when(dishServiceMock).update(any(Dish.class));

        mockMvc.perform(post("/admin/dish/edit").params(paramMap))
                .andDo(print())
                .andExpect(view().name("redirect:/admin/dish"))
                .andExpect(model().attribute(
                        "editableDish", allOf(
                                hasProperty("id", is(1)),
                                hasProperty("name", is("dishTestName")),
                                hasProperty("category", allOf(hasProperty("id", is(1)), hasProperty("description", is("testCategory")))),
                                hasProperty("price", is(Money.of(CurrencyUnit.USD, 10))),
                                hasProperty("weight", is(150)),
                                hasProperty("deleted", is(false)))));

        verify(dishServiceMock, times(1)).update(any(Dish.class));

    }

    @Test
    public void editSaveWrongParam() throws Exception {

        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("id", "1");
        paramMap.add("name", EMPTY_STRING);
        paramMap.add("category", "1,testCategory");
        paramMap.add("price", "-10");
        paramMap.add("weight", "-150");
        paramMap.add("deleted", "false");

        int expectedErrorCount = 4;

        doNothing().when(dishServiceMock).update(any(Dish.class));

        mockMvc.perform(post("/admin/dish/edit").params(paramMap))
                .andDo(print())
                .andExpect(view().name("/admin/dish/editDishError.html"))
                .andExpect(model().hasErrors())
                .andExpect(model().errorCount(expectedErrorCount))
                .andExpect(model().attributeExists("editableDish", "dishCategories", "ingredients"))
                .andExpect(model().attributeHasFieldErrorCode("editableDish", "name", anyOf(is("Size"), is("NotEmpty"))))
                .andReturn();

        verifyNoMoreInteractions(dishServiceMock);

    }

    private static class DishBuilder {
        private Dish dish = new Dish();

        DishBuilder id(int id) {
            dish.setId(id);
            return this;
        }

        DishBuilder name(String name) {
            dish.setName(name);
            return this;
        }

        DishBuilder category(String category) {
            dish.setCategory(new DishCategory(category.hashCode(), category));
            return this;
        }

        DishBuilder price(double price) {
            dish.setPrice(Money.of(CurrencyUnit.USD, price));
            return this;
        }

        DishBuilder weight(int weight) {
            dish.setWeight(weight);
            return this;
        }

        DishBuilder photo(byte[] photo) {
            dish.setPhoto(photo);
            return this;
        }

        Dish build() {
            return dish;
        }
    }

}
