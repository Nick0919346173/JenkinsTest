package com.aic.edudemo.repository;
import com.aic.edudemo.domain.Employee;
import com.aic.edudemo.web.vm.EmployeeRequestVM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
    @Query("Select e From Employee e"
            +" Where Upper(e.lastName) Like Upper(:#{#selector.lastName})"
            +" And Upper(e.firstName) Like Upper(:#{#selector.firstName})"
            +" And ( e.email = :#{#selector.email} OR :#{#selector.email} Is Null )"
            +" And ( e.phoneNumber = :#{#selector.phoneNumber} OR :#{#selector.phoneNumber} Is Null )"
            +" And ( e.salary Between :#{#selector.salaryFrom} And :#{#selector.salaryTo} )"
            +" And ( e.hireDate Between :#{#selector.hireDateFrom} And :#{#selector.hireDateTo} )"
            +" And ( e.jobId = :#{#selector.jobId} OR :#{#selector.jobId} is Null )"
            +" And ( e.departmentId IN :#{#selector.departmentIds.size() != 0 ? #selector.departmentIds : null} OR :#{#selector.departmentIds.size() != 0 ? #selector.departmentIds.size() : null} is Null)"
    )
    Page<Employee> findEmployees(Pageable pageable, @Param("selector") EmployeeRequestVM employeeRequestVM);
    @Query(nativeQuery = true,value="Select * From Employees e"
            +" Where Upper(e.last_Name) Like Upper(?1)"
            +" And Upper(e.first_Name) Like Upper(?2)"
            +" And ( e.email = ?3 OR ?3 Is Null )"
            +" And ( e.phone_Number = ?4 OR ?4 Is Null )"
            +" And ( e.salary Between ?5 And ?6 )"
            +" And ( e.hire_Date Between ?7 And ?8 )"
            +" And ( e.job_Id = ?9 OR ?9 is Null )"
            +" And (e.department_Id in (?10) or ?11=0)"
            ,countQuery = "Select count(*) From Employees e"
            +" Where Upper(e.last_Name) Like Upper(?1)"
            +" And Upper(e.first_Name) Like Upper(?2)"
            +" And ( e.email = ?3 OR ?3 Is Null )"
            +" And ( e.phone_Number = ?4 OR ?4 Is Null )"
            +" And ( e.salary Between ?5 And ?6 )"
            +" And ( e.hire_Date Between ?7 And ?8 )"
            +" And ( e.job_Id = ?9 OR ?9 is Null )"
            +" And (e.department_Id in (?10) or ?11=0)"
//            +"(connect by level <= length(?10) - length(replace(?10, ';', '')) + 1 SELECT REGEXP_SUBSTR('?10', '[^,]+', 1, LEVEL, 'i') FROM DUAL CONNECT BY LEVEL <=LENGTH('?10') - LENGTH(REGEXP_REPLACE('?10', ',', '')) + 1)"
    )
    List<Employee> findEmployees(String lastName, String firstName, String email, String phoneNumber, BigDecimal salaryFrom, BigDecimal salaryTo, LocalDate hireDateFrom, LocalDate hireDateTo, String jobId,List<Integer> departmentId,Integer length);


}
