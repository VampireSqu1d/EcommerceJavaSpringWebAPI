package com.cuauh.springbootecommerce.config;

import com.cuauh.springbootecommerce.entity.Country;
import com.cuauh.springbootecommerce.entity.Product;
import com.cuauh.springbootecommerce.entity.ProductCategory;
import com.cuauh.springbootecommerce.entity.State;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

    private final EntityManager entityManager;

    @Autowired
    public MyDataRestConfig(EntityManager theentityManager) {
        entityManager = theentityManager;
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        RepositoryRestConfigurer.super.configureRepositoryRestConfiguration(config, cors);

        HttpMethod[] theUnsupportedActions = {HttpMethod.DELETE, HttpMethod.PUT, HttpMethod.POST};

        //disable the methods for Product: PUT, POST, DELETE
        disableHttpMethods(Product.class, config, theUnsupportedActions);

        //disable the methods for ProductCategory: PUT, POST, DELETE
        disableHttpMethods(ProductCategory.class, config, theUnsupportedActions);

        //disable the methods for Country: PUT, POST, DELETE
        disableHttpMethods(Country.class, config, theUnsupportedActions);

        //disable the methods for State: PUT, POST, DELETE
        disableHttpMethods(State.class, config, theUnsupportedActions);

        // call an internal helper method
        exposeIds(config);

    }

    private void disableHttpMethods(Class theClass, RepositoryRestConfiguration config, HttpMethod[] theUnsupportedActions) {
        config.getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure((metadata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
                .withCollectionExposure((metadata, httpMethods) -> httpMethods.disable(theUnsupportedActions));
    }

    private void exposeIds(RepositoryRestConfiguration config) {
        // get entity ids
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

        List<Class> entitityClasses = new ArrayList<>();

        for (EntityType tempEntityType: entities) {
            entitityClasses.add(tempEntityType.getJavaType());
        }

        Class[] domainTypes = entitityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);
    }
}
