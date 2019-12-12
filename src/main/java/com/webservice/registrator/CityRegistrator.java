package com.webservice.registrator;

import com.webservice.entity.City;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@Stateless
public class CityRegistrator {

    @Inject
    private EntityManager entityManager;

    public void create (City item) {

        entityManager.merge(item);
    }
    public void update (City item) {

        entityManager.merge(item);
    }
    public void delete (City item) {

        entityManager.remove(item);
    }
}
