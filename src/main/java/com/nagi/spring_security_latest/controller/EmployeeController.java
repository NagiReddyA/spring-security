package com.nagi.spring_security_latest.controller;

import com.nagi.spring_security_latest.entity.Employee;
import com.nagi.spring_security_latest.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/welcome")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String welcomeToApp(){
        return "Welcome!!!";
    }


    @GetMapping("/getAllEmployees")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public List<Employee> getAllEmployees(){
       List<Employee> employeeList = employeeService.getAllEmployees();
       return employeeList;
    }

    @PostMapping("/addEmployee")
    public void addEmployee(@RequestBody Employee employee){
        if(employee != null){
            employeeService.addNewEmployee(employee);
        }
    }


}
