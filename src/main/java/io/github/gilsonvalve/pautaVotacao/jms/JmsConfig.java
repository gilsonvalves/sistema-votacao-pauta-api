package io.github.gilsonvalve.pautaVotacao.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQConnectionFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;

public class JmsConfig {


    private String brokerUrl;

    private String user;

    private String password;

    @Bean
    public ActiveMQConnectionFactory connectionFactory(){
        if ( "".equals(user) ) {
            ActiveMQConnectionFactory acmq = new ActiveMQConnectionFactory(brokerUrl);
            acmq.setTrustAllPackages(true);
            return acmq;
        }
        ActiveMQConnectionFactory acmq = new ActiveMQConnectionFactory(user, password, brokerUrl);
        acmq.setTrustAllPackages(true);
        return acmq;
    }

    @Bean
    public JmsListenerContainerFactory  jmsFactoryTopic(ConnectionFactory connectionFactory,
                                                        DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setPubSubDomain(true);
        return factory;
    }
    @Bean
    public JmsTemplate jmsTemplate() {
        return new JmsTemplate(connectionFactory());
    }

    @Bean
    public JmsTemplate jmsTemplateTopic() {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory());
        jmsTemplate.setPubSubDomain( true );
        return jmsTemplate;
    }
}
