package demo;

import net.jawr.web.JawrConstant;
import net.jawr.web.servlet.JawrSpringController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class JawrConfiguration {

    @Value("${jawr.config-location}")
    private String configLocation;

    @Bean
    public JawrSpringController jawrJsController() {
        return jawrBaseController(JawrConstant.JS_TYPE);
    }

    @DependsOn("jawrBinaryController")
    @Bean
    public JawrSpringController jawrCssController() {
        return jawrBaseController(JawrConstant.CSS_TYPE);
    }

    @Bean(name = "jawrBinaryController")
    public JawrSpringController jawrBinaryController() {
        return jawrBaseController(JawrConstant.BINARY_TYPE);
    }

    protected JawrSpringController jawrBaseController(String type) {
        JawrSpringController controller = new JawrSpringController();
        controller.setConfigLocation(configLocation);
        controller.setType(type);
        return controller;
    }

    @Bean
    public HandlerMapping handlerMapping() {
        final SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        final Map<String, Object> urlMap = new HashMap<String, Object>();
        urlMap.put("/**/**.png", jawrBinaryController());
        urlMap.put("/**/**.jpg", jawrBinaryController());
        urlMap.put("/**/**.jpeg", jawrBinaryController());
        urlMap.put("/**/**.gif", jawrBinaryController());
        urlMap.put("/**/**.ico", jawrBinaryController());
        urlMap.put("/**/**.woff", jawrBinaryController());
        urlMap.put("/**/**.woff2", jawrBinaryController());
        urlMap.put("/**/**.ttf", jawrBinaryController());
        urlMap.put("/**/**.eot", jawrBinaryController());
        urlMap.put("/**/**.svg", jawrBinaryController());

        urlMap.put("/**/**.js", jawrJsController());
        urlMap.put("/**/**.js.map", jawrJsController());

        urlMap.put("/**/**.css", jawrCssController());
        urlMap.put("/**/**.css.map", jawrCssController());

        mapping.setUrlMap(urlMap);
        mapping.setOrder(Integer.MIN_VALUE);

        return mapping;
    }
}
