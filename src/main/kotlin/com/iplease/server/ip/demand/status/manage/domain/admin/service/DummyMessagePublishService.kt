package com.iplease.server.ip.demand.status.manage.domain.admin.service

import com.iplease.server.ip.demand.status.manage.infra.message.service.MessagePublishService
import org.springframework.stereotype.Service

@Service
class DummyMessagePublishService: MessagePublishService {
    override fun <T : Any> publish(routingKey: String, data: T) {
        println("publish: routingKey=$routingKey, message=${data.toString()}")
    }
}