package com.sk.controller.admin;

import com.sk.config.TestControllerConfig;
import com.sk.controller.general.IndexControllerTest;
import com.sk.model.Employee;
import com.sk.service.EmployeeService;
import com.sk.service.PositionService;
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

import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestControllerConfig.class)
@WebAppConfiguration
public class EmployeeControllerTest {

    @Mock private EmployeeService employeeServiceMock;
    @Mock private PositionService positionServiceMock;
    @Mock private View viewMock;
    @InjectMocks private EmployeeController employeeControllerMock;

    private MockMvc mockMvc;

    @Resource(name = "conversionService")
    private FormattingConversionService conversionService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(employeeControllerMock)
                .setSingleView(viewMock)
                .setConversionService(conversionService)
                .build();

    }

    @Test
    public void employeePhoto() throws Exception {
        Employee expectedEmployee = new Employee();
        expectedEmployee.setPhoto(new byte[]{});

        when(employeeServiceMock.findById(anyInt())).thenReturn(expectedEmployee);

        mockMvc.perform(get("/employee/{id}/photo", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(content().bytes(expectedEmployee.getPhoto()));

    }

    @Test
    public void showAll() throws Exception {
        Employee employeeOne = new Employee();
        employeeOne.setId(1);
        employeeOne.setFirstName("NameOne");

        Employee employeeTwo = new Employee();
        employeeOne.setId(2);
        employeeOne.setFirstName("NameTwo");

        when(employeeServiceMock.findAll()).thenReturn(asList(employeeOne, employeeTwo));

        mockMvc.perform(get("/admin/employee"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("admin/employee/employee.html"))
                .andExpect(model().attributeExists("employees"))
                .andExpect(model().attribute("employees", asList(employeeOne, employeeTwo)));

        verify(employeeServiceMock, times(1)).findAll();
    }

    @Test
    public void edit() throws Exception {

        Employee employeeForEdit = new IndexControllerTest.EmployeeBuilder()
                .id(1)
                .asWaiter()
                .name("FirstName")
                .lastName("LastName")
                .birthday("1990-10-25")
                .salary(150D)
                .phone("123")
                .build();

        when(employeeServiceMock.findById(anyInt())).thenReturn(employeeForEdit);

        mockMvc.perform(get("/admin/employee/edit").param("id", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("admin/employee/employeeEdit.html"))
                .andExpect(model().attributeExists("positions", "employee"));

        verify(employeeServiceMock, times(1)).findById(anyInt());
        verify(positionServiceMock, times(1)).findAll();
    }

    @Test
    public void editSave() throws Exception {

        MultiValueMap<String, String> employeeParamMap = new LinkedMultiValueMap<>();
        employeeParamMap.add("id", "1");
        employeeParamMap.add("position", "1,waiter");
        employeeParamMap.add("firstName", "TestFirstName");
        employeeParamMap.add("lastName", "TestLastName");
        employeeParamMap.add("birthDay", "25/01/1990");
        employeeParamMap.add("phoneNumber", "333-333-333");
        employeeParamMap.add("salary", "2500");
        employeeParamMap.add("isDeleted", "false");

        mockMvc.perform(post("/admin/employee/edit").params(employeeParamMap))
                .andDo(print())
                .andExpect(view().name("admin/employee/employeeEdit.html"))
                .andExpect(model().attributeExists("positions", "employee"))
                .andExpect(model().hasNoErrors());

        verify(employeeServiceMock, times(1)).update(any(Employee.class));

    }

    @Test
    public void editSaveWrongParam() throws Exception {

        MultiValueMap<String, String> employeeParamMap = new LinkedMultiValueMap<>();
        employeeParamMap.add("id", "");
        employeeParamMap.add("position", "");
        employeeParamMap.add("firstName", "TestFirstName");
        employeeParamMap.add("lastName", "TestLastName");
        employeeParamMap.add("birthDay", "");
        employeeParamMap.add("phoneNumber", "333-333-333");
        employeeParamMap.add("salary", "-2500");
        employeeParamMap.add("isDeleted", "false");

        int expectedErrorCount = 4;

        mockMvc.perform(post("/admin/employee/edit").params(employeeParamMap))
                .andDo(print())
                .andExpect(view().name("admin/employee/employeeEdit.html"))
                .andExpect(model().attributeExists("positions", "employee"))
                .andExpect(model().hasErrors())
                .andExpect(model().errorCount(expectedErrorCount));

        verify(employeeServiceMock, times(0)).update(any(Employee.class));

    }

    @Test
    public void create() throws Exception {
        mockMvc.perform(get("/admin/employee/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/employee/employeeCreate.html"))
                .andExpect(model().attributeExists("positions", "employee"));

        verify(positionServiceMock, times(1)).findAll();
    }

    @Test
    public void saveNew() throws Exception {
        MultiValueMap<String, String> employeeParamMap = new LinkedMultiValueMap<>();
        employeeParamMap.add("id", "1");
        employeeParamMap.add("position", "1,waiter");
        employeeParamMap.add("firstName", "TestFirstName");
        employeeParamMap.add("lastName", "TestLastName");
        employeeParamMap.add("birthDay", "25/01/1990");
        employeeParamMap.add("phoneNumber", "333-333-333");
        employeeParamMap.add("salary", "2500");
        employeeParamMap.add("isDeleted", "false");


        mockMvc.perform(post("/admin/employee/create").params(employeeParamMap))
                .andExpect(status().isOk())
                .andExpect(view().name("redirect: ../employee"));

        verify(employeeServiceMock, times(1)).add(any(Employee.class));
    }


}