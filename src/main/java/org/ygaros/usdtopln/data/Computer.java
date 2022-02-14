package org.ygaros.usdtopln.data;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Computer{

    @EmbeddedId
    private ComputerId id;

    @Column(name = "koszt_USD")
    private BigDecimal costUSD;

    @Column(name = "koszt_PLN")
    private BigDecimal costPLN;

}
