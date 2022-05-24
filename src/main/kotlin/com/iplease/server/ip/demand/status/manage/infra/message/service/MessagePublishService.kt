package com.iplease.server.ip.demand.status.manage.infra.message.service

interface MessagePublishService {
    fun <T: Any> publish(routingKey: String, data: T)
}