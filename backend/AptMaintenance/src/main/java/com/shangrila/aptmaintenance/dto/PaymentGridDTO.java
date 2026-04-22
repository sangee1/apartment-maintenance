package com.shangrila.aptmaintenance.dto;

import com.shangrila.aptmaintenance.entity.PaymentStatus;
import com.shangrila.aptmaintenance.entity.Quarter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class PaymentGridDTO {
    private String flatName;
    private List<PaymentCellDTO> payments;
}
