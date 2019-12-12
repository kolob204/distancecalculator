package com.webservice.repository;


import com.webservice.entity.City;
import com.webservice.entity.Distance;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

@ApplicationScoped
public class DistanceRepo {

    private HttpServletResponse response;
    @Inject
    private EntityManager entityManager;

    public List<Distance> getAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Distance> criteria  = criteriaBuilder.createQuery(Distance.class);
        Root<Distance> element = criteria.from(Distance.class);
        return entityManager.createQuery(criteria).getResultList();
    }

    //поиск  по ID
    public Distance getById(long id) {
        return  entityManager.find(Distance.class,id);
    }

    //поиск  по Двум городам
    public String getByNames(String from_city, String to_city, String method) {
      Distance x=null;
/*
      работает ОК


        CriteriaBuilder criteriaB = entityManager.getCriteriaBuilder();
        CriteriaQuery<Distance> criteriaQ = criteriaB.createQuery(Distance.class);
        Root<Distance> element = criteriaQ.from(Distance.class);


        Predicate[]predicates = new Predicate[2];
        predicates[0]= criteriaB.equal(element.get("from_city"),from_city);
        predicates[1]= criteriaB.equal(element.get("to_city"), to_city);
        criteriaQ.select(element).where(predicates);


*/



/*  ПРОБА, не работает чёто ))
        CriteriaBuilder criteriaB = entityManager.getCriteriaBuilder();
        CriteriaQuery<Distance> criteriaQ = criteriaB.createQuery(Distance.class);

        Root<Distance> element = criteriaQ.from(Distance.class);

        Predicate[]predicates = new Predicate[2];
        predicates[0]= criteriaB.equal(element.get("from_city"),from_city);
        predicates[1]= criteriaB.equal(element.get("to_city"), to_city);

        criteriaQ.multiselect(element.get(Distance_.distance)).where(predicates);
        x = entityManager.createQuery(criteriaQ).getSingleResult();
*/

/*  ещё одна проба, которая не заработала
        CriteriaBuilder criteriaB = entityManager.getCriteriaBuilder();
        CriteriaQuery<Distance> criteriaQ = criteriaB.createQuery(Distance.class);
        Root<Distance> element = criteriaQ.from(Distance.class);

        criteriaQ.multiselect(element.get("distance"));

        Predicate[]predicates = new Predicate[2];
        predicates[0]= criteriaB.equal(element.get("from_city"),from_city);
        predicates[1]= criteriaB.equal(element.get("to_city"), to_city);

       // criteriaQ.where(criteriaB.in(element.get("id"), criteriaB.parameter("ids"));
        criteriaQ.where(predicates);
        x = entityManager.createQuery(criteriaQ).getSingleResult();
*/

        TypedQuery<Distance> q = entityManager.createQuery("select u from Distance u where u.from_city in (:from_city,:to_city) and u.to_city in (:to_city,:from_city)", Distance.class)
                .setParameter("from_city",from_city)
                .setParameter("to_city",to_city);

        List<Distance> list = null;
        list = q.getResultList();
        if (list == null || list.isEmpty())
            if (method.equals("1"))
                    {return "<html><head><meta charset=\"utf-8\"> </head> <body>" +
                    "Данных нет, попробуйте использовать метод CrowFlight </body> </html>";}
            else {return CrowFlight(from_city,to_city);}

        else { return "<html><head><meta charset=\"utf-8\"> </head> <body>"+
                "Расстояние от "+from_city+" до "+to_city+" : "+String.valueOf(q.getSingleResult().getDistance())+
                "<br><br><br>  <a class=\"textlink\" href=\"javascript:history.back()\">Go Back</a> </body>"+"</html>";}




    /*        try {
                x=entityManager.createQuery(criteriaQ).getSingleResult();

            } catch (NoResultException e) {
                URI targetURIForRedirection = URI.create("http://127.0.0.1:8080/distance/index.html1");
                Response.seeOther(targetURIForRedirection).build();
            }
            catch (NullPointerException e2) {
                URI targetURIForRedirection = URI.create("http://127.0.0.1:8080/distance/index.html2");
                Response.seeOther(targetURIForRedirection).build();
            }

        if (x.equals(null)) {
            URI targetURIForRedirection = URI.create("http://127.0.0.1:8080/distance/index.html3");
            Response.seeOther(targetURIForRedirection).build();
        }*/


                //entityManager.createQuery(criteriaQ).getSingleResult();
    }

    private String CrowFlight(String from_city,String to_city) {
        TypedQuery<City> q = entityManager.createQuery("select u from City u where u.name in (:from_city,:to_city)", City.class)
                .setParameter("from_city",from_city)
                .setParameter("to_city",to_city);

        List<City> list = null;
        list = q.getResultList();
        if (list.size()<2) {return "<html><head><meta charset=\"utf-8\"> </head> <body>В базе данных не хватает данных по координатам городов</body> </html>";}

        else {
            double lat1=list.get(0).getLatitude();
            double lon1=list.get(0).getLongitude();
            double lat2=list.get(1).getLatitude();
            double lon2=list.get(1).getLongitude();

            /*double theta = x2 - y2;
            double dist = Math.sin(Math.toRadians(x1)) * Math.sin(Math.toRadians(x2)) + Math.cos(Math.toRadians(x1)) * Math.cos(Math.toRadians(x2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            dist = dist * 1.609344;  // перевод в километры*/

            double dLat = Math.toRadians(lat2-lat1);
            double dLon = Math.toRadians(lon2-lon1);
            double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                    Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                            Math.sin(dLon/2) * Math.sin(dLon/2);
            double dist = 2 * Math.asin(Math.sqrt(a)) * 6.371 * 1000;  // Radius of EArth = 6.371 km

            NumberFormat formatter = new DecimalFormat("###.00");
            String result_distance = formatter.format(dist);

            return "<html><head><meta charset=\"utf-8\"> </head> <body>"+
                    "Расстояние от "+from_city+" до "+to_city+" : "+result_distance+"  км"+
                    "</body>"+"</html>";

                     }
    }

}
