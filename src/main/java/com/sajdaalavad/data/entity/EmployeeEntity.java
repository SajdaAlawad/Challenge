package com.sajdaalavad.data.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

//Lombok
@Data
@NoArgsConstructor
@Builder

//entity
@Entity
@Table(name="employees")
public class EmployeeEntity extends BaseEntity{
    @Column(name = "employee_name")
    public String employeeName;

    @Column(name = "employee_email")
    public String employeeEmail;


    public EmployeeEntity(String employeeName, String employeeEmail) {
        this.employeeName = employeeName;
        this.employeeEmail = employeeEmail;
    }
}
//parametreli consstructor