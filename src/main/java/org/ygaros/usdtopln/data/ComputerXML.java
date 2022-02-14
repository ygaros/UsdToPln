package org.ygaros.usdtopln.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import java.math.BigDecimal;

@XmlRootElement(name = "komputer")
@XmlAccessorType(XmlAccessType.FIELD)
@AllArgsConstructor
@Data
@NoArgsConstructor
public class ComputerXML {

    @XmlElement(name = "nazwa")
    private String name;

    @XmlElement(name = "data_ksiegowania")
    private String date;

    @XmlElement(name = "koszt_USD")
    private BigDecimal costUSD;

    @XmlElement(name = "koszt_PLN")
    private BigDecimal costPLN;
}
