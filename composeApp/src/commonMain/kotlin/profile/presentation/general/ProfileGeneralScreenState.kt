package profile.presentation.general

import player.domain.model.LoggedInPlayer
import registration.presentation.model.photo.ImageSourceItem

sealed interface ProfileGeneralScreenState {

    data object Initial : ProfileGeneralScreenState

    data class Content(
        val loggedInPlayer: LoggedInPlayer,
        val imageSourceItems: List<ImageSourceItem>,
        val isPhotoExpanded: Boolean = false,
        val isNewPhotoLoading: Boolean = false,
        val isEditPhotoRequested: Boolean = false,
        val shouldShowDeleteAccountDialog: Boolean = false,
        val isDeleteAccountLoading: Boolean = false,
    ) : ProfileGeneralScreenState

    fun asContent(): Content? = this as? Content
}
