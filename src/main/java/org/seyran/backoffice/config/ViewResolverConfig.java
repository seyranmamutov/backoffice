package org.seyran.backoffice.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.servlet.view.xml.MappingJackson2XmlView;

@Configuration
public class ViewResolverConfig {

  /**
   * Support both XML and JSON response for controller based on Accept header. 
   */
  @Bean
  public ViewResolver contentNegotiatingViewResolver() {
    ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();

    List<View> views = new ArrayList<>();
    views.add(new MappingJackson2XmlView());
    views.add(new MappingJackson2JsonView());

    resolver.setDefaultViews(views);
    return resolver;
  }
}
