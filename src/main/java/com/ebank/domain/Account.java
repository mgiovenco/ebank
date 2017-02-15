package com.ebank.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class Account {

    private final long id;

    @NotNull
    private String type;

    @NotNull
    private BigDecimal balance;

    @NotNull
    private long customerId;

}
