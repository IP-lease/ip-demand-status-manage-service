package com.iplease.server.ip.demand.status.manage.domain.admin.service

import reactor.core.publisher.Mono

interface IpDemandAdminService {
    fun accept(demandUuid: Long): Mono<Unit>
    fun reject(demandUuid: Long): Mono<Unit>

}
