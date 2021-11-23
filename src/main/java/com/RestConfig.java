package com;

import io.dropwizard.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class RestConfig extends Configuration {
    static String accessTokenSecret = "";
    static String consumerSecret = "";
    String filepath = "twitter4j.yml";
    String consumerKey = "";
    String accessToken = "";
    Properties properties = new Properties();
    FileInputStream fileInputStream;

    {
        try {
            fileInputStream = new FileInputStream(filepath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        accessTokenSecret = properties.getProperty("accessTokenSecret");
        consumerSecret = properties.getProperty("consumerSecret");
        consumerKey = properties.getProperty("consumerKey");
        accessToken = properties.getProperty("accessToken");
    }

    public ConfigurationBuilder configurationBuilder() {
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.setDebugEnabled(true)
                .setOAuthConsumerKey(consumerKey)
                .setOAuthConsumerSecret(consumerSecret)
                .setOAuthAccessToken(accessToken)
                .setOAuthAccessTokenSecret(accessTokenSecret);
        return configurationBuilder;
    }

}
