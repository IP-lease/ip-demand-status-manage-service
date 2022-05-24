package com.iplease.server.ip.demand.status.manage.domain.admin.data.table

import com.iplease.server.ip.demand.status.manage.domain.admin.data.type.DemandStatusType
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table
data class IpDemandStatusTable (
    @Id val uuid: Long,
    val demandUuid: Long,
    val status: DemandStatusType
)