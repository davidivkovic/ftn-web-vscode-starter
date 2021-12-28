package config;

import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;

import org.glassfish.hk2.api.Immediate;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;

import controllers.ProductsController;
import services.ProductsService;

@ApplicationPath("/api")
public class JerseyConfig extends ResourceConfig {
    
    @Inject
    public JerseyConfig(ServiceLocator locator) {
        ServiceLocatorUtilities.enableImmediateScope(locator);
        
        packages("controllers");

        // Enable LoggingFilter & output entity.     
        registerInstances(new LoggingFilter(Logger.getLogger(JerseyConfig.class.getName()), true));
    
        // Dependency injection
        register(ProductsService.class);
        
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bindAsContract(ProductsService.class).in(Immediate.class);
            }
        });
    }
}