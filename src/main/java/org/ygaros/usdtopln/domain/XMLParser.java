package org.ygaros.usdtopln.domain;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.ygaros.usdtopln.data.*;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class XMLParser {

    public String xmlPath;

    @PostConstruct
    public void getPath(){
        String path = null;
        try {
            path = new ClassPathResource("application.yaml").getFile().getPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (path != null) {
            String ending = "target\\classes\\application.yaml";
            path = path.replace(ending, "xml\\");
        }
        this.xmlPath = path;
    }

    public void changeXmlPath(XmlPath xmlPath){
        this.xmlPath = xmlPath.getXmlPath().concat("\\");
    }

    public ComputerXML toComputerXML(Computer computer){
        ComputerId id = computer.getId();
        return new ComputerXML(
                id.getName(),
                id.getDate().toString(),
                computer.getCostUSD(),
                computer.getCostPLN()
        );
    }
    public void generateXMLFile(Invoice invoice) throws JAXBException, IOException {
        Marshaller marshaller = JAXBContext.newInstance(Invoice.class).createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        if(!invoice.getComputers().isEmpty()) {
            String path = String.format("%s", this.getXmlPath());
            String fileEnding = ".xml";
            String fileName = String.format("%s%s", invoice.getComputers().get(0).getDate(), fileEnding);
            File file = new File(path.concat(fileName));
            if (!file.createNewFile()) {
                File archives = new File(path.concat("archives\\"));
                if(!archives.exists()){
                    archives.mkdir();
                }
                try {
                    Path move = Files.move(Paths.get(file.getPath()),
                            Paths.get((archives.getPath().concat("\\a_").concat(fileName))));
                    if(move != null){
                        if(file.createNewFile()){
                            marshaller.marshal(invoice, file);
                        }
                    }else{
                    }
                }catch(FileAlreadyExistsException ignored){
                }
            }else{
                marshaller.marshal(invoice, file);
            }
        }
    }
    public String getXmlPath(){
        return this.xmlPath;
    }
}
