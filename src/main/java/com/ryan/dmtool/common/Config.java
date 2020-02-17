package com.ryan.dmtool.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.File;

@Slf4j
public class Config {

    public static PropertiesConfiguration cfg = null;
    public static PropertiesConfiguration jdbc = null;
    static {
        Configurations configs = new Configurations();
        try {
            cfg = configs.properties("config/config.properties");
            jdbc=  configs.properties("config/jdbc.properties");

            log.info(Config.cfg.getString("findDocumentSQL"));
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }

    }
}
