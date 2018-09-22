package com.socgen.empapp;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.socgen.empapp.Employee;
import com.socgen.empapp.EmployeeService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmpAppApplicationTests {

	@Autowired EmployeeService employeeService;
	
	@Test
	public void contextLoads() {
	}
	
	@Test
	public void testAddProduct() {
		
		Employee actual = new Employee(5, "Eene", "HR");
		Employee expected = employeeService.addEmployee(actual);
		assertEquals(expected.id, actual.id);
		assertEquals(expected.name, actual.name);
		assertEquals(expected.dept, actual.dept);
	}
	
	@After
	public void cleanUp() {
		employeeService.deleteById(5);
	}

}
