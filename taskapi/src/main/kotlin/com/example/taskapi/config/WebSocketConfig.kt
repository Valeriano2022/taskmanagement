package com.example.taskapi.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.converter.CompositeMessageConverter
import org.springframework.messaging.converter.JacksonJsonMessageConverter
import org.springframework.messaging.converter.MessageConverter
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer
import tools.jackson.databind.json.JsonMapper

@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig : WebSocketMessageBrokerConfigurer {

    @Bean
    fun websocketJsonMapper(): JsonMapper =
        JsonMapper.builder()
            .findAndAddModules()
            .build()

    @Bean
    fun websocketJacksonConverter(): JacksonJsonMessageConverter =
        JacksonJsonMessageConverter(websocketJsonMapper())


    @Bean(name = ["brokerMessageConverter"])
    fun brokerMessageConverter(): CompositeMessageConverter =
        CompositeMessageConverter(listOf(websocketJacksonConverter()))

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry.addEndpoint("/ws")
            .setAllowedOriginPatterns("*")
            .withSockJS()
    }

    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
        registry.enableSimpleBroker("/topic")
        registry.setApplicationDestinationPrefixes("/app")
    }

    override fun configureMessageConverters(messageConverters: MutableList<MessageConverter>): Boolean {
        messageConverters.add(websocketJacksonConverter())
        return false
    }
}
