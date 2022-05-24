package com.iplease.server.ip.demand.status.manage.global.status.data.type

import com.iplease.server.ip.demand.status.manage.global.status.data.type.DemandStatusType.CREATE
import com.iplease.server.ip.demand.status.manage.global.status.data.type.DemandStatusType.IN_PROGRESS
import com.iplease.server.ip.demand.status.manage.global.status.data.type.DemandStatusType.ACCEPT
import com.iplease.server.ip.demand.status.manage.global.status.data.type.DemandStatusType.REJECT
import com.iplease.server.ip.demand.status.manage.global.status.data.type.DemandStatusType.SUCCESS
import com.iplease.server.ip.demand.status.manage.global.status.data.type.DemandStatusType.FAIL
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DemandStatusTypeTest {
    private val canChangeFrom: MutableMap<DemandStatusType, MutableList<DemandStatusType>> = mutableMapOf()
    @BeforeEach
    fun setUp() {
        canChangeFrom[CREATE] = mutableListOf(IN_PROGRESS)
        canChangeFrom[IN_PROGRESS] = mutableListOf(ACCEPT, REJECT)
        canChangeFrom[ACCEPT] = mutableListOf(SUCCESS, FAIL)
        canChangeFrom[REJECT] = mutableListOf(FAIL)
        canChangeFrom[FAIL] = mutableListOf()
        canChangeFrom[SUCCESS] = mutableListOf()
    }

    @Test
    fun testCanChangeFrom() {
        for (from in DemandStatusType.values()) {
            for (to in DemandStatusType.values()) {
                if (from == to) continue
                val expected = canChangeFrom[from]?.contains(to)?:false
                val result = from.canChangeTo(to).map { true }.onErrorReturn(false).block()
                assert(expected == result) { "$from to $to Expected $expected but was $result" }
            }
        }
    }
}