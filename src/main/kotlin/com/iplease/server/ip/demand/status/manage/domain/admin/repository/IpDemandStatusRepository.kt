package com.iplease.server.ip.demand.status.manage.domain.admin.repository

import com.iplease.server.ip.demand.status.manage.domain.admin.data.table.IpDemandStatusTable
import org.springframework.data.r2dbc.repository.R2dbcRepository
import reactor.core.publisher.Mono

interface IpDemandStatusRepository: R2dbcRepository<IpDemandStatusTable, Long> {
    fun findByDemandUuid(demandUuid: Long): Mono<IpDemandStatusTable>
}