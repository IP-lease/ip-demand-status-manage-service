package com.iplease.server.ip.demand.status.manage.global.error.type.listener

import com.iplease.lib.messa.error.type.IpDemandErrorTypeV1
import com.iplease.lib.messa.event.data.ip.demand.status.IpDemandStatusRejectEvent
import com.iplease.lib.messa.event.type.IpDemandEventTypeV1
import com.iplease.server.ip.demand.status.manage.domain.admin.data.type.DemandStatusType
import com.iplease.server.ip.demand.status.manage.domain.admin.repository.IpDemandStatusRepository
import com.iplease.server.ip.demand.status.manage.infra.message.gateway.Gateway
import com.iplease.server.ip.demand.status.manage.infra.message.listener.MessageListener
import com.iplease.server.ip.demand.status.manage.infra.message.listener.SimpleMessageListener
import com.iplease.server.ip.demand.status.manage.infra.message.service.MessagePublishService
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class IpDemandStatusRejectListener(
    private val messagePublishService: MessagePublishService,
    messageListenerGateway: Gateway<MessageListener>,
    private val ipDemandStatusRepository: IpDemandStatusRepository
): SimpleMessageListener<IpDemandStatusRejectEvent>(
    IpDemandStatusRejectEvent::class,
    IpDemandEventTypeV1.IP_DEMAND_STATUS_REJECT.routingKey,
    messagePublishService,
    messageListenerGateway
) {
    override fun handle(data: IpDemandStatusRejectEvent) {
        ipDemandStatusRepository.findByDemandUuid(data.demandUuid)
            .map { it.copy(status = DemandStatusType.REJECT) }
            .flatMap { ipDemandStatusRepository.save(it) }
            .doOnError{ messagePublishService.publish(
                IpDemandErrorTypeV1.IP_DEMAND_STATUS_REJECT.routingKey,
                IpDemandStatusRejectEvent(data.demandUuid)
            ) }.onErrorResume { Mono.empty() }
            .block()
    }
}