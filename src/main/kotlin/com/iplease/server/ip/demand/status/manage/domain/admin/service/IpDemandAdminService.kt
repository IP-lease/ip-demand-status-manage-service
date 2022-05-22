package com.iplease.server.ip.demand.status.manage.domain.admin.service

interface IpDemandAdminService {
    fun accept(demandUuid: Long)
    fun reject(demandUuid: Long)

}
