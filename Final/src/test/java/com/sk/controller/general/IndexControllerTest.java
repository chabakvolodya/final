package com.sk.controller.general;

import com.sk.config.TestControllerConfig;
import com.sk.model.Dish;
import com.sk.model.Employee;
import com.sk.model.Menu;
import com.sk.model.Position;
import com.sk.service.DishService;
import com.sk.service.EmployeeService;
import com.sk.service.MenuService;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.View;

import java.time.LocalDate;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestControllerConfig.class)
@WebAppConfiguration
public class IndexControllerTest {

    @Mock private DishService dishServiceMock;
    @Mock private EmployeeService employeeServiceMock;
    @Mock private MenuService menuServiceMock;
    @Mock private View mockView;
    @InjectMocks private IndexController indexController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(indexController)
                .setSingleView(mockView)
                .build();
    }

    @Test
    public void index() throws Exception {
        Menu menuOne = new MenuBuilder()
                .id(1)
                .name("TestMenu_1")
                .build();

        Menu menuTwo = new MenuBuilder()
                .id(2)
                .name("TestMenu_2")
                .build();

        List<Menu> expectedMenus = asList(menuOne, menuTwo);
        when(menuServiceMock.findAll()).thenReturn(expectedMenus);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("menus", expectedMenus))
                .andExpect(model().attributeExists("dish"))
                .andExpect(view().name("index.html"));

        mockMvc.perform(get("/index"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("menus", expectedMenus))
                .andExpect(model().attributeExists("dish"))
                .andExpect(view().name("index.html"));

    }

    @Test
    public void dishDetail() throws Exception {
        when(dishServiceMock.findById(1)).thenReturn(new Dish());

        mockMvc.perform(get("/dish?id=1"))
                .andExpect(status().isOk())
                .andExpect(view().name("dishDetail.html"))
                .andExpect(model().attribute("dish", instanceOf(Dish.class)));
    }

    @Test
    public void findDishByName() throws Exception {
        List<Dish> expectedList = asList(new Dish(), new Dish());
        String paramDishName = "test name";

        when(dishServiceMock.findByName(paramDishName)).thenReturn(expectedList);

        mockMvc.perform(post("/dish/findByName").param("name", paramDishName))
                .andExpect(status().isOk())
                .andExpect(view().name("dishFindResults.html"))
                .andExpect(model().attribute("dishes", expectedList))
                .andExpect(model().attributeExists("dishes"));

    }

    @Test
    public void restaurantScheme() throws Exception {
        mockMvc.perform(get("/scheme"))
                .andExpect(status().isOk())
                .andExpect(view().name("restaurantScheme.html"));
    }

    @Test
    public void contacts() throws Exception {
        mockMvc.perform(get("/contacts"))
                .andExpect(status().isOk())
                .andExpect(view().name("contacts.html"));
    }

    @Test
    public void team() throws Exception {
        Employee first = new EmployeeBuilder()
                .id(1)
                .name("test1")
                .birthday("1986-12-31")
                .salary(150D)
                .asWaiter()
                .build();

        Employee second = new EmployeeBuilder()
                .id(2)
                .name("test2")
                .asWaiter()
                .build();


        List<Employee> expectedEmployees = asList(first, second);
        when(employeeServiceMock.findAll()).thenReturn(expectedEmployees);


        mockMvc.perform(get("/team"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("employees", expectedEmployees))
                .andExpect(view().name("team.html"));
    }

    public static class EmployeeBuilder {
        private Employee employee = new Employee();

        private Position waiter = new Position(1, "waiter");

        public EmployeeBuilder id(int id) {
            employee.setId(id);
            return this;
        }

        public EmployeeBuilder name(String name) {
            employee.setFirstName(name);
            return this;
        }

        public EmployeeBuilder lastName(String name) {
            employee.setLastName(name);
            return this;
        }

        public EmployeeBuilder salary(double salary) {
            employee.setSalary(Money.of(CurrencyUnit.USD, salary));
            return this;
        }

        public EmployeeBuilder phone(String phone) {
            employee.setPhoneNumber(phone);
            return this;
        }

        public EmployeeBuilder birthday(String isoDate) {
            employee.setBirthDay(LocalDate.parse(isoDate));
            return this;
        }

        public EmployeeBuilder isDeleted(boolean isDeleted) {
            employee.setDeleted(isDeleted);
            return this;
        }

        public EmployeeBuilder asWaiter() {
            employee.setPosition(waiter);
            return this;
        }

        public Employee build() {
            return employee;
        }

    }

    public static class MenuBuilder {
        private Menu menu = new Menu();

        public MenuBuilder id(int id) {
            menu.setId(id);
            return this;
        }

        public MenuBuilder name(String name) {
            menu.setDescription(name);
            return this;
        }

        public MenuBuilder dishes(List<Dish> dishes) {
            menu.setDishes(dishes);
            return this;
        }

        public Menu build() {
            return this.menu;
        }
    }
}