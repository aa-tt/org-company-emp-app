package com.socgen.empapp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {
	
	@Autowired
	public EmployeeService employeeService;
	
	@RequestMapping(
			value="/employees", 
			method=RequestMethod.GET, 
			produces = {"application/json"})
	public ResponseEntity<List<Employee>> getAllEmployees() {
		List<Employee> employees = employeeService.getAllEmployees();
		return new ResponseEntity<List<Employee>>(employees, HttpStatus.OK);
	}
	
	@RequestMapping(
			value="/employee/{empId}", 
			method=RequestMethod.GET, 
			produces = {"application/json"})
	public ResponseEntity<Employee> getEmployee(@PathVariable(value="empId") Integer id) {
		Employee employee = employeeService.getEmployeeById(id);
		return new ResponseEntity<Employee>(employee, HttpStatus.OK);
	}
	
	@RequestMapping(
			value="/employee", 
			method=RequestMethod.GET, 
			produces = {"application/json"})
	public ResponseEntity<List<Employee>> getEmployeeByDepartment(@RequestParam(name="dept") String dept) {
		List<Employee> employees = employeeService.getEmployeesByType(dept);
		return new ResponseEntity<List<Employee>>(employees, HttpStatus.OK);
	}
	
	@RequestMapping(
			value="/employee", 
			method=RequestMethod.DELETE, 
			produces = {"application/json"})
	public ResponseEntity<Employee> deleteEmployee(@RequestParam(name="id") Integer id) {
		Employee employee = employeeService.deleteById(id);
		return new ResponseEntity<Employee>(employee, HttpStatus.OK);
	}
	
	@RequestMapping(
			value="/employee", 
			method=RequestMethod.POST, 
			consumes = {"application/json"}, 
			produces = {"application/json"})
	public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
		Employee employeeSaved = employeeService.addEmployee(employee);
		return new ResponseEntity<Employee>(employeeSaved, HttpStatus.OK);
	}

}
