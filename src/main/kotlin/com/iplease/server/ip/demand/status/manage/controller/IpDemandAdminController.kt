package com.iplease.server.ip.demand.status.manage.controller

import com.iplease.server.ip.demand.status.manage.service.IpDemandAdminService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController("/api/v1/ip/demand/admin")
class IpDemandAdminController(
    private val ipDemandAdminService: IpDemandAdminService
) {
    //IP 할당신청 수락
    @PostMapping("/{demandUuid}")
    fun accept(@PathVariable demandUuid: Long): ResponseEntity<Unit> {
        //IP할당 신청성공 Message 발행
        ipDemandAdminService.accept(demandUuid)
        return ResponseEntity.ok().build()
    }

    //IP 할당신청 거절
    @DeleteMapping("/{demandUuid}")
    fun reject(@PathVariable demandUuid: Long): ResponseEntity<Unit> {
        //IP할당 신청실패 Message 발행
        ipDemandAdminService.reject(demandUuid)
        return ResponseEntity.ok().build()
    }
}