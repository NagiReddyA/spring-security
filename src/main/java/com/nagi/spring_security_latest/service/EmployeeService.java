package com.nagi.spring_security_latest.service;

import com.nagi.spring_security_latest.entity.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> getAllEmployees();

    void addNewEmployee(Employee employee);
}
