package com.socgen.empapp.business;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.socgen.empapp.common.EmpAppCustomException;
import com.socgen.empapp.model.Employee;
import com.socgen.empapp.repository.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	public EmployeeRepository employeeRepository;

	@Transactional
	public Employee addEmployee(Employee employee) {

		return employeeRepository.save(employee);
	}

	public List<Employee> getEmployeesFilteredByDept(String dept) {

		return ((List<Employee>) employeeRepository.findAll()).stream().filter(employee -> {
			return employee.getDept().equals(dept);
		}).collect(Collectors.toList());
	}

	public List<Employee> getEmployeesFilteredByDeptAndAge(String dept, Integer age) {

		return ((List<Employee>) employeeRepository.findAll()).stream().filter(employee -> {
			return employee.getDept().equals(dept) && employee.getAge() >= age;
		}).collect(Collectors.toList());
	}

	@Transactional
	public Employee deleteById(Integer id) {

		Employee emp = employeeRepository.findOne(id);
		employeeRepository.delete(id);
		return emp;
	}

	public Employee getEmployeeById(Integer id) {
		List<Employee> empList = employeeRepository.findById(id);
		if (empList.isEmpty())
			throw new EmpAppCustomException(EmployeeService.class, "Employee Not Found by id = ", id);
		return employeeRepository.findById(id).get(0);
	}

	public List<Employee> getAllEmployees() {
		return (List<Employee>) employeeRepository.findAll();
	}

}
