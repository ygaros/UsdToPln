package org.ygaros.usdtopln;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.ygaros.usdtopln.data.Computer;
import org.ygaros.usdtopln.data.ComputerId;
import org.ygaros.usdtopln.domain.Domain;
import org.ygaros.usdtopln.domain.USDcaller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * @StartUp Generuje podstawowe tresci w bazie danych
 * do pracy aplikacji.
 */
@Component
public class StartUp implements CommandLineRunner {

    private final USDcaller caller;
    private final Domain domain;

    @Autowired
    public StartUp(USDcaller caller,
                   Domain domain){
        this.caller = caller;
        this.domain = domain;
    }

    @Override
    public void run(String... args) throws Exception {
        LocalDate jan03 = LocalDate.parse("2022-01-03");
        LocalDate jan10 = LocalDate.parse("2022-01-10");
        BigDecimal midJan03 = this.caller.getMidValue(jan03);
        BigDecimal midJan10 = this.caller.getMidValue(jan10);

        ComputerId computer1Id = new ComputerId();
        computer1Id.setName("komputer 1");

        ComputerId computer2Id = new ComputerId();
        computer2Id.setName("komputer 2");

        ComputerId computer3Id = new ComputerId();
        computer3Id.setName("komputer 3");

        Computer computer1 = new Computer(
                computer1Id,
                        BigDecimal.valueOf(345),
                        BigDecimal.valueOf(345).multiply(midJan03)
        );
        Computer computer2 = new Computer(
                computer2Id,
                BigDecimal.valueOf(543),
                BigDecimal.valueOf(543).multiply(midJan03)
        );
        Computer computer3 = new Computer(
                computer3Id,
                BigDecimal.valueOf(346),
                BigDecimal.valueOf(346).multiply(midJan03)
        );



        Computer computer10 = new Computer(
                computer1Id,
                BigDecimal.valueOf(345),
                BigDecimal.valueOf(345).multiply(midJan10)
        );
        Computer computer20 = new Computer(
                computer2Id,
                BigDecimal.valueOf(543),
                BigDecimal.valueOf(543).multiply(midJan10)
        );
        Computer computer30 = new Computer(
                computer3Id,
                BigDecimal.valueOf(346),
                BigDecimal.valueOf(346).multiply(midJan10)
        );

        List<Computer> listJan03 = Arrays.asList(computer1, computer2, computer3);
        List<Computer> listJan10 = Arrays.asList(computer10, computer20, computer30);
        this.domain.saveComputers(listJan03, jan03);
        this.domain.saveComputers(listJan10, jan10);

    }
}
