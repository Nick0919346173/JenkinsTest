package com.aic.edudemo.service;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import com.aic.edudemo.domain.Employee;
import com.aic.edudemo.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.aic.edudemo.web.vm.EmployeeRequestVM;

@AllArgsConstructor
@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public Page<Employee> findAll(Pageable pageable) {
        return this.employeeRepository.findAll(pageable);
    }
    public Page<Employee> findEmployees(Pageable pageable, EmployeeRequestVM employee) {
        this.format(employee);
        return this.employeeRepository.findEmployees(pageable, employee);
    }
    public List<Employee> findAll() {
        return this.employeeRepository.findAll();
    }
    public List<Employee> findEmployees(EmployeeRequestVM employee) {
        this.format(employee);
//        if( employee.getDepartmentIds()!=null){
//            Iterator<Integer> iter = employee.getDepartmentIds().iterator();
//            StringBuilder stringBuilder = new StringBuilder();
//            while (iter.hasNext())
//                stringBuilder.append(","+iter.next());
//            stringBuilder.substring(0);
            return this.employeeRepository.findEmployees(employee.getLastName(),employee.getFirstName(),employee.getEmail(),employee.getPhoneNumber(),employee.getSalaryFrom(),employee.getSalaryTo(),employee.getHireDateFrom(),employee.getHireDateTo(),employee.getJobId(),employee.getDepartmentIds(),employee.getDepartmentIds().size());
//        }else{
//            list
//        }

//        return this.employeeRepository.findEmployees(employee.getLastName(),employee.getFirstName(),employee.getEmail(),employee.getPhoneNumber(),employee.getSalaryFrom(),employee.getSalaryTo(),employee.getHireDateFrom(),employee.getHireDateTo(),employee.getJobId(),);
    }

    private EmployeeRequestVM format(EmployeeRequestVM employee) {
        if(!Objects.equals(employee.getFirstName(), "%")) employee.setFirstName("%" + employee.getFirstName() + "%");
        if(!Objects.equals(employee.getLastName(), "%")) employee.setLastName("%" + employee.getLastName() + "%");
        return employee;
    }
    public Employee save(Employee employee){
        return this.employeeRepository.save(employee);
    }
    public void delete(Employee employee) {
        this.employeeRepository.deleteById(employee.getEmployeeId());
    }
}

