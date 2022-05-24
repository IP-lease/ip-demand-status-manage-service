package com.iplease.server.ip.demand.status.manage.infra.message.listener

interface MessageListener {
    fun subscribe(routingKey: String, message: String)
}
