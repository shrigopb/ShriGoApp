package `in`.shrigo.app.screens.splash

import `in`.shrigo.app.utils.VersionUtils
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class VersionUtilsTest {

    @Test
    fun update_Available() {

        assertTrue(
            VersionUtils.isUpdateAvailable("20.2.9", "20.3.0")
        )

        assertFalse(
            VersionUtils.isUpdateAvailable("20.3.0", "20.2.9")
        )

        assertFalse(
            VersionUtils.isUpdateAvailable("20.2.9", "20.2.9")
        )

        assertTrue(
            VersionUtils.isUpdateAvailable("20.9.9", "20.10.0")
        )

        assertFalse(
            VersionUtils.isUpdateAvailable("20.10.0", "20.9.9")
        )

        assertFalse(
            VersionUtils.isUpdateAvailable("21", "21.0")
        )

        assertFalse(
            VersionUtils.isUpdateAvailable("21.0", "21")
        )

        assertTrue(
            VersionUtils.isUpdateAvailable("21.0", "21.0.1")
        )

        assertFalse(
            VersionUtils.isUpdateAvailable("21.0.1", "21.0")
        )
    }
}