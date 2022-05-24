package com.iplease.server.ip.demand.status.manage.domain.status.service

import com.iplease.server.ip.demand.status.manage.domain.status.data.dto.IpDemandStatusDto
import com.iplease.server.ip.demand.status.manage.global.status.data.table.IpDemandStatusTable
import com.iplease.server.ip.demand.status.manage.global.status.data.type.DemandStatusType
import com.iplease.server.ip.demand.status.manage.global.status.repository.IpDemandStatusRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Service
class IpDemandStatusManageServiceImpl(
    private val ipDemandStatusRepository: IpDemandStatusRepository
): IpDemandStatusManageService {
    override fun reject(demandUuid: Long): Mono<IpDemandStatusDto> = changeDemandStatusByUuid(demandUuid, DemandStatusType.REJECT)
    override fun accept(demandUuid: Long): Mono<IpDemandStatusDto> = changeDemandStatusByUuid(demandUuid, DemandStatusType.ACCEPT)

    private fun changeDemandStatusByUuid(demandUuid: Long, status: DemandStatusType) =
        ipDemandStatusRepository.findByDemandUuid(demandUuid)
            .flatMap { checkExists(it) }
            .flatMap { checkStatus(it, status) }
            .map { it.copy(status = status) }
            .flatMap { ipDemandStatusRepository.save(it) }
            .map { it.toDto() }

    private fun checkExists(table: IpDemandStatusTable?): Mono<IpDemandStatusTable> =
        table.let { it?.toMono() ?:Mono.error(IllegalArgumentException("demand status table is null")) }

    private fun checkStatus(table: IpDemandStatusTable, tobe: DemandStatusType): Mono<IpDemandStatusTable> =
            table.toMono()
                .flatMap { it.status.canChangeTo(tobe) }
                .map { table } //이미 line26에서 null checking 을 완료하였다.

    private fun IpDemandStatusTable.toDto() = IpDemandStatusDto(
        uuid = uuid,
        demandUuid = demandUuid,
        status = status
    )
}