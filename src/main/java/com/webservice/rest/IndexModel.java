package com.webservice.rest;


import com.webservice.entity.City;
import com.webservice.repository.CityRepo;
import org.eclipse.jetty.servlet.ServletContextHandler;

import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.Viewable;
import org.glassfish.jersey.server.mvc.jsp.JspMvcFeature;
import org.glassfish.jersey.server.mvc.mustache.MustacheMvcFeature;
import org.mortbay.jetty.handler.ContextHandler;
import org.mortbay.jetty.servlet.ServletHolder;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Context;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Path("/")
public class IndexModel  {

    @Inject
    private CityRepo cityRepo;

/*
    public IndexModel() {
        packages("com.webservice.rest");

        this.packages(IndexModel.class.getPackage().getName()).register(JspMvcFeature.class);
        this.packages(IndexModel.class.getPackage().getName()).register(MultiPartFeature.class);
        //      register(MultiPartFeature.class);
        //      register(JspMvcFeature.class);
        //register(MustacheMvcFeature.class);

        property(JspMvcFeature.TEMPLATE_BASE_PATH, "/WEB-INF/jsp");

        //property("jersey.config.server.mvc.templateBasePath.jsp", "/WEB-INF/jsp");
        //property(MustacheMvcFeature.TEMPLATE_BASE_PATH, "/templates");
    }*/


  /*  @GET
    public Response redirectToIndexFile (@Context UriInfo uriDetails) {
        URI getAbsolutePath = uriDetails.getAbsolutePath();
        return Response.seeOther(URI.create(getAbsolutePath+"index.html")).build();
    }*/

    @GET
    public Viewable redirectToIndexFile(@Context HttpServletRequest request,
                                        @Context HttpServletResponse response) throws Exception {

        List<City> list = cityRepo.getAll();
        request.setAttribute("cities2", list);

        return new Viewable("/index.jsp",null);
    }


}  // end  of class  IndexModel
