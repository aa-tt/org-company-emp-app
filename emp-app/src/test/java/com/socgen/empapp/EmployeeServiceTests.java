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
		assertEquals(expected.get(0).getId(), actual.getId());
		assertEquals(expected.get(0).getName(), actual.getName());
		assertEquals(expected.get(0).getDept(), actual.getDept());
		assertEquals(expected.get(0).getAge(), actual.getAge());
	}
	
	@After
	public void cleanUp() {
		employeeService.deleteById(7);
	}

}
