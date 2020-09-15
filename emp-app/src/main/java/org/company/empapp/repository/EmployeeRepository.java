package org.company.empapp.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import org.company.empapp.model.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Integer>{
	
	List<Employee> findById(Integer id);

}
