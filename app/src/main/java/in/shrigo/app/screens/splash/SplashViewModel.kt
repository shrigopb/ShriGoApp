package `in`.shrigo.app.screens.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import `in`.shrigo.app.api.RetrofitClient
import `in`.shrigo.app.models.VersionResponse
import `in`.shrigo.app.repository.VersionRepository
import `in`.shrigo.app.utils.VersionUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {

    private val repository =
        VersionRepository(RetrofitClient.api)

    private val _versionResponse =
        MutableStateFlow<VersionResponse?>(null)

    val versionResponse: StateFlow<VersionResponse?> =
        _versionResponse

    private val _showUpdateDialog =
        MutableStateFlow(false)

    val showUpdateDialog: StateFlow<Boolean> =
        _showUpdateDialog

    private val _isLoading =
        MutableStateFlow(false)

    val isLoading: StateFlow<Boolean> =
        _isLoading
    private val _startupCompleted =
        MutableStateFlow(false)

    val startupCompleted: StateFlow<Boolean> =
        _startupCompleted

    fun checkLatestVersion(currentVersion: String) {
        _isLoading.value = true
        _startupCompleted.value = false
        viewModelScope.launch {

            try {

                val response =
                    repository.getLatestVersion()
                Log.d(
                    "VERSION_CHECK",
                    "Current: $currentVersion"
                )

               Log.d(
                    "VERSION_CHECK",
                    "Latest : ${response.latestVersion}"
                )

                Log.d(
                    "VERSION_CHECK",
                    "Update : ${
                        VersionUtils.isUpdateAvailable(
                            currentVersion,
                            response.latestVersion
                        )
                    }"
                )
                _versionResponse.value =
                    response

                _showUpdateDialog.value =
                    VersionUtils.isUpdateAvailable(
                        currentVersion,
                        response.latestVersion
                    )
                android.util.Log.d(
                    "VERSION_CHECK",
                    "Show Dialog = ${_showUpdateDialog.value}"
                )
            } catch (e: Exception) {

             Log.e(
                    "SplashViewModel",
                    "Version check failed",
                    e
                )

                _showUpdateDialog.value = false
            }

            finally {

                _isLoading.value = false
                _startupCompleted.value = true
            }
        }
    }
}