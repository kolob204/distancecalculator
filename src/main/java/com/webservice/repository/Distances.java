package com.webservice.repository;



import com.webservice.entity.Distance;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "distances")
@XmlAccessorType(XmlAccessType.FIELD)
public class Distances {

    @XmlElement(name = "distance", type = Distance.class)
    private List<Distance> distances;

    public Distances(List<Distance> cities) {
        this.distances = cities;
    }

    public Distances() {
    }

    public List<Distance> getDistances() {
        return distances;
    }

    public void setDistances(List<Distance> distances) {
        this.distances = distances;
    }
}
