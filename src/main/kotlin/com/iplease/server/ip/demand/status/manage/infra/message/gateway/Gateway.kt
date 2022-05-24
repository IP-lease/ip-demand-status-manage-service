package com.iplease.server.ip.demand.status.manage.infra.message.gateway

interface Gateway<T> {
    fun add(handler: T)
}