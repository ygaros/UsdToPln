package org.ygaros.usdtopln.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.ygaros.usdtopln.data.*;
import org.ygaros.usdtopln.domain.Domain;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/")
public class FrontPageController {

    private final Domain domain;

    @Autowired
    public FrontPageController(Domain domain){
        this.domain = domain;
    }

    @GetMapping
    public String frontPage(Model model) throws JAXBException, IOException {

        List<Computer> computers = this.domain.getAllComputers();
        model.addAttribute("computers", computers);
        model.addAttribute("SearchQuery", new SearchQuery());
        model.addAttribute("date", new CalculateQuery());
        model.addAttribute("xmlPath", new XmlPath());
        return "index";
    }
    @PostMapping
    public String handleSearch(Model model,
                               SearchQuery searchQuery,
                               CalculateQuery date) throws Exception {
        this.domain.checkIfSearchQueryContainsDate(searchQuery);
        model.addAttribute("SearchQuery", searchQuery);
        model.addAttribute("date", date);
        model.addAttribute("computers", this.domain.handleSearch(searchQuery));
        model.addAttribute("xmlPath", new XmlPath());
        return "index";
    }
    @PostMapping("/calculate")
    public String calculate(Model model,
                            SearchQuery searchQuery,
                            CalculateQuery date) throws JAXBException, IOException {
        this.domain.calculateForDate(date.getData());
        model.addAttribute("SearchQuery", searchQuery);
        model.addAttribute("computers", this.domain.getAllComputers());
        model.addAttribute("xmlPath", new XmlPath());
        model.addAttribute("date", date);
        return "index";
    }
    @PostMapping("/xml")
    public String changeDestinationForXml(Model model,
                                          XmlPath xmlPath,
                                          SearchQuery searchQuery,
                                          CalculateQuery date) throws JAXBException, IOException {
        model.addAttribute("xmlPath", xmlPath);
        model.addAttribute("SearchQuery", searchQuery);
        model.addAttribute("computers", this.domain.getAllComputers());
        model.addAttribute("date", date);
        this.domain.setNewXmlPath(xmlPath);
        return "index";
    }

}
