package model;

import lombok.Getter;

import javax.xml.bind.annotation.*;
import java.util.List;

@Getter
class ExcludedVendors implements BaseModel {
    private int rating;
    private List<String> vendors;

    @XmlAttribute(name = "rating")
    public void setRating(int rating) {
        this.rating = rating;
    }

    @XmlElement(name = "Vendor")
    public void setVendors(List<String> vendors) {
        this.vendors = vendors;
    }

    @Override
    public String toString() {
        return gson.toJson(this);
    }
}
