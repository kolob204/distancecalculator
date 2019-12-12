package com.webservice.app;

import com.webservice.rest.ResourceService;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.jsp.JspMvcFeature;


public class Application {

/* extends ResourceConfig {
    public Application() {
        packages("com.webservice;com.webservice.rest");

        register(JspMvcFeature.class);
        register(MultiPartFeature.class);

        property(JspMvcFeature.TEMPLATE_BASE_PATH, "/WEB-INF/jsp");
        property("jersey.config.servlet.filter.staticContentRegex","(/(jsp|js|css)/?.*)|(/.*\\.jsp)|(/WEB-INF/jsp/.*\\.jsp)");

    }*/

}
