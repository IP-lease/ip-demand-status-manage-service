package com.iplease.server.ip.demand.status.manage.infra.message.service

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.amqp.rabbit.core.RabbitTemplate

class RabbitMqMessagePublishService(
    val rabbitTemplate: RabbitTemplate
): MessagePublishService {
    companion object { const val EXCHANGE_NAME = "iplease.event" }
    override fun <T: Any>  publish(routingKey: String, data: T) {
        jacksonObjectMapper().registerModule(JavaTimeModule())
            .writeValueAsString(data)
            .let { rabbitTemplate.convertAndSend(EXCHANGE_NAME, routingKey, it) }
            .let { data }
    }
}