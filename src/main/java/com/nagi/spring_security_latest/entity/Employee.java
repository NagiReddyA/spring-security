package com.nagi.spring_security_latest.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employee")
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Nonnull
    private Integer emp_id;

    private String first_name;

    private String last_name;

    @Column(unique = true, length = 100)
    private String email;

    @Column(length = 15)
    @NotNull(message = "Phone number is required")
    private String phone;

    @Column(precision = 10, scale = 2)
    private BigDecimal salary;

    private LocalDateTime hireDate;

    private String department;
}
