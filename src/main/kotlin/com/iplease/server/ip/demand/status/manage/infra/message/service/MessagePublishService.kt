package com.iplease.server.ip.demand.status.manage.infra.message.service

interface MessagePublishService {
    fun subscribe(routingKey: String, message: String)
}