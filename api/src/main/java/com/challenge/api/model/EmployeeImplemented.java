package com.challenge.api.model;

import java.time.Instant;
import java.util.UUID;
import lombok.Data;

/**
 * This is the fully model of Employee. Lombok's {@code @Data} used to generate
 * getters and setters used by {@link Employee}.
 */
@Data
public class EmployeeImplemented implements Employee {
    private UUID uuid;
    private String firstName;
    private String lastName;
    private String fullName;
    private Integer salary;
    private Integer age;
    private String jobTitle;
    private String email;
    private Instant contractHireDate;
    private Instant contractTerminationDate;
}
