package org.ygaros.usdtopln.data;

import lombok.*;

@Data
@NoArgsConstructor
@ToString
public class NBPResponse {

    private String table;

    private String currency;

    private String code;

    private Rate[] rates;
}
