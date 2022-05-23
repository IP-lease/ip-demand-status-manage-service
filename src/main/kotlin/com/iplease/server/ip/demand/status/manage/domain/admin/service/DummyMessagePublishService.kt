package com.iplease.server.ip.demand.status.manage.domain.admin.service

import com.iplease.server.ip.demand.status.manage.infra.message.service.MessagePublishService
import org.springframework.stereotype.Service

@Service
class DummyMessagePublishService: MessagePublishService {
    override fun publish(routingKey: String, message: String) {
        println("publish: routingKey=$routingKey, message=$message")
    }
}