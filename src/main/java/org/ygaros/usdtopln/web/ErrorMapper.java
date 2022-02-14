package org.ygaros.usdtopln.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.ygaros.usdtopln.data.CalculateQuery;
import org.ygaros.usdtopln.data.SearchQuery;
import org.ygaros.usdtopln.data.XmlPath;
import org.ygaros.usdtopln.domain.Domain;



@ControllerAdvice
public class ErrorMapper {

    private final Domain domain;

    @Autowired
    public ErrorMapper(Domain domain){
        this.domain = domain;
    }

    @ExceptionHandler({
            Exception.class
    })
    public String noData(Model model, Exception ex){
        model.addAttribute("computers", this.domain.getAllComputers());
        model.addAttribute("SearchQuery", new SearchQuery());
        model.addAttribute("date", new CalculateQuery());
        model.addAttribute("error", ex.getMessage());
        model.addAttribute("xmlPath", new XmlPath());
        return "index";
    }
}
