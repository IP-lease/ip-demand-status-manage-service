package com.iplease.server.ip.demand.status.manage.domain.status.listener

import com.iplease.lib.messa.error.data.ip.demand.status.IpDemandStatusAcceptError
import com.iplease.lib.messa.error.type.IpDemandErrorTypeV1
import com.iplease.server.ip.demand.status.manage.domain.status.service.IpDemandStatusManageService
import com.iplease.server.ip.demand.status.manage.infra.message.gateway.Gateway
import com.iplease.server.ip.demand.status.manage.infra.message.listener.MessageListener
import com.iplease.server.ip.demand.status.manage.infra.message.listener.SimpleMessageListener
import com.iplease.server.ip.demand.status.manage.infra.message.service.MessagePublishService
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class IpDemandStatusAcceptErrorListener(
    messagePublishService: MessagePublishService,
    messageListenerGateway: Gateway<MessageListener>,
    private val ipDemandStatusManageService: IpDemandStatusManageService
): SimpleMessageListener<IpDemandStatusAcceptError>(
    IpDemandStatusAcceptError::class,
    IpDemandErrorTypeV1.IP_DEMAND_STATUS_ACCEPT.routingKey,
    messagePublishService,
    messageListenerGateway
) {
    override fun handle(data: IpDemandStatusAcceptError) {
        println("테스토스테스트")
        ipDemandStatusManageService.rollbackStatus(data.demandUuid)
            //TODO doOnError로 로깅로직 작성
            .onErrorResume { Mono.empty() }
            .block()
    }
}