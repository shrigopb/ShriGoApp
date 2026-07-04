package `in`.shrigo.app.utils


/**
 * Returns true if the Play Store version is newer than the installed version.
 *
 * Examples:
 * 20.2.9 -> 20.3.0 = true
 * 20.10.0 -> 20.9.9 = false
 */
object VersionUtils {

    fun isUpdateAvailable(
        currentVersion: String,
        latestVersion: String
    ): Boolean {

        val current = currentVersion.split(".").map { it.toIntOrNull() ?: 0 }
        val latest = latestVersion.split(".").map { it.toIntOrNull() ?: 0 }

        val maxLength = maxOf(current.size, latest.size)

        for (i in 0 until maxLength) {

            val currentPart = current.getOrElse(i) { 0 }
            val latestPart = latest.getOrElse(i) { 0 }

            when {
                latestPart > currentPart -> return true
                latestPart < currentPart -> return false
            }
        }

        return false
    }
}