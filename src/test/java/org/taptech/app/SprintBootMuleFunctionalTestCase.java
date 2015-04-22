package org.taptech.app;

import org.mule.api.config.ConfigurationBuilder;
import org.mule.config.spring.SpringXmlConfigurationBuilder;
import org.mule.tck.junit4.FunctionalTestCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.StaticApplicationContext;

/**
 * Created by tap on 4/22/15.
 */
public class SprintBootMuleFunctionalTestCase extends FunctionalTestCase {

    @Autowired
    private ApplicationContext context;

    protected ConfigurationBuilder getBuilder() throws Exception {

        String configResources = this.getConfigFile();
        StaticApplicationContext staticApplicationContext = new StaticApplicationContext(context);
        staticApplicationContext.refresh();
        SpringXmlConfigurationBuilder configurationBuilder = null;
        if(configResources != null) {
            if(configResources.contains(",")) {
                throw new RuntimeException("Do not use this method when the config is composed of several files. Use getConfigFiles method instead.");
            } else {
                configurationBuilder = new SpringXmlConfigurationBuilder(configResources);
            }
        } else {
            String[] multipleConfigResources = this.getConfigFiles();
            configurationBuilder = new SpringXmlConfigurationBuilder(multipleConfigResources);
        }
        configurationBuilder.setParentContext(staticApplicationContext);
        return configurationBuilder;
    }
}
