package com.iplease.server.ip.demand.status.manage.domain.status.listener

import com.iplease.lib.messa.error.type.IpDemandErrorTypeV1
import com.iplease.lib.messa.event.data.ip.demand.status.IpDemandStatusRejectEvent
import com.iplease.lib.messa.event.type.IpDemandEventTypeV1
import com.iplease.server.ip.demand.status.manage.domain.status.service.IpDemandStatusManageService
import com.iplease.server.ip.demand.status.manage.infra.message.gateway.Gateway
import com.iplease.server.ip.demand.status.manage.infra.message.listener.MessageListener
import com.iplease.server.ip.demand.status.manage.infra.message.listener.SimpleMessageListener
import com.iplease.server.ip.demand.status.manage.infra.message.service.MessagePublishService
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class IpDemandStatusRejectEventListener(
    private val messagePublishService: MessagePublishService,
    messageListenerGateway: Gateway<MessageListener>,
    private val ipDemandStatusManageService: IpDemandStatusManageService
): SimpleMessageListener<IpDemandStatusRejectEvent>(
    IpDemandStatusRejectEvent::class,
    IpDemandEventTypeV1.IP_DEMAND_STATUS_REJECT.routingKey,
    messagePublishService,
    messageListenerGateway
) {
    override fun handle(data: IpDemandStatusRejectEvent) {
        ipDemandStatusManageService.reject(data.demandUuid)
            .doOnError{ messagePublishService.publish(
                IpDemandErrorTypeV1.IP_DEMAND_STATUS_REJECT.routingKey,
                IpDemandStatusRejectEvent(data.demandUuid)
            ) }.onErrorResume { Mono.empty() }
            .block()
    }
}