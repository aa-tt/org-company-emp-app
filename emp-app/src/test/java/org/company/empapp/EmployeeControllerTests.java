package org.company.empapp;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.company.empapp.business.EmployeeService;
import org.company.empapp.controller.EmployeeController;
import org.company.empapp.model.Employee;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeControllerTests {

	@MockBean
	private EmployeeService svc;
	private MockMvc mvc;
	private ObjectMapper objectMapper;

	@Before
	public void setUp() {
		this.mvc = MockMvcBuilders.standaloneSetup(new EmployeeController(svc)).build();
		this.objectMapper = new ObjectMapper();

		Employee emp = new Employee();
		emp.id = 1;
		emp.name = "Eene";
		emp.dept = "HR";
		
		List<Employee> empList = new ArrayList<>(); 
		empList.add(emp);

		try {
			doReturn(emp).when(svc).getEmployeeById(any());
			doReturn(empList).when(svc).getAllEmployees();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test_getAllEmployees_expectSuccess() throws Exception {
		this.mvc.perform(get("/v1/employees")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()", is(1)));
	}
	
	@Test
	public void test_getEmployee_expectSuccess() throws Exception {
		this.mvc.perform(get("/v1/employees/5")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id", is(1)))
		.andExpect(jsonPath("$.name", is("Eene")))
		.andExpect(jsonPath("$.dept", is("HR")));
	}

	@After
	public void cleanUp() {
	}

}
