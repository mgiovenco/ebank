package com.ebank.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class Customer {

    private final long id;

    @NotNull
    private final String firstName;

    @NotNull
    private final String lastName;

}
