package com.learning.spring.spring_rest_template;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.spring.spring_rest_template.model.Employee;

public class App 
{
	
	static final String GET_EMPLOYEE_LIST = "http://localhost:8080/spring-mvc-crud-repository/api/employees";
	static final String GET_EMPLOYEE_BY_ID = "http://localhost:8080/spring-mvc-crud-repository/api/employee";
	static final String GET_EMPLOYEE = "http://localhost:8080/spring-mvc-crud-repository/api/employee";
	static final String UPDATE_EMPLOYEE = "http://localhost:8080/spring-mvc-crud-repository/api/update_employee";
	static final String ADD_EMPLOYEE = "http://localhost:8080/spring-mvc-crud-repository/api/add_employee";
	static final String DELETE_EMPLOYEE = "http://localhost:8080/spring-mvc-crud-repository/api/delete_employee";
	
    public static void main( String[] args ) 
    {
    	JsonNode root = null;
    	RestTemplate restTemplate = new RestTemplate();
    	ResponseEntity<String> response;
    	ObjectMapper mapper;
    	String getEmployeeResourceUrl = GET_EMPLOYEE_LIST;
    	String getEmployeeByIdResourceUrl = GET_EMPLOYEE_BY_ID;
       
    	/**
    	 * GET DATA FROM REST API - OBJECT MODE RESPONSE
    	 * **/
    	//ResponseEntity<String> response = restTemplate.getForEntity(getEmployeeResourceUrl+"?page=1&size=3", String.class);
    	response = restTemplate.getForEntity(getEmployeeByIdResourceUrl+"/202", String.class);
    	Employee employee = restTemplate.getForObject(getEmployeeByIdResourceUrl+"/202", Employee.class);
    	
    	System.out.println("Response Status Code: "+response.getStatusCode());
    	System.out.println("Response Body: "+response.getBody());
    	System.out.println("Employee Name:"+employee.getName()+" Salary: "+employee.getSalary());
       
    	mapper =  new ObjectMapper();
       
    	try {
    		root = mapper.readTree(response.getBody());
    		
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	
    	JsonNode name = root.path("name");
    	System.out.println("Name: "+name);
       
    	/**
    	 * GET DATA FROM REST API - ARRAY OF OBJECT MODE RESPONSE
    	 * **/
    	response = restTemplate.getForEntity(getEmployeeResourceUrl+"?page=1&size=4", String.class);
    	
    	System.out.println("Response Status Code: "+response.getStatusCode());
    	System.out.println("Response Body: "+response.getBody());
       
    	mapper =  new ObjectMapper();
       
    	try {
    		List<Employee> empList = mapper.readValue(response.getBody(), new TypeReference<List<Employee>>() {});
    		
    		for(Employee emp:empList) {
    			System.out.println("Name: "+emp.getName()+" Salary: "+emp.getSalary());
    		}
    		
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
}
