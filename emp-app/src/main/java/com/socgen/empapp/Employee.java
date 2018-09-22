package com.socgen.empapp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Employee {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Integer id;
	
	@Column
	@JsonProperty("name")
	String name;
	
	@Column
	@JsonProperty("dept")
	String dept;
	
	
	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", type=" + dept + "]";
	}
	
	public Employee() {}
	public Employee(Integer id, String name, String dept) {
		this.id = id; this.name = name; this.dept = dept;
	}

}
