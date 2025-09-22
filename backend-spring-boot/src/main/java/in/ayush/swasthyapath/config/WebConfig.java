package in.ayush.swasthyapath.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Map URL path /pdfs/** to the folder pdfs/ in the project root
        registry.addResourceHandler("/pdfs/**")
                .addResourceLocations("file:pdfs/");
    }
}
