package com.iplease.server.ip.demand.status.manage.domain.status.service

import com.iplease.server.ip.demand.status.manage.domain.status.data.dto.IpDemandStatusDto
import reactor.core.publisher.Mono

interface IpDemandStatusManageService {
    fun reject(demandUuid: Long): Mono<IpDemandStatusDto>
    fun accept(demandUuid: Long): Mono<IpDemandStatusDto>
    fun rollbackStatus(demandUuid: Long): Mono<IpDemandStatusDto>
}