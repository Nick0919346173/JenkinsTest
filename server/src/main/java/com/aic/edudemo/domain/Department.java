package com.aic.edudemo.domain;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
@Data
@Entity
@Table(name = "DEPARTMENTS")
public class Department {
    @Id
    private Integer departmentId;

    private String departmentName;

    private Integer managerId;

    private Integer locationId;
}
