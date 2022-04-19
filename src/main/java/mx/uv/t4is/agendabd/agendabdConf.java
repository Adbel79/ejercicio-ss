package mx.uv.t4is.agendabd;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class agendabdConf {
    @Bean
    public XsdSchema agendabdSchema(){
        return new SimpleXsdSchema(new ClassPathResource("agendabd.xsd"));
    }

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(
            ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }
    
    @Bean(name = "agendabd")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema agendabdSchema) {
        DefaultWsdl11Definition wsdl = new DefaultWsdl11Definition();
        wsdl.setPortTypeName("agendabdPort");
        wsdl.setLocationUri("/ws");
        wsdl.setTargetNamespace("https://t4is.uv.mx/agendabd");
        wsdl.setSchema(agendabdSchema);
        return wsdl;
    }

}
