package org.company.empapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;

import org.company.empapp.common.AgeLimitValid;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.company.empapp.common.EmpAppCustomDeserializers;

@Entity
public class Employee {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer id;
	
	@Column
	@JsonProperty("name")
	@NotBlank(message="name cannot be null or blank")
	public String name;
	
	@Column
	@JsonProperty("dept")
	@JsonDeserialize(using=EmpAppCustomDeserializers.ToUpperCaseDeserializer.class)
	public String dept;
	
	@Column
	@JsonProperty("age")
	@AgeLimitValid(message = "abcd")
	//@Min(value=20, message="minimum age of employee should be 20")
	public int age;
	
	
	public Employee() {}
	
	public Employee(Integer id, String name, String dept, int age) {
		this.id = id; this.name = name; this.dept = dept; this.age = age;
	}

}
