package com.socgen.empapp.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.socgen.empapp.model.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Integer>{
	
	List<Employee> findById(Integer id);

}
