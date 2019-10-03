package model;

import lombok.Getter;

import javax.xml.bind.annotation.XmlElement;

@Getter
public class Global implements BaseModel {
    private Price price;
    private ExcludedVendors excludedVendors;

    @XmlElement(name = "Price")
    public void setPrice(Price price) {
        this.price = price;
    }
    @XmlElement(name = "Excluded_vendors")
    public void setExcludedVendors(ExcludedVendors excludedVendors) {
        this.excludedVendors = excludedVendors;
    }

    @Override
    public String toString() {
        return gson.toJson(this);
    }
}
