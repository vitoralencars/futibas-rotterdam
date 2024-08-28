package profile.presentation.general

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material.icons.outlined.PhotoLibrary
import androidx.lifecycle.viewModelScope
import common.base.BaseViewModel
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import futibasrotterdam.composeapp.generated.resources.Res
import futibasrotterdam.composeapp.generated.resources.image_source_request_camera
import futibasrotterdam.composeapp.generated.resources.image_source_request_gallery
import kotlinx.coroutines.launch
import network.ServiceResult
import platform.image.Image
import platform.permission.PermissionType
import player.domain.usecase.RetrieveLoggedInPlayerUseCase
import player.domain.usecase.StoreLoggedInPlayerUseCase
import profile.domain.model.playerdata.UploadPlayerPhotoRequest
import profile.domain.usecase.DeletePlayerAccountUseCase
import profile.domain.usecase.RemoveLoggedInPlayerUseCase
import profile.domain.usecase.UploadPlayerPhotoUseCase
import registration.presentation.model.photo.ImageSourceItem

class ProfileGeneralViewModel(
    private val retrieveLoggedInPlayerUseCase: RetrieveLoggedInPlayerUseCase,
    private val storeLoggedInPlayerUseCase: StoreLoggedInPlayerUseCase,
    private val uploadPlayerPhoto: UploadPlayerPhotoUseCase,
    private val removeLoggedInPlayerUseCase: RemoveLoggedInPlayerUseCase,
    private val deletePlayerAccountUseCase: DeletePlayerAccountUseCase,
) : BaseViewModel<ProfileGeneralScreenState, ProfileGeneralSideEffect>(
    initialState = ProfileGeneralScreenState.Initial
) {
    init {
        viewModelScope.launch {
            retrieveLoggedInPlayer()
        }
    }

    private suspend fun retrieveLoggedInPlayer() {
        val player = retrieveLoggedInPlayerUseCase()
        player?.let { loggedInPlayer ->
            updateState {
                ProfileGeneralScreenState.Content(
                    loggedInPlayer = loggedInPlayer,
                    imageSourceItems = listOf(
                        ImageSourceItem(
                            icon = Icons.Outlined.PhotoCamera,
                            titleResId = Res.string.image_source_request_camera,
                            permissionType = PermissionType.CAMERA,
                        ),
                        ImageSourceItem(
                            icon = Icons.Outlined.PhotoLibrary,
                            titleResId = Res.string.image_source_request_gallery,
                            permissionType = PermissionType.GALLERY,
                        ),
                    ),
                )
            }
        }
    }

    fun onPhotoTapped() {
        state.value.asContent()?.let { content ->
            content.loggedInPlayer.photoUrl?.let {
                updateState {
                    content.copy(
                        isPhotoExpanded = true,
                    )
                }
            } ?: run {

            }
        }
    }

    fun onPhotoDialogDismissed() {
        state.value.asContent()?.let { content ->
            updateState {
                content.copy(
                    isPhotoExpanded = false,
                )
            }
        }
    }

    fun onPhotoSet(photo: Image?) {
        state.value.asContent()?.let { content ->
            val base64 = photo?.toBase64()
            base64?.let { imageData ->
                viewModelScope.launch {
                    updateState {
                        content.copy(isNewPhotoLoading = true)
                    }
                    val response = uploadPlayerPhoto(
                        UploadPlayerPhotoRequest(
                        playerId = content.loggedInPlayer.playerId,
                        imageData = imageData,
                    )
                    )

                    if (response is ServiceResult.Success) {
                        val loggedInPlayer = content.loggedInPlayer.copy(
                            photoUrl = response.response
                        )
                        updateState {
                            content.copy(
                                loggedInPlayer = loggedInPlayer,
                            )
                        }
                        storeLoggedInPlayerUseCase(loggedInPlayer = loggedInPlayer)
                    }
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            Firebase.auth.signOut()
            removeLoggedInPlayerUseCase()
            emitSideEffect(ProfileGeneralSideEffect.NavigateToLogin)
        }
    }

    fun onDeleteAccountTapped() {
        state.value.asContent()?.let { content ->
            updateState {
                content.copy(
                    shouldShowDeleteAccountDialog = true,
                )
            }
        }
    }

    fun onDeleteAccountDismissed() {
        state.value.asContent()?.let { content ->
            updateState {
                content.copy(
                    shouldShowDeleteAccountDialog = false,
                )
            }
        }
    }

    fun deleteAccount() {
        state.value.asContent()?.let { content ->
            updateState {
                content.copy(
                    shouldShowDeleteAccountDialog = false,
                    isDeleteAccountLoading = true,
                )
            }
            viewModelScope.launch {
                deletePlayerAccountUseCase(playerId = content.loggedInPlayer.playerId)
                logout()
            }
        }
    }
}
