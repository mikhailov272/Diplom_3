package ru.praktikum.ui.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
public class UserRequest {
    private final String email;
    @ToString.Exclude
    private final String password;
    private final String name;
}
