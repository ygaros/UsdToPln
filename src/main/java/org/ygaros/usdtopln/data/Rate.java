package org.ygaros.usdtopln.data;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class Rate {

    private String no;

    private LocalDate effectiveDate;

    private double mid;
}
