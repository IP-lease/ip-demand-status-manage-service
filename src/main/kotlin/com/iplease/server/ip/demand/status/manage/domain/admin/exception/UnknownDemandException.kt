package com.iplease.server.ip.demand.status.manage.domain.admin.exception

data class UnknownDemandException(val demandUuid: Long) : RuntimeException("Unknown demand: $demandUuid")