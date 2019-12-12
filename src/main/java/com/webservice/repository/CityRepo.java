package com.webservice.repository;

import com.webservice.entity.City;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@ApplicationScoped
public class CityRepo {

    @Inject
    private EntityManager entityManager;

    public List<City> getAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<City> criteria  = criteriaBuilder.createQuery(City.class);
        Root<City> element = criteria.from(City.class);
        return entityManager.createQuery(criteria).getResultList();
    }

    //поиск  по ID
    public City getById(long id) {
        return  entityManager.find(City.class,id);
    }

    //поиск  по name
    public City getByName(String name) {
        return  entityManager.find(City.class,name);
    }
}
