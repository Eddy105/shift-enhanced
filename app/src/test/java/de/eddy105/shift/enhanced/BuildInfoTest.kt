package de.eddy105.shift.enhanced

import org.junit.Assert.assertEquals
import org.junit.Test

class BuildInfoTest {
    @Test
    fun versionMatchesFirstMilestone() {
        assertEquals("0.1.0", BuildInfo.version)
    }
}
