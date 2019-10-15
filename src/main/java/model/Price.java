package model;

import lombok.Getter;
import javax.xml.bind.annotation.XmlElement;

@Getter
public class Price {

    private int max;

    @XmlElement(name = "Max")
    public void setMax(int max) {
        this.max = max;
    }
}
