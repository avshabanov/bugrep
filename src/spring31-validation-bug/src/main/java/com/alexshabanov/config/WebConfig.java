package com.alexshabanov.config;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import javax.annotation.PostConstruct;
import java.util.List;

@Configuration
@EnableWebMvc
//@ComponentScan(
//        basePackages="com.alexshabanov.controller",
//        useDefaultFilters = false,
//        includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Controller.class)
//)
@ComponentScan(basePackages = "com.alexshabanov")
public class WebConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private RequestMappingHandlerMapping requestHandlerMapping;

    @PostConstruct
    public void initHandlerMapping() {
        requestHandlerMapping.setAlwaysUseFullPath(true);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        final MappingJacksonHttpMessageConverter jacksonConverter = new MappingJacksonHttpMessageConverter();
        jacksonConverter.getObjectMapper().setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
		converters.add(jacksonConverter);
	}

    @Override
    public Validator getValidator() {
		return new LocalValidatorFactoryBean();
	}

	@Bean
	public ViewResolver viewResolver() {
		UrlBasedViewResolver viewResolver = new UrlBasedViewResolver();
		viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
}
