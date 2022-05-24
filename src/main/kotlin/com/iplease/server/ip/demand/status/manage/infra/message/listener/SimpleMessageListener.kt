package com.iplease.server.ip.demand.status.manage.infra.message.listener

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.iplease.lib.messa.error.data.global.WrongPayloadError
import com.iplease.lib.messa.error.type.GlobalErrorTypeV1
import com.iplease.server.ip.demand.status.manage.infra.message.gateway.Gateway
import com.iplease.server.ip.demand.status.manage.infra.message.service.MessagePublishService
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import kotlin.reflect.KClass
import kotlin.reflect.cast

abstract class SimpleMessageListener<T: Any>(
    private val type: KClass<T>,
    private val routingKey: String,
    private val messagePublishService: MessagePublishService,
    messageSubscribeService: Gateway<MessageListener>
): MessageListener {
    init { messageSubscribeService.add(this) }

    override fun subscribe(routingKey: String, message: String) {
        if(routingKey != this.routingKey) return
        parse(message)
            .map { type.cast(it) }
            .map { handle(it) }
            .onErrorReturn(Unit)
            .block()
    }

    private fun parse(message: String) =
        jacksonObjectMapper().registerModule(JavaTimeModule())
            .toMono()
            .map { it.readValue(message, type.java) }
            .doOnError{ messagePublishService.publish(GlobalErrorTypeV1.WRONG_PAYLOAD.routingKey, WrongPayloadError(it.message!!)) }
            .onErrorResume { Mono.empty() }

    abstract fun <T> handle(it: T)
}