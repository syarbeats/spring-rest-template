package com.learning.spring.spring_rest_template;

import java.util.List;

import org.springframework.http.HttpEntity;
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
    	App app = new App();
    	
    	/**
    	 * GET DATA FROM REST API - OBJECT MODE RESPONSE
    	 * **/
    	Employee employee = app.getEmployeeById(808);
    	System.out.println("Employe Name: "+ employee.getName()+" Salary: "+employee.getSalary());
    	
    	String name = app.getEmployeNameById(808);
    	System.out.println("Employee name: "+name);
    	
    	/**
    	 * GET DATA FROM REST API - ARRAY OF OBJECT MODE RESPONSE
    	 * **/
    	List<Employee> empList = app.getEmployeeList(2, 3);
    	
    	for(Employee emp:empList) {
			System.out.println("Name: "+emp.getName()+" Salary: "+emp.getSalary());
		}
    	
    
    	/**
    	 * POST OBJECT DATA TO REST API ENDPOINT
    	 * **/
    	app.addEmployee(new Employee(708, "Chapo El Guzman", 150000));
    	
  
    	
    }
    
    /**
	 * GET DATA FROM REST API - OBJECT MODE RESPONSE
	 * **/
    public Employee getEmployeeById(int id) {
    	
    	RestTemplate restTemplate = new RestTemplate();
        Employee employee = restTemplate.getForObject(GET_EMPLOYEE_BY_ID+"/"+id, Employee.class);
    
    	return employee;
    }
    
    /**
	 * GET DATA FROM REST API - OBJECT MODE RESPONSE
	 * **/
    public String getEmployeNameById(int id) {
    	ObjectMapper mapper;
    	RestTemplate restTemplate = new RestTemplate();
    	JsonNode root = null;
    	
    	ResponseEntity<String> response = restTemplate.getForEntity(GET_EMPLOYEE_BY_ID+"/"+id, String.class);
    	
    	System.out.println("Response Status Code: "+response.getStatusCode());
    	System.out.println("Response Body: "+response.getBody());
    	
    	mapper =  new ObjectMapper();
       
    	try {
    		root = mapper.readTree(response.getBody());
    		
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	
    	JsonNode name = root.path("name");
    	
    	return name.asText();
    	
    }
    
    /**
	 * GET DATA FROM REST API - ARRAY OF OBJECT MODE RESPONSE
	 * **/
    public List<Employee> getEmployeeList(int page, int size){
    	
    	RestTemplate restTemplate = new RestTemplate();
    	ResponseEntity<String> response = restTemplate.getForEntity(GET_EMPLOYEE_LIST+"?page="+page+"&size="+size, String.class);
    	
    	System.out.println("Response Status Code: "+response.getStatusCode());
    	System.out.println("Response Body: "+response.getBody());
       
    	ObjectMapper mapper =  new ObjectMapper();
       
    	try {
    		List<Employee> empList = mapper.readValue(response.getBody(), new TypeReference<List<Employee>>() {});
    		return empList;
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	
    	return null;
    }
    
    /**
	 * POST OBJECT DATA TO REST API ENDPOINT
	 * **/
    public Employee addEmployee(Employee emp) {
    	
    	RestTemplate restTemplate = new RestTemplate();
    	HttpEntity<Employee> request = new HttpEntity<>(emp);
    	
    	try {
    		Employee empl = restTemplate.postForObject(ADD_EMPLOYEE, request, Employee.class);
    		System.out.println("Employee Data "+empl.getName()+" Salary: "+empl.getSalary()+" has been inserted successfully...");
    		return empl;
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	
    	return null;
    }
    
}
