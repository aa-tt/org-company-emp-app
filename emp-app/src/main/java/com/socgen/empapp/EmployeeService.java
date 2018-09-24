package com.socgen.empapp;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeService {
	
	@Autowired
	public EmployeeRepository employeeRepository;
	
	@Transactional
	public Employee addEmployee(Employee employee) {
		
		return employeeRepository.save(employee);
	}
	
	public List<Employee> getEmployeesFilteredByDept(String dept) {
		
		return ((List<Employee>) employeeRepository.findAll())
				.stream()
				.filter(employee -> {
					return employee.dept.equals(dept);
				})
				.collect(Collectors.toList());
	}

	@Transactional
	public Employee deleteById(Integer id) {
		
		Employee emp = employeeRepository.findOne(id);
			employeeRepository.delete(id);
		return emp;
	}

	public Employee getEmployeeById(Integer id) {
		return employeeRepository.findById(id).get(0);
	}

	public List<Employee> getAllEmployees() {
		return (List<Employee>) employeeRepository.findAll();
	}

}
