package com.workday.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;

@Configuration
@ConfigurationProperties
@PropertySource("classpath:application.yml")
public class PropertyConfig {

    private Auth auth;
    private Config config;

    public Auth getAuth() {
        return auth;
    }

    public void setAuth(Auth auth) {
        this.auth = auth;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public static class Auth {
        private String username;
        private String password;
        private String passwordType;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPasswordType() {
            return passwordType;
        }

        public void setPasswordType(String passwordType) {
            this.passwordType = passwordType;
        }
    }

    public static class Config {

        private String version;
        private String defaultUri;

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getDefaultUri() {
            return defaultUri;
        }

        public void setDefaultUri(String defaultUri) {
            this.defaultUri = defaultUri;
        }
    }
}
