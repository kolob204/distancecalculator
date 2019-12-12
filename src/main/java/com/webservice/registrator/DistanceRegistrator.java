package com.webservice.registrator;

import com.webservice.entity.Distance;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@Stateless
public class DistanceRegistrator {


    @Inject
    private EntityManager entityManager;

    public void create (Distance item) {

        entityManager.merge(item);
    }
    public void update (Distance item) {

        entityManager.merge(item);
    }
    public void delete (Distance item) {

        entityManager.remove(item);
    }
}
