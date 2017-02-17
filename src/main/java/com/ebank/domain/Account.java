package com.ebank.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class Account {

    private final Integer id;

    @NotNull
    private final String type;

    @NotNull
    private final BigDecimal balance;

    @NotNull
    private final Boolean active;

    @NotNull
    private final Integer customerId;
}
