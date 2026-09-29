package com.challenge.api.model;

import lombok.Data;

/**
 * Request body for the steps of creating an Employee. This doesn't
 * include the server-assigned fields like uuid, fullName, and contract dates.
 */
@Data
public class CreatesEmployeeRequest {

    private String firstName;
    private String lastName;
    private Integer salary;
    private Integer age;
    private String jobTitle;
    private String email;
}
