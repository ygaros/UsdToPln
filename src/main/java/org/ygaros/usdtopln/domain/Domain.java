package org.ygaros.usdtopln.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ygaros.usdtopln.data.*;
import org.ygaros.usdtopln.repository.ComputerRepository;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@Service
public class Domain {

    final private ComputerRepository computerRepository;
    final private USDcaller caller;
    final private XMLParser parser;

    @Autowired
    public Domain(ComputerRepository computerRepository,
                  USDcaller caller,
                  XMLParser parser){
        this.computerRepository = computerRepository;
        this.caller = caller;
        this.parser = parser;
    }

    public void calculateForDate(LocalDate date) throws JAXBException, IOException {
        List<Computer> computer = this.computerRepository.findByIdDate("2022-01-03");
        this.saveComputers(computer, date);
    }

    public void saveComputers(List<Computer> computers, LocalDate newDate) throws JAXBException, IOException {
        List<Computer> updated = this.newDate(computers, newDate);
        this.computerRepository.saveAll(updated);
        this.generateXML(updated);
    }

    private Invoice generateInvoice(List<Computer> computerList){
        Invoice invoice = new Invoice();
        List<ComputerXML> computers = new ArrayList<>(computerList.size());
        invoice.setComputers(computers);
        computerList.forEach(c -> computers.add(this.parser.toComputerXML(c)));
        return invoice;
    }

    private void generateXML(List<Computer> computerList) throws JAXBException, IOException {
        this.parser.generateXMLFile(this.generateInvoice(computerList));
    }

    private List<Computer> newDate(List<Computer> computers, LocalDate date){
        BigDecimal mid = this.caller.getMidValue(date);
        List<Computer> result = new ArrayList<>(computers.size());
        computers.forEach(c -> {
            Computer newComputer = new Computer();
            newComputer.setId(new ComputerId(c.getId().getName(), date.toString()));
            newComputer.setCostUSD(c.getCostUSD());
            newComputer.setCostPLN(
                    c.getCostUSD()
                            .multiply(mid)
                            .setScale(2, RoundingMode.HALF_UP));
            result.add(newComputer);
        });
        return result;
    }

    public List<Computer> getAllComputers(){
        return this.computerRepository.findAll();
    }


    public List<Computer> handleSearch(SearchQuery searchQuery) {
        if(searchQuery.isIgnoreOrderBy()){
            return this.computerRepository.findByPartialNameAndDate(searchQuery.getNameQuery(),
                    searchQuery.getDateQuery());
        }
        if(searchQuery.getNameOrderBy() != null) {
            if(searchQuery.getDateOrderBy() != null) {
                return calculateForNameOrderByDateOrderByExists(searchQuery);
            }else{
                return calculateForNamOrderByDateOrderByDoesntExists(searchQuery);
            }
        }else{
            return this.calculateOnlyOrderByDate(searchQuery);
        }
    }

    private List<Computer> calculateForNamOrderByDateOrderByDoesntExists(SearchQuery searchQuery) {
        switch (searchQuery.getNameOrderBy()) {
            case ASC:
                return this.computerRepository.findByPartialNameAndDateOrderByNameAsc(searchQuery.getNameQuery(),
                        searchQuery.getDateQuery());
            case DESC:
                return this.computerRepository.findByPartialNameAndDateOrderByNameDesc(searchQuery.getNameQuery(),
                        searchQuery.getDateQuery());
            default:
                return this.getAllComputers();
        }
    }

    private List<Computer> calculateForNameOrderByDateOrderByExists(SearchQuery searchQuery) {
        switch (searchQuery.getNameOrderBy()) {
            case ASC:
                return this.calculateForNameAscOrderByDate(searchQuery);
            case DESC:
                return this.calculateForNameDescOrderByDate(searchQuery);
            default:
                return this.getAllComputers();
        }
    }
    private List<Computer> calculateOnlyOrderByDate(SearchQuery searchQuery) {
        if(searchQuery.getDateOrderBy() != null){
            switch (searchQuery.getDateOrderBy()){
                case ASC:
                    return this.computerRepository.findByPartialNameAndDateOrderByDateAsc(searchQuery.getNameQuery(),
                            searchQuery.getDateQuery());
                case DESC:
                    return this.computerRepository.findByPartialNameAndDateOrderByDateDesc(searchQuery.getNameQuery(),
                            searchQuery.getDateQuery());
                default:
                    return this.getAllComputers();
            }
        }else{
            return this.computerRepository.findByPartialNameAndDate(searchQuery.getNameQuery(),
                    searchQuery.getDateQuery());
        }
    }

    private List<Computer> calculateForNameDescOrderByDate(SearchQuery searchQuery) {
        switch (searchQuery.getDateOrderBy()) {
            case ASC:
                return this.computerRepository.findByPartialNameAndDateOrderByDateAscNameDesc(searchQuery.getNameQuery(),
                        searchQuery.getDateQuery());
            case DESC:
                return this.computerRepository.findByPartialNameAndDateOrderByDateDescNameDesc(searchQuery.getNameQuery(),
                        searchQuery.getDateQuery());
            default:
                return this.getAllComputers();
        }
    }

    private List<Computer> calculateForNameAscOrderByDate(SearchQuery searchQuery) {
        switch (searchQuery.getDateOrderBy()) {
            case ASC:
                return this.computerRepository.findByPartialNameAndDateOrderByDateAscNameAsc(searchQuery.getNameQuery(),
                        searchQuery.getDateQuery());
            case DESC:
                return this.computerRepository.findByPartialNameAndDateOrderByDateDescNameAsc(searchQuery.getNameQuery(),
                        searchQuery.getDateQuery());
            default:
                return this.getAllComputers();
        }
    }


    public void checkIfSearchQueryContainsDate(SearchQuery searchQuery) throws Exception{
        String dataQuery = searchQuery.getDateQuery();
        if(dataQuery.length() < 1){
            return;
        }
        boolean isLocalDate = this.checkIfLocalDate(dataQuery);
        boolean isYear = this.checkIfYear(dataQuery);
        boolean isYearMonth = this.checkIfYearMonth(dataQuery);
        boolean isMonthDay = this.checkIfMonthDay(dataQuery);
        if(!(isYear || isYearMonth || isMonthDay || isLocalDate)){
            throw new Exception("Niewlasciwy format daty. Poprawny format -> np.: 2022-01-10");
        }
    }

    private boolean checkIfLocalDate(String dataQuery) {
        try {
            LocalDate.parse(dataQuery);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    private boolean checkIfMonthDay(String dataQuery) {
        try {
            MonthDay.parse(dataQuery);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    private boolean checkIfYearMonth(String dataQuery) {
        try {
            YearMonth.parse(dataQuery);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    private boolean checkIfYear(String dataQuery) {
        try {
            Year.parse(dataQuery);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public void setNewXmlPath(XmlPath xmlPath) throws JAXBException, IOException {
        this.parser.changeXmlPath(xmlPath);
        Map<String, List<Computer>> map = this.computerRepository.findAll().stream()
                .collect(groupingBy(c -> c.getId().getDate()));
        for(Map.Entry<String, List<Computer>> entry: map.entrySet()){
            this.generateXML(entry.getValue());
        }
    }
}
