package profile.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import common.navigation.GlobalNavController
import common.navigation.ScreenNavigation
import common.ui.theme.dimensions
import common.ui.theme.themeColors
import common.ui.theme.typographies
import common.util.image.ImageSource
import common.util.image.PickImage
import components.bottomsheet.BottomSheetManager
import components.button.ButtonViewState
import components.button.NegativeButton
import components.image.ImageSourceOptions
import futibasrotterdam.composeapp.generated.resources.Res
import futibasrotterdam.composeapp.generated.resources.add_photo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import platform.image.ImageSourceManager
import platform.image.rememberCameraManager
import platform.image.rememberGalleryManager
import player.domain.mapper.toPLayerLevel
import player.domain.model.PlayerPermissions
import profile.presentation.menu.MenuProfileItemViewState
import profile.presentation.general.ProfileGeneralScreenState
import profile.presentation.general.ProfileGeneralSideEffect
import profile.presentation.general.ProfileGeneralViewModel
import profile.ui.menu.MenuProfileItem
import profile.ui.navigation.ProfileScreenNavigation
import registration.presentation.model.photo.ImageSourceItem

@OptIn(KoinExperimentalAPI::class)
@Composable
fun ProfileGeneralScreen(
    navController: NavController,
    bottomSheetManager: BottomSheetManager,
) {
    val viewModel: ProfileGeneralViewModel = koinViewModel<ProfileGeneralViewModel>()

    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    var selectedImageSource by remember { mutableStateOf<ImageSource?>(null) }

    LaunchedEffect(viewModel.sideEffect) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is ProfileGeneralSideEffect.NavigateToLogin -> {
                    GlobalNavController.navController.popBackStack(
                        route = ScreenNavigation.Login.route,
                        inclusive = false,
                    )
                }
            }
        }
    }

    val cameraManager = rememberCameraManager { photo ->
        coroutineScope.launch {
            bottomSheetManager.hide()
            viewModel.onPhotoSet(photo)
        }
    }
    val galleryManager = rememberGalleryManager { photo ->
        coroutineScope.launch {
            withContext(Dispatchers.Default) {
                bottomSheetManager.hide()
                viewModel.onPhotoSet(photo)
            }
        }
    }

    when (val state = viewModel.state.collectAsState().value) {
        is ProfileGeneralScreenState.Content -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = themeColors.mainColor)
            ) {
                ProfileInfo(
                    content = state,
                    viewModel = viewModel,
                    bottomSheetManager = bottomSheetManager,
                    cameraManager = cameraManager,
                    galleryManager = galleryManager,
                    onImageSourceSelected = {
                        selectedImageSource = it
                    }
                )
                Menu(
                    navController = navController,
                    playerPermissions = state.loggedInPlayer.permissions,
                    modifier = Modifier.weight(1f),
                )
                LogoutButton(
                    onClick = { viewModel.logout() }
                )
            }
        }
        else -> {}
    }

    selectedImageSource?.let {
        PickImage(
            permissionType = it.permissionType,
            imageSourceManager = it.imageSourceManager,
            onPermissionResult = {
                selectedImageSource = null
            },
        )
    }
}

