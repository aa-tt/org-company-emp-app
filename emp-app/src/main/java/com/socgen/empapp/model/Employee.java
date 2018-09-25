package com.socgen.empapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.socgen.empapp.common.EmpAppCustomDeserializers;

import lombok.Data;

@Data
@Entity
public class Employee {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Integer id;
	
	@Column
	@JsonProperty("name")
	@NotBlank
	String name;
	
	@Column
	@JsonProperty("dept")
	@JsonDeserialize(using=EmpAppCustomDeserializers.ToUpperCaseDeserializer.class)
	String dept;
	
	@Column
	@JsonProperty("age")
	Integer age;
	
	
	public Employee() {}
	public Employee(Integer id, String name, String dept) {
		this.id = id; this.name = name; this.dept = dept;
	}

}
