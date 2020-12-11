package com.example.jupiter.db;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
public class MySQLDBUtil {
    private static final String INSTANCE = "twitchgame.cgaweydjy4xs.us-east-2.rds.amazonaws.com";
    private static final String PORT_NUM = "3306";
    private static final String DB_NAME = "jupiter";
    public static String getMySQLAddress() throws IOException {
        Properties prop = new Properties();
        String propFileName = "config.properties";

        // read file as stream <= avoid cache overflow (file is too large)
        // read data from file part by part
        InputStream inputStream = MySQLDBUtil.class.getClassLoader().getResourceAsStream(propFileName);
        // convert to hash table
        prop.load(inputStream);

        String username = prop.getProperty("user");
        String password = prop.getProperty("password");
        return String.format("jdbc:mysql://%s:%s/%s?user=%s&password=%s&autoReconnect=true&serverTimezone=UTC&createDatabaseIfNotExist=true",
                INSTANCE, PORT_NUM, DB_NAME, username, password);
    }
}