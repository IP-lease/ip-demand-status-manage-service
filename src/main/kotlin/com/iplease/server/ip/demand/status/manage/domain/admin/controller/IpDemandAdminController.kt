package com.iplease.server.ip.demand.status.manage.domain.admin.controller

import com.iplease.server.ip.demand.status.manage.domain.admin.service.IpDemandAdminService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/ip/demand/admin")
class IpDemandAdminController(
    private val ipDemandAdminService: IpDemandAdminService
) {
    //IP 할당신청 수락
    //IP할당 신청성공 Message 발행
    @PostMapping("/{demandUuid}")
    fun accept(@PathVariable demandUuid: Long) =
        ipDemandAdminService.accept(demandUuid)
            .map { ResponseEntity.ok(Unit) }

    //IP 할당신청 거절
    //IP할당 신청실패 Message 발행
    @DeleteMapping("/{demandUuid}")
    fun reject(@PathVariable demandUuid: Long) =
        ipDemandAdminService.reject(demandUuid)
            .map { ResponseEntity.ok(Unit) }
}