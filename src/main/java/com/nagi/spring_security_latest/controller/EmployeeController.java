package com.nagi.spring_security_latest.controller;

import com.nagi.spring_security_latest.entity.Employee;
import com.nagi.spring_security_latest.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/getAllEmployees")
    public List<Employee> getAllEmployees(){
       List<Employee> employeeList = employeeService.getAllEmployees();
       return employeeList;
    }

}
