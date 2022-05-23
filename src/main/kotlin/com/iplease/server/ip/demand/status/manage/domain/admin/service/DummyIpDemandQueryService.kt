package com.iplease.server.ip.demand.status.manage.domain.admin.service

import com.iplease.server.ip.demand.status.manage.domain.admin.data.dto.IpDemandDto
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import java.time.LocalDate

@Service //TODO 나중에 gRPC 구현체 만들어서 적용하기
class DummyIpDemandQueryService: IpDemandQueryService {
    override fun getByUuid(demandUuid: Long): Mono<IpDemandDto> = IpDemandDto(
        0, 0, 0,
        LocalDate.MIN, LocalDate.MAX,
        127, 0, 0, 1
    ).toMono()

    override fun existsByUuid(demandUuid: Long): Mono<Boolean> = true.toMono()
}