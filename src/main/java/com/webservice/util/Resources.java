package com.webservice.util;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.logging.Logger;

public class Resources {

    @Produces
    @PersistenceContext
    private EntityManager entityManager;
/*
    http://tomee.apache.org/jpa-concepts.html

-    Entities are managed by javax.persistence.EntityManager instance using persistence context.
-    Each EntityManager instance is associated with a persistence context.
-    Within the persistence context, the entity instances and their lifecycle are managed.
-    Persistence context defines a scope under which particular entity instances are created, persisted, and removed.
-    A persistence context is like a cache which contains a set of persistent entities , So once the transaction is finished, all persistent objects are detached from the EntityManager's persistence context and are no longer managed.

*/

    @Produces
    public Logger produceLog(InjectionPoint injectionPoint) {          //https://docs.jboss.org/weld/reference/latest/en-US/html/injection.html#d0e1662
        return Logger.getLogger(injectionPoint.getMember().getName());
    }

}
