package com.iplease.server.ip.demand.status.manage.domain.admin.advice

import com.iplease.server.ip.demand.status.manage.domain.admin.exception.UnknownDemandException
import com.iplease.server.ip.demand.status.manage.global.error.response.ErrorResponse
import com.iplease.server.ip.demand.status.manage.global.error.type.ErrorType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class IpDemandAdminControllerAdvice {
    @ExceptionHandler(UnknownDemandException::class)
    fun handle(exception: UnknownDemandException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .badRequest()
            .body(ErrorResponse(ErrorType.UNKNOWN_DEMAND, "IP할당신청을 찾을 수 없습니다!", exception.message?:""))
    }
}