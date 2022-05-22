package com.iplease.server.ip.demand.status.manage.service

interface IpDemandAdminService {
    fun accept(demandUuid: Long)
    fun reject(demandUuid: Long)

}
