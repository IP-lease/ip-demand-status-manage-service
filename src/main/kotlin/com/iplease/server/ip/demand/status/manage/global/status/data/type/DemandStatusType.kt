package com.iplease.server.ip.demand.status.manage.global.status.data.type

import reactor.core.publisher.Mono

enum class DemandStatusType(
    vararg val canChangeTo: DemandStatusType
) {
    //IP할당에 실패하였을 경우 FAIL
    FAIL(),
    //IP할당이 완료되었을 경우 SUCCESS
    SUCCESS(),
    //관리자가 수락하였을 경우 ACCEPT
    ACCEPT(SUCCESS, FAIL),
    //관리자가 거절하였을 경우 REJECT
    REJECT(FAIL),
    //관리자가 확인하였을 경우 IN_PROGRESS
    IN_PROGRESS(REJECT, ACCEPT),
    //상태가 처음 등록되었을 경우 CREATE
    CREATE(IN_PROGRESS);

    fun canChangeTo(tobe: DemandStatusType) =
        if(canChangeTo.contains(tobe)) Mono.just(Unit)
        else Mono.error(UnsupportedOperationException("$this can't change to $tobe"))
}
