package com.socgen.empapp;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Integer>{
	
	List<Employee> findById(Integer id);

}