@Composable
private fun ProfileInfo(
    content: ProfileGeneralScreenState.Content,
    viewModel: ProfileGeneralViewModel,
    bottomSheetManager: BottomSheetManager,
    cameraManager: ImageSourceManager,
    galleryManager: ImageSourceManager,
    onImageSourceSelected: (ImageSource) -> Unit,
) {
    with (content.loggedInPlayer) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = dimensions.gap8,
                    bottom = dimensions.gap8,
                    start = dimensions.gap4,
                    end = dimensions.gap4,
                )
        ) {
            if (!content.isNewPhotoLoading) {
                PhotoImage(
                    model = photoUrl,
                    onPhotoTapped = {
                        photoUrl?.let {
                            viewModel.onPhotoTapped()
                        } ?: requestEditPhoto(
                            bottomSheetManager = bottomSheetManager,
                            imageSourceItems = content.imageSourceItems,
                            cameraManager = cameraManager,
                            galleryManager = galleryManager,
                            onImageSourceSelected = onImageSourceSelected,
                        )
                    },
                )
            } else {
                Box(modifier = Modifier
                    .size(dimensions.playerPhotoProfile)
                    .clip(CircleShape)
                    .background(themeColors.secondaryColor)
                    .padding(dimensions.gap4)
                ) {
                    CircularProgressIndicator(
                        color = themeColors.mainColor,
                        strokeWidth = dimensions.gap1,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
            Spacer(Modifier.height(dimensions.gap3))
            Text(
                text = nickname ?: name,
                style = typographies.h1Light,
            )
            Text(
                text = level.toPLayerLevel().description,
                style = typographies.bodySecondary,
            )
        }

        if (content.isPhotoExpanded) {
            ExpandedPhotoDialog(
                photoUrl = photoUrl,
                onEditPhotoTapped = {
                    viewModel.onPhotoDialogDismissed()
                    requestEditPhoto(
                        bottomSheetManager = bottomSheetManager,
                        imageSourceItems = content.imageSourceItems,
                        cameraManager = cameraManager,
                        galleryManager = galleryManager,
                        onImageSourceSelected = onImageSourceSelected,
                    )
                },
                onDialogDismissed = {
                    viewModel.onPhotoDialogDismissed()
                }
            )
        }
    }
}

@Composable
private fun Menu(
    navController: NavController,
    playerPermissions: PlayerPermissions,
    modifier: Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(
                color = themeColors.secondaryColor,
                shape = RoundedCornerShape(
                    topStart = dimensions.profileMenuRoundedCorner,
                    topEnd = dimensions.profileMenuRoundedCorner,
                )
            )
            .padding(
                vertical = dimensions.gap4,
                horizontal = dimensions.gap6
            )
    ) {
        MenuProfileItem(
            viewState = MenuProfileItemViewState(
                title = "Informações pessoais"
            ),
            onItemClicked = {
                navController.navigate(ProfileScreenNavigation.PersonalData.route)
            },
        )
        MenuProfileItem(
            viewState = MenuProfileItemViewState(
                title = "Quadras"
            ),
            onItemClicked = {
                navController.navigate(ProfileScreenNavigation.Courts.route)
            },
        )
        if (playerPermissions.configureList) {
            MenuProfileItem(
                viewState = MenuProfileItemViewState(
                    title = "Configurar lista"
                ),
                onItemClicked = {
                    navController.navigate(ProfileScreenNavigation.NewGameList.route)
                },
            )
        }
        if (playerPermissions.managePendingPlayers) {
            MenuProfileItem(
                viewState = MenuProfileItemViewState(
                    title = "Jogadores pendentes"
                ),
                onItemClicked = {
                    navController.navigate(ProfileScreenNavigation.PendingPlayers.route)
                },
            )
        }
    }
}

@Composable
private fun LogoutButton(
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(themeColors.secondaryColor)
            .padding(dimensions.gap4)
    ) {
        NegativeButton(
            viewState = ButtonViewState.Content(
                text = "Logout"
            ),
            onClick = onClick,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun PhotoImage(
    model: Any?,
    onPhotoTapped: () -> Unit,
) {
    AsyncImage(
        model = model,
        contentDescription = null,
        error = painterResource(Res.drawable.add_photo),
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(dimensions.playerPhotoProfile)
            .clip(CircleShape)
            .clickable {
                onPhotoTapped()
            }
    )
}

@Composable
private fun ExpandedPhotoDialog(
    photoUrl: String?,
    onEditPhotoTapped:() -> Unit,
    onDialogDismissed:() -> Unit,
) {
    Dialog(onDismissRequest = {
        onDialogDismissed()
    }) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(.8f)
        ) {
            AsyncImage(
                model = photoUrl,
                contentDescription = null,
            )
            IconButton(
                onClick = {
                    onEditPhotoTapped()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(themeColors.mainColor)
            ) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    tint = themeColors.secondaryColor,
                    contentDescription = null,
                )
            }
        }
    }
}

private fun requestEditPhoto(
    bottomSheetManager: BottomSheetManager,
    imageSourceItems: List<ImageSourceItem>,
    cameraManager: ImageSourceManager,
    galleryManager: ImageSourceManager,
    onImageSourceSelected: (ImageSource) -> Unit,
) {
    bottomSheetManager.show {
        ImageSourceOptions(
            imageSourceItems = imageSourceItems,
            imageSources = listOf(
                ImageSource.Camera(imageSourceManager = cameraManager),
                ImageSource.Gallery(imageSourceManager = galleryManager),
            ),
            onImageSourceSelected = { imageSource ->
                onImageSourceSelected(imageSource)
            },
        )
    }
}
