package org.ygaros.usdtopln.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalculateQuery {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate data;
}
