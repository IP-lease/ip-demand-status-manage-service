package com.iplease.server.ip.demand.status.manage.domain.status.listener

import com.iplease.lib.messa.error.data.ip.demand.status.IpDemandStatusAcceptError
import com.iplease.lib.messa.error.type.IpDemandErrorTypeV1
import com.iplease.lib.messa.event.data.ip.demand.status.IpDemandStatusAcceptEvent
import com.iplease.lib.messa.event.type.IpDemandEventTypeV1
import com.iplease.server.ip.demand.status.manage.domain.status.service.IpDemandStatusManageService
import com.iplease.server.ip.demand.status.manage.infra.message.gateway.Gateway
import com.iplease.server.ip.demand.status.manage.infra.message.listener.MessageListener
import com.iplease.server.ip.demand.status.manage.infra.message.listener.SimpleMessageListener
import com.iplease.server.ip.demand.status.manage.infra.message.service.MessagePublishService
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class IpDemandStatusAcceptListener(
    private val messagePublishService: MessagePublishService,
    messageListenerGateway: Gateway<MessageListener>,
    private val ipDemandStatusManageService: IpDemandStatusManageService
):  SimpleMessageListener<IpDemandStatusAcceptEvent>(
    IpDemandStatusAcceptEvent::class,
    IpDemandEventTypeV1.IP_DEMAND_STATUS_ACCEPT.routingKey,
    messagePublishService,
    messageListenerGateway
) {
    override fun handle(data: IpDemandStatusAcceptEvent) {
        ipDemandStatusManageService.accept(data.demandUuid)
            .doOnError{ messagePublishService.publish(
                IpDemandErrorTypeV1.IP_DEMAND_STATUS_ACCEPT.routingKey,
                IpDemandStatusAcceptError(data.demandUuid, data.issuerUuid, data.managerUuid, data.demandedIp)
            ) }.onErrorResume { Mono.empty() }
            .block()
    }
}