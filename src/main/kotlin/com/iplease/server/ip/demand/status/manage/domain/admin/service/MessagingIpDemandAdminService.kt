package com.iplease.server.ip.demand.status.manage.domain.admin.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.iplease.lib.messa.event.data.ip.demand.status.IpDemandStatusAcceptEvent
import com.iplease.lib.messa.event.data.ip.demand.status.IpDemandStatusRejectEvent
import com.iplease.lib.messa.event.type.IpDemandEventTypeV1
import com.iplease.server.ip.demand.status.manage.domain.admin.exception.UnknownDemandException
import com.iplease.server.ip.demand.status.manage.infra.message.service.MessagePublishService
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Service
class MessagingIpDemandAdminService(
    private val messagePublishService: MessagePublishService,
    private val ipDemandQueryService: IpDemandQueryService
): IpDemandAdminService {
    //만약 해당 UUID를 가진 IP신청이 없다면 Mono.error(UnknownDemandException) 을 반환한다
    //만약 해당 UUID를 가진 IP신청이 있다면 해당 신청정보를 통해 IpDemandStatusAcceptEvent를 발행한다.
    override fun accept(demandUuid: Long): Mono<Unit> =
        ipDemandQueryService.existsByUuid(demandUuid)
            .flatMap { isExists ->
                if(!isExists) Mono.error(UnknownDemandException(demandUuid)) //만약 해당 UUID를 가진 IP신청이 없다면 Mono.error(UnknownDemandException) 을 반환한다
                else ipDemandQueryService.getByUuid(demandUuid) //만약 해당 UUID를 가진 IP신청이 있다면 해당 신청정보를 통해 IpDemandStatusAcceptEvent를 발행한다.
            }.map { IpDemandStatusAcceptEvent(demandUuid, it.issuerUuid, it.assignerUuid, toIpString(it.ipFirst, it.ipSecond, it.ipThird, it.ipFourth)) } //Event 구성로직
            .map { jacksonObjectMapper().writeValueAsString(it) } //Event 변환로직
            .map { messagePublishService.publish(IpDemandEventTypeV1.IP_DEMAND_STATUS_ACCEPT.routingKey, it) } //Event 발행로직

    private fun toIpString(a: Int, b: Int, c: Int, d: Int) = "$a.$b.$c.$d"

    //만약 해당 UUID 가진 IP신청이 없다면 Mono.error(UnknownDemandException) 을 반환한다
    //만약 해당 UUID를 가진 IP신청이 있다면 IpDemandStatusRejectEvent를 발행한다.
    override fun reject(demandUuid: Long): Mono<Unit> =
        ipDemandQueryService.existsByUuid(demandUuid)
            .flatMap { isExists ->
                if (!isExists) Mono.error(UnknownDemandException(demandUuid)) //만약 해당 UUID를 가진 IP신청이 없다면 Mono.error(UnknownDemandException) 을 반환한다
                else IpDemandStatusRejectEvent(demandUuid).toMono() //만약 해당 UUID를 가진 IP신청이 있다면 IpDemandStatusRejectEvent를 발행한다. Event 구성로직
            }.map { jacksonObjectMapper().writeValueAsString(it) } //Event 변환로직
            .map { messagePublishService.publish(IpDemandEventTypeV1.IP_DEMAND_STATUS_REJECT.routingKey, it) } //Event 발행로직
}