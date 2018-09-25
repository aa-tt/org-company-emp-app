package com.socgen.empapp;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.socgen.empapp.business.EmployeeService;
import com.socgen.empapp.model.Employee;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeServiceTests {
	
	@Autowired EmployeeService employeeService;
	Employee actual;
	
	@Before
	public void setUp() {		
		actual = new Employee(7, "Eene", "HR");
	}
	
	@Test
	public void testAddEmployee() {
		
		Employee expected = employeeService.addEmployee(actual);
		assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.getDept(), actual.getDept());
	}
	
	@After
	public void cleanUp() {
		employeeService.deleteById(7);
	}

}
