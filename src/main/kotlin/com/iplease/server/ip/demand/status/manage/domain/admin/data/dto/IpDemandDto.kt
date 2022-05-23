package com.iplease.server.ip.demand.status.manage.domain.admin.data.dto

import java.time.LocalDate

class IpDemandDto (
    val demandUuid: Long,
    val issuerUuid: Long,
    val assignerUuid: Long,
    val assignedAt: LocalDate,
    val expireAt: LocalDate,
    val ipFirst: Int,
    val ipSecond: Int,
    val ipThird: Int,
    val ipFourth: Int
)