package com.iplease.server.ip.demand.status.manage.global.status.repository

import com.iplease.server.ip.demand.status.manage.global.status.data.table.IpDemandStatusTable
import org.springframework.data.r2dbc.repository.R2dbcRepository
import reactor.core.publisher.Mono

interface IpDemandStatusRepository: R2dbcRepository<IpDemandStatusTable, Long> {
    fun findByDemandUuid(demandUuid: Long): Mono<IpDemandStatusTable>
}