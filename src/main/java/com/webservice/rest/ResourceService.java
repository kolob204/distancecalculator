package com.webservice.rest;


import com.webservice.entity.Distance;
import com.webservice.registrator.DistanceRegistrator;
import com.webservice.repository.Distances;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.*;

import com.webservice.entity.City;
import com.webservice.registrator.CityRegistrator;
import com.webservice.repository.Cities;
import com.webservice.repository.CityRepo;
import com.webservice.repository.DistanceRepo;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import org.glassfish.jersey.server.mvc.Viewable;

import javax.inject.Inject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import javax.faces.bean.RequestScoped;

@RequestScoped
@Path("/service")
public class ResourceService   {


    @Inject
    private CityRegistrator cityRegistrator;

    @Inject
    private DistanceRegistrator distanceRegistrator;

    @Inject
    private CityRepo cityRepo;

    @Inject
    private DistanceRepo distanceRepo;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Viewable rootService() {
        Map<String, String> model = new HashMap<>();
        model.put("hello", "Hello");
        model.put("world", "World");
        return new Viewable("/listOfCities.jsp",model);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/status")
    public Response getStatus() {
        return Response.ok("{\"status\":\"Service Running...\"}").build();
    }

    @GET
    @Path("/getCities")
    @Produces(MediaType.APPLICATION_XML)
    public List<City> getCities() {
        return cityRepo.getAll();
    }

    @GET
    @Path("/getMatrix")
    @Produces(MediaType.APPLICATION_XML)
    public List<Distance> getDistance() {
        return distanceRepo.getAll();
    }

    @GET
    @Path("/getCities2")
    @Produces(MediaType.TEXT_HTML)
    public String getCities2() {
        List<City> list = cityRepo.getAll();
        StringBuilder SB = new StringBuilder();
        SB.append("<html><head>\n" +
                "    <meta charset=\"UTF-8\"> <link href=\"./../css/style.css\" rel=\"stylesheet\">\n" +
                "    <title>List of Cities</title>\n" +
                "</head><body>" +
                "<table class=\"table\">");

        for (City element: list) {
            SB.append("<tr><td>"+element.getId()+"</td>"+"<td>"+element.getName()+"</td></tr>");
        }
        SB.append("</table> <br><br><br>" +"<a class=\"textlink\" href=\"javascript:history.back()\">Go Back</a>"+
                "</body></html>");
        return SB.toString();
    }

  /*  try configure Mustache
  @GET
    @Path("/getCities3")
    @Template(name = "/cities")
    @Produces(MediaType.TEXT_HTML)
    public Map<String, Object> getCities3() {
        Map<String, Object> model = new HashMap<String, Object>();
        Iterable<City> list = cityRepo.getAll();

        model.put("cities", list);
    return model;
    }*/

    @GET
    @Path("/getCities3")
    @Produces(MediaType.TEXT_HTML)
    public Viewable getCities3(@Context HttpServletRequest request,
                               @Context HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        List<City> list = cityRepo.getAll();

        model.put("cities", list);
        request.setAttribute("cities2", list);

        model.put("variable_name", "variable_content");
        model.put("variable_name2",  1);

/*      try {
            request.setAttribute("cities", list);
                request.getRequestDispatcher("../listOfCities.jsp").forward(request, response);
        } catch (ServletException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/

        return  new Viewable("/listOfCities.jsp",model);

                //  work around
                //  getAbsolutePath = http://127.0.0.1:8080/distance/service/getCities3
                //  Response.seeOther(URI.create(getAbsolutePath+"/listOfCities.jsp")).build();
    }


    @GET
    @Path("/getDistance/{from_city}/{to_city}")
    @Produces(MediaType.TEXT_HTML)
    public String getDistance(@PathParam("from_city") String from_city,
                              @PathParam("to_city") String to_city) {
        String method="1";
        return distanceRepo.getByNames(from_city,to_city,method);
    }


    @POST
    @Path("/getDistance")
    @Produces(MediaType.TEXT_HTML)
    public String getDistanceFromForm(@FormParam("cityname1") String cityname1,
                                      @FormParam("cityname2")String cityname2,
                                      @FormParam("method") String method) {

        return distanceRepo.getByNames(cityname1, cityname2,method);
    }

    @POST
    @Path("/addcity")
    @Produces(MediaType.TEXT_HTML)
    public Response addCity(@FormParam("cityname")    String cityname,
                            @FormParam("latitude")    double latitude,
                            @FormParam("longitude")   double longitude) {

        cityRegistrator.create(new City(cityname,latitude,longitude));
        return Response.status(200)
                .entity("citi added! <br><br><br>  <a class=\"textlink\" href=\"javascript:history.back()\">Go Back</a> ")
                .build();
    }


    @POST
    @Path("/addcitiesfromfile")
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response addCitiesfromfile(@FormDataParam("file") InputStream uploadedInputStream,
                                      @FormDataParam("file") FormDataContentDisposition fileDetails )  throws JAXBException {

        String xmlFileString="";
        try {
            xmlFileString = IOUtils.toString(uploadedInputStream, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        JAXBContext jaxbContext = JAXBContext.newInstance(Cities.class);  // NOT City.class.  Cities!!   Common mistake
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        Cities cities = (Cities) jaxbUnmarshaller.unmarshal(new StringReader(xmlFileString));

        List<City> listofcity= cities.getCities();

        for(City element : listofcity)
        {
            cityRegistrator.create(new City(element.getName(),element.getLatitude(),element.getLongitude()));
        }

        return  Response.status(200).build();
    }

    @POST
    @Path("/addmatrixfromfile")
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response addmatrixfromfile(@FormDataParam("file") InputStream uploadedInputStream,
                                      @FormDataParam("file") FormDataContentDisposition fileDetails )  throws JAXBException {

        String xmlFileString="";
        try {
            xmlFileString = IOUtils.toString(uploadedInputStream, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        JAXBContext jaxbContext = JAXBContext.newInstance(Distances.class);  // NOT City.class.  Cities!!   Common mistake
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        Distances distances = (Distances) jaxbUnmarshaller.unmarshal(new StringReader(xmlFileString));

        List<Distance> listofDistance = distances.getDistances();

        for(Distance element : listofDistance)
        {
            distanceRegistrator.create(new Distance(element.getFrom_city(),element.getTo_city(),element.getDistance()));
        }

        return  Response.status(200).build();
    }

                //.entity("cities added!  <br><br><br>  <a class=\"textlink\" href=\"javascript:history.back()\">Go Back</a>  ")



     // END OF Method addCitiesfromfile





}  // END of class ResourceService
