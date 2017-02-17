package com.ebank.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class Customer {

    private final Integer id;

    @NotNull
    private final String firstName;

    @NotNull
    private final String lastName;

    @NotNull
    private final String phone;

    @NotNull
    private final Boolean active;
}
