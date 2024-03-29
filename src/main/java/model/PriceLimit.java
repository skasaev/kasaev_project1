package model;

import lombok.Getter;

import javax.xml.bind.annotation.XmlElement;

@Getter
public class PriceLimit {
    private int min;
    private int max;

    @XmlElement(name = "Min")
    public void setMin(int min) {
        this.min = min;
    }

    @XmlElement(name = "Max")
    public void setMax(int max) {
        this.max = max;
    }
}
