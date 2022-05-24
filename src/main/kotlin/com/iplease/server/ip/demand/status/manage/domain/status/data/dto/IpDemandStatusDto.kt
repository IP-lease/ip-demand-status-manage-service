package com.iplease.server.ip.demand.status.manage.domain.status.data.dto

import com.iplease.server.ip.demand.status.manage.global.status.data.type.DemandStatusType

class IpDemandStatusDto(
    val uuid: Long,
    val demandUuid: Long,
    val status: DemandStatusType
)