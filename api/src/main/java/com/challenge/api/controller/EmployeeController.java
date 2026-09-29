package com.challenge.api.controller;

import com.challenge.api.model.CreatesEmployeeRequest;
import com.challenge.api.model.Employee;
import com.challenge.api.service.EmployeeService;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * REST API exposing Employee data for consumption by Employees-R-US web hooks.
 *
 * <p>Security: in production these endpoints should be protected through Spring Security;
 * They require an API key or OAuth2 bearer token issued to Employees-R-US, served only over HTTPS.
 */
@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * @return All Employees
     */
    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    /**
     * @param uuid Employee UUID
     * @return Requested Employee if exists
     * @throws ResponseStatusException 404 if there exists no Employee with param UUID
     */
    @GetMapping("/{uuid}")
    public Employee getEmployeeByUuid(@PathVariable UUID uuid) {
        return employeeService
                .getEmployeeByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));
    }

    /**
     * @param requestBody Attributes to create an Employee
     * @return Newly created Employee
     * @throws ResponseStatusException 400 if request is missing or fields are invalid
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody CreatesEmployeeRequest requestBody) {
        return employeeService.createEmployee(requestBody);
    }
}
