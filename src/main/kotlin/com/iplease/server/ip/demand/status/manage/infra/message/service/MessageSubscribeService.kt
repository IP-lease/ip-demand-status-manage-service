package com.iplease.server.ip.demand.status.manage.infra.message.service

interface MessageSubscribeService {
    fun subscribe(routingKey: String, message: String)
}