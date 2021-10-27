package com.roche.ssg.utils

import io.mockk.MockKAnnotations
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Before

open class BaseMockkTest {
    @Before
    open fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @After
    open fun finish() {
        unmockkAll()
    }
}