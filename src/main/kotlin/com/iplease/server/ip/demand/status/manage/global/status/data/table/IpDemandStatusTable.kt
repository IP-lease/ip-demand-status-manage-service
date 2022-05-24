package com.iplease.server.ip.demand.status.manage.global.status.data.table

import com.iplease.server.ip.demand.status.manage.global.status.data.type.DemandStatusType
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table
data class IpDemandStatusTable (
    @Id val uuid: Long,
    val demandUuid: Long,
    val status: DemandStatusType
)