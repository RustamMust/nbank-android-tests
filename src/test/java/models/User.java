package models;

import annotations.GeneratingRule;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    @GeneratingRule(regex = "^[A-Za-z0-9]{3,15}$")
    private String username;
    @GeneratingRule(regex = "^[A-Z]{3}[a-z]{4}[0-9]{3}[$%&]{2}$")
    private String password;
}
