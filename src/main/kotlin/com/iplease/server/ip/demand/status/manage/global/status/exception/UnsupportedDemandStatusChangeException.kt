package com.iplease.server.ip.demand.status.manage.global.status.exception

import com.iplease.server.ip.demand.status.manage.global.status.data.type.DemandStatusType

class UnsupportedDemandStatusChangeException(from: DemandStatusType, tobe: DemandStatusType): RuntimeException("$from can't change to $tobe") {

}
