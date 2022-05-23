package com.iplease.server.ip.demand.status.manage.infra.message.service

interface MessagePublishService {
    fun publish(routingKey: String, message: String)
}