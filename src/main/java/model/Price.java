package model;

import lombok.Getter;
import javax.xml.bind.annotation.XmlElement;

@Getter
public class Price implements BaseModel {

    private int max;

    @XmlElement(name = "Max")
    public void setMax(int max) {
        this.max = max;
    }

    @Override
    public String toString() {
        return gson.toJson(this);
    }
}
