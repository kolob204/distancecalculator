package com.webservice.repository;

import com.webservice.entity.City;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

    @XmlRootElement(name = "cities")
    @XmlAccessorType(XmlAccessType.FIELD)
    public class Cities {


        @XmlElement(name = "city", type = City.class)
        private List<City> cities;

        public List<City> getCities() {
            return cities;
        }


        public Cities() {
        }

        public Cities(List<City> cities) {
            this.cities = cities;
        }

        public void setCities(List<City> cities) {
            this.cities = cities;
        }


    }
