package com.erdemburak.dto;

import com.erdemburak.model.Currency;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class AccountDto {
    private String id;
    private String customerId;
    private Double balance;
    private Currency currency;
}
