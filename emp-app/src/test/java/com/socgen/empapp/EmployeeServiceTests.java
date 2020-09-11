package com.socgen.empapp;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.collect.Lists;
import com.socgen.empapp.business.EmployeeService;
import com.socgen.empapp.model.Employee;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeServiceTests {
	
	@Autowired EmployeeService employeeService;
	Employee actual;
	
	@Before
	public void setUp() {		
		actual = new Employee(7, "Eene", "HR", 40);
	}
	
	@Test
	public void testAddEmployee() {
		
		List<Employee> expected = employeeService.addEmployee(Lists.newArrayList(actual));
		assertEquals(expected.get(0).id, actual.id);
		assertEquals(expected.get(0).name, actual.name);
		assertEquals(expected.get(0).dept, actual.dept);
		assertEquals(expected.get(0).age, actual.age);
	}
	
	@After
	public void cleanUp() {
		employeeService.deleteById(7);
	}

}
