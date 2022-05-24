package com.iplease.server.ip.demand.status.manage.infra.message.gateway

import com.iplease.server.ip.demand.status.manage.infra.message.listener.MessageListener
import com.iplease.server.ip.demand.status.manage.infra.message.service.MessageSubscribeService
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class GatewayRabbitMqMessageSubscribeService: MessageSubscribeService, Gateway<MessageListener> {
    private val list = mutableSetOf<MessageListener>()
    private val logger = LoggerFactory.getLogger(this::class.java)

    @RabbitListener(queues = ["server.ip.demand.status.manage"])
    fun subscribe(message: Message) {
        try { subscribe(message.messageProperties.receivedRoutingKey, String(message.body)) }
        catch (ex: Throwable) { logger.error(ex.message) } //RabbitMQ 메세지 구독중 발생한 모든 오류는 로깅이후 무시한다. (무한loop 방지)
    }

    override fun subscribe(routingKey: String, message: String) {
        Flux.fromIterable(list)
            .map { it.subscribe(routingKey = routingKey, message = message) }
            .onErrorContinue{ ex, _ -> logger.error(ex.message) } //RabbitMQ 메세지 구독중 발생한 모든 오류는 로깅이후 무시한다. (무한loop 방지)
            .subscribe()
    }
    override fun add(handler: MessageListener) { list.add(handler) }
}