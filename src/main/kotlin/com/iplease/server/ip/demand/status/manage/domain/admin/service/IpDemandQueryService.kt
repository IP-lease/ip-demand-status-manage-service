package com.iplease.server.ip.demand.status.manage.domain.admin.service

import com.iplease.server.ip.demand.status.manage.domain.admin.data.dto.IpDemandDto
import reactor.core.publisher.Mono

//TODO 추후 Global로
interface IpDemandQueryService {
    fun getByUuid(demandUuid: Long): Mono<IpDemandDto>
    fun existsByUuid(demandUuid: Long): Mono<Boolean>
}