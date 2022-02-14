package org.ygaros.usdtopln.data;

import lombok.Data;

@Data
public class XmlPath {

    private String xmlPath;

    public XmlPath(){
        this.xmlPath = "";
    }
    public XmlPath(String s){
        this.xmlPath = s;
    }
}
