package model;

import lombok.Getter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@Getter
public class Manufacturer implements BaseModel {
    private String products;
    private String name;
    private PriceLimit priceLimit;

    @XmlAttribute(name = "products")
    public void setProducts(String products) {
        this.products = products;
    }

    @XmlElement(name = "Name")
    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "Pricelimit")
    public void setPriceLimit(PriceLimit priceLimit) {
        this.priceLimit = priceLimit;
    }

    @Override
    public String toString() {
        return gson.toJson(this);
    }
}
