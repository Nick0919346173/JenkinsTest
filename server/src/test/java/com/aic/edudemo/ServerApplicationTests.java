package com.aic.edudemo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import com.aic.edudemo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
@SpringBootTest
class ServerApplicationTests {
	@Autowired
	private EmployeeRepository employeeRepository;

	@Test
	void contextLoads() {
	}
	@Test
	void fetchAllEmployees() {
		assertNotNull(this.employeeRepository);
		this.employeeRepository.findAll().forEach(System.out::println);
	}
}
