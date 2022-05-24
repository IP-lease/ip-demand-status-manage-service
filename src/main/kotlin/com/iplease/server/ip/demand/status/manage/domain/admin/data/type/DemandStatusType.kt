package com.iplease.server.ip.demand.status.manage.domain.admin.data.type

enum class DemandStatusType {
    //상태가 처음 등록되었을 경우 CREATE
    //관리자가 확인하였을 경우 IN_PROGRESS
    //관리자가 거절하였을 경우 REJECT
    //관리자가 수락하였을 경우 ACCEPT
    //IP할당이 완료되었을 경우 SUCCESS
    CREATE, IN_PROGRESS, REJECT, ACCEPT, SUCCESS
}
