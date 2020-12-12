package com.teams_mars._config;


//import com.example.demo.intercepter.LoginIntercepter;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


public class MvcConfig implements WebMvcConfigurer {

   /* public void addViewControllers(ViewControllerRegistry registry) {
        //将login.html映射到路径urlpath为："/"上
        registry.addViewController("/").setViewName("login");
    }*/
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

       // registry.addInterceptor(new LoginIntercepter()).addPathPatterns("/**").excludePathPatterns("/","/login");
    }
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource
                = new ReloadableResourceBundleMessageSource();

        messageSource.setBasenames("classpath:messages", "classpath:errorMessages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
    @Override
    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }




}
