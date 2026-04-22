package com.shangrila.aptmaintenance.dto;

import com.shangrila.aptmaintenance.entity.PaymentStatus;
import com.shangrila.aptmaintenance.entity.Quarter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCellDTO {
    private Long id;
    private Quarter quarter;
    private PaymentStatus status;
}
