package model;

import lombok.Getter;

import javax.xml.bind.annotation.*;
import java.util.List;

@Getter
public class ExcludedVendors {
    private String rating;
    private List<String> vendors;

    @XmlAttribute(name = "rating")
    public void setRating(String rating) {
        this.rating = rating;
    }

    @XmlElement(name = "Vendor")
    public void setVendors(List<String> vendors) {
        this.vendors = vendors;
    }
}
