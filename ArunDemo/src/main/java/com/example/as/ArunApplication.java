package com.example.as;


import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.yaml.SpringProfileDocumentMatcher;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;


@SpringBootApplication
@ComponentScan({"com.example.*"})
@EnableCircuitBreaker
public class ArunApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArunApplication.class, args);
	}

    @Bean
    @Profile("dev")
    public PropertySourcesPlaceholderConfigurer propertiesStage() {
    	return properties("dev");
    }
    
    @Bean
    @Profile("stage")
    public PropertySourcesPlaceholderConfigurer propertiesDev() {
    	return properties("stage");
    }
    
    @Bean
    @Profile("default")
    public PropertySourcesPlaceholderConfigurer propertiesDefault() {
    	return properties("default");
    
    }
   /**
    * Update custom specific yml file with profile configuration.
    * @param profile
    * @return
    */
    public static PropertySourcesPlaceholderConfigurer properties(String profile) {
       PropertySourcesPlaceholderConfigurer propertyConfig = null;
       YamlPropertiesFactoryBean yaml  = null;
       
       propertyConfig  = new PropertySourcesPlaceholderConfigurer();
       yaml = new YamlPropertiesFactoryBean();
       yaml.setDocumentMatchers(new SpringProfileDocumentMatcher(profile));// load profile filter.
       yaml.setResources(new ClassPathResource("env-config/test-service-config.yml"));
       propertyConfig.setProperties(yaml.getObject());
     
       return propertyConfig;
    }
}
