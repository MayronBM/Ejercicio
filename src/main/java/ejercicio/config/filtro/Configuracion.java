package ejercicio.config.filtro;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@Order(1)
public class Configuracion {

    @Value("${jwt.secret}")
    private String secret;

    @Bean
    public FilterRegistrationBean jwtFilter() {
        FilterRegistrationBean filter = new FilterRegistrationBean();
        filter.setFilter(new Filtro(secret));
        filter.addUrlPatterns("/api/v1/blog/restricted");
        return filter;
    }
}
