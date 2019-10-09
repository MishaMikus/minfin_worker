package server;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;


@RestController
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        JdbcTemplateAutoConfiguration.class
})
@ComponentScan(basePackages = {"server"})
@Configuration
public class SpringBootApplication extends SpringBootServletInitializer {

    @Value("${server.tomcat.additional-tld-skip-patterns}")
    private static String prop ;

    private final Logger LOGGER = Logger.getLogger(SpringBootApplication.class);

    public static void main(String[] args) {
        prop = "*mchange-commons-java*.jar";
        new SpringApplication(SpringBootApplication.class).run(args);
    }

    @Bean
    public FilterRegistrationBean someFilterRegistration() {
        LOGGER.info("setup filter " + GeneralFilter.class.getCanonicalName());
        return new FilterRegistrationBean(new GeneralFilter());
    }

    @Bean
    public InternalResourceViewResolver viewResolver() {
        LOGGER.info("setup Resolver " + InternalResourceViewResolver.class.getCanonicalName());
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/jsp/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);
        LOGGER.info("setup JstlView " + JstlView.class.getCanonicalName());
        return resolver;
    }

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        int maxUploadSize = -1;
        multipartResolver.setMaxUploadSize(maxUploadSize);
        LOGGER.info("setup multipartResolver " + CommonsMultipartResolver.class.getCanonicalName());
        LOGGER.info("setup setMaxUploadSize '" + maxUploadSize + "'");
        return multipartResolver;
    }

}

