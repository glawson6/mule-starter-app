package com.taptech.esper.util;

import com.espertech.esper.client.Configuration;

/**
 * Created by tap on 5/9/15.
 */
public class SpringEsperConfigLoader {

    private String configFileLocation;
    private Configuration configuration = new Configuration();

    public void init(){
        configuration.configure(configFileLocation);
    }

    public String getConfigFileLocation() {
        return configFileLocation;
    }

    public void setConfigFileLocation(String configFileLocation) {
        this.configFileLocation = configFileLocation;
    }
}
