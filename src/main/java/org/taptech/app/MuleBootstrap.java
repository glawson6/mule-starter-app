package org.taptech.app;


import org.mule.api.MuleContext;
import org.mule.api.MuleException;
import org.mule.api.config.ConfigurationException;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.config.spring.SpringXmlConfigurationBuilder;
import org.mule.context.DefaultMuleContextFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.StaticApplicationContext;

/**
 * Created by tap on 4/18/15.
 */
@ComponentScan
@Configuration
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class,DispatcherServletAutoConfiguration.class,
        WebMvcAutoConfiguration.class, EmbeddedServletContainerAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
public class MuleBootstrap implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(MuleBootstrap.class);

    @Autowired
    private ApplicationContext context;

    @Override
    public void run(String... strings) throws Exception {
        DefaultMuleContextFactory muleContextFactory = new DefaultMuleContextFactory();
        SpringXmlConfigurationBuilder configBuilder = null;
        try {
            StaticApplicationContext staticApplicationContext = new StaticApplicationContext(context);
            configBuilder = new SpringXmlConfigurationBuilder("mule-config.xml");
            staticApplicationContext.refresh();
            configBuilder.setParentContext(staticApplicationContext);
            MuleContext muleContext = muleContextFactory.createMuleContext(configBuilder);
            muleContext.start();
            log.info("Started Mule!");
        } catch (Exception e) {
            log.error("Error starting Mule...",e);
        }
    }

    public static void main(String... args) {
        log.info("Starting SpringApplication...");
        SpringApplication app = new SpringApplication(MuleBootstrap.class);
        app.setWebEnvironment(false);
        app.run(args);
        log.info("SpringApplication has started...");
    }


}
