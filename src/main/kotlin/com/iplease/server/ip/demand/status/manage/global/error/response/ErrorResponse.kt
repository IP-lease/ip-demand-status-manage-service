package com.iplease.server.ip.demand.status.manage.global.error.response

import com.iplease.server.ip.demand.status.manage.global.error.type.ErrorType

class ErrorResponse (
    val code: ErrorType,
    val message: String,
    val detail: String
)
