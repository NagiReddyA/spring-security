package com.nagi.spring_security_latest.service;

import com.nagi.spring_security_latest.entity.Employee;
import com.nagi.spring_security_latest.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getAllEmployees() {
        try{
           List<Employee> employees = employeeRepository.findAll();
           if(employees.size() != 0){
                return employees;
           }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
