package ni.com.userApi.support.configuration;

import ni.com.userApi.support.security.Filter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

@org.springframework.context.annotation.Configuration
@Order(1)
public class Configuration {

    @Value("${jwt.secret}")
    private String secret;

    @Bean
    public FilterRegistrationBean jwtFilter() {
        FilterRegistrationBean filter = new FilterRegistrationBean();
        filter.setFilter(new Filter(secret));
        filter.addUrlPatterns("/blog/restricted");
        return filter;
    }
}
