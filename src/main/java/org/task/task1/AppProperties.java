package org.task.task1;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
public class AppProperties {

    private final String driver;
    private final String url;
    private final String username;
    private final String password;

    public static AppProperties loadProperties() {
        return new AppProperties("liquibase.properties");

    }

    private AppProperties(String filename) {
        Properties properties = new Properties();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(filename)) {
            properties.load(is);
            driver = properties.getProperty("driver", "");
            url = properties.getProperty("url", "");
            username = properties.getProperty("username", "");
            password = properties.getProperty("password", "");

        } catch (IOException e) {
            throw new RuntimeException("Could not load " + filename, e);
        }
    }

    public String getDriver() {
        return driver;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
