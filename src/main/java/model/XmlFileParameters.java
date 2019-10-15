package model;

import lombok.Getter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter

@XmlRootElement(name = "Parameters")
public class XmlFileParameters {
    private Global global;
    private Manufacturers manufacturers;

    @XmlElement(name = "Global")
    public void setGlobal(Global global) {
        this.global = global;
    }

    @XmlElement(name = "Manufacturers")
    public void setManufacturers(Manufacturers manufacturers) {
        this.manufacturers = manufacturers;
    }
}
