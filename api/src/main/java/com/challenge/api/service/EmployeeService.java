package com.challenge.api.service;

import com.challenge.api.model.CreatesEmployeeRequest;
import com.challenge.api.model.Employee;
import com.challenge.api.model.EmployeeImplemented;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * Business logic for Employees, uses thread-safe in memory store.
 */
@Service
public class EmployeeService {
    private final Map<UUID, Employee> employees = new ConcurrentHashMap<>();

    public EmployeeService() {
        addTempEmployee("Sarah", "James", 100000, 40, "Lawyer", "sarah.james@company.com");
        addTempEmployee("Ralph", "Jones", 95000, 38, "Software Engineer", "ralph.jones@company.com");
    }

    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees.values());
    }

    /**
     * @param uuid Employee UUID
     * @return The matching Employee, or an empty Optional if none exist
     */
    public Optional<Employee> getEmployeeByUuid(UUID uuid) {
        return Optional.ofNullable(employees.get(uuid));
    }

    /**
     * Validates the request, then creates an Employee with an assigned UUID, full name, and hire date.
     * @param request Attributes that are necessary to create an Employee
     * @return Newly created Employee
     * @throws ResponseStatusException 400 if required fields are missing or invalid
     */
    public Employee createEmployee(CreatesEmployeeRequest request) {
        validate(request);
        EmployeeImplemented employee = new EmployeeImplemented();
        employee.setUuid(UUID.randomUUID());
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setFullName(request.getFirstName() + " " + request.getLastName());
        employee.setSalary(request.getSalary());
        employee.setAge(request.getAge());
        employee.setJobTitle(request.getJobTitle());
        employee.setEmail(request.getEmail());
        employee.setContractHireDate(Instant.now());
        employees.put(employee.getUuid(), employee);
        return employee;
    }

    private void addTempEmployee(
            String firstName, String lastName, int salary, int age, String jobTitle, String email) {
        CreatesEmployeeRequest request = new CreatesEmployeeRequest();
        request.setFirstName(firstName);
        request.setLastName(lastName);
        request.setSalary(salary);
        request.setAge(age);
        request.setJobTitle(jobTitle);
        request.setEmail(email);
        createEmployee(request);
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    private void validate(CreatesEmployeeRequest request) {
        if (request == null
                || isBlank(request.getFirstName())
                || isBlank(request.getLastName())
                || isBlank(request.getEmail())
                || isBlank(request.getJobTitle())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "needs firstName, lastName, email, and jobTitle");
        }
        if (request.getSalary() == null || request.getSalary() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "salary must be a non negative number");
        }
        if (request.getAge() == null || request.getAge() < 16) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "must be 16 years old or older");
        }
    }
}
