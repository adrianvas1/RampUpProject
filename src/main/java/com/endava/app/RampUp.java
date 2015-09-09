package com.endava.app;

import com.endava.config.ExampleServiceConfiguration;
import com.endava.config.SpringConfig;
import com.endava.health.DatabaseHealthCheck;
import com.endava.resources.DocumentResource;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class RampUp extends Service<ExampleServiceConfiguration> {

    public static void main(String[] args) throws Exception {
        new RampUp().run(args);
    }

    @Override
    public void initialize(Bootstrap<ExampleServiceConfiguration> bootstrap) {
        bootstrap.setName("Ramp up");
    }

    @Override
    public void run(ExampleServiceConfiguration conf, Environment env) throws Exception {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        // new resource
        DocumentResource helloResource = (DocumentResource) context.getBean("documentResource");
        env.addResource(helloResource);
        // added health check
        DatabaseHealthCheck healthCheck = (DatabaseHealthCheck) context.getBean("databaseHealthCheck");
        env.addHealthCheck(healthCheck);
    }
}