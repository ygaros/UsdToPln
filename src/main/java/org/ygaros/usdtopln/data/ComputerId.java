package org.ygaros.usdtopln.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ComputerId implements Serializable {

    @Column(name = "nazwa")
    private String name;

    @Column(name = "data_ksiegowania")
    private String date;
}
