package gov.cdc.nczeid.eip.transport;

import gov.cdc.nczeid.eip.filter.RequestCacheWrapperFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@SpringBootApplication
public class EIPTransportServices {

	public static void main(String[] args) {
		SpringApplication.run(EIPTransportServices.class, args);
	}

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**");//.allowedOrigins("http://localhost:9000");
            }
        };
    }
    @Bean
    public FilterRegistrationBean requestCacheFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new RequestCacheWrapperFilter());
        // In case you want the filter to apply to specific URL patterns only
        //registration.addUrlPatterns("/*");
        return registration;
    }

}
