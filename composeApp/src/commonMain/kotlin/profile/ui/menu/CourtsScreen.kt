package profile.ui.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import common.ui.theme.dimensions
import common.ui.theme.themeColors
import common.ui.theme.typographies
import components.toolbar.Toolbar
import components.toolbar.ToolbarViewState
import futibasrotterdam.composeapp.generated.resources.Res
import futibasrotterdam.composeapp.generated.resources.profile_menu_courts
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import platform.util.openUrl
import profile.domain.model.Court
import profile.presentation.menu.courts.CourtsSideEffect
import profile.presentation.menu.courts.CourtsViewModel

@OptIn(KoinExperimentalAPI::class)
@Composable
fun CourtsScreen(
    navController: NavController,
) {
    val viewModel = koinViewModel<CourtsViewModel>()

    LaunchedEffect(viewModel.sideEffect) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is CourtsSideEffect.OpenGoogleMaps -> {
                    openUrl(sideEffect.url)
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = themeColors.mainColor.copy(alpha = .2f))
    ) {
        Toolbar(
            viewState = ToolbarViewState(title = stringResource(Res.string.profile_menu_courts)),
            navController = navController,
            shouldNavigateBack = true,
        )

        CourtsList(
            courts = viewModel.state.value.courts,
            viewModel = viewModel,
        )
    }
}

@Composable
private fun CourtsList(
    courts: List<Court>,
    viewModel: CourtsViewModel,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(dimensions.gap4),
        contentPadding = PaddingValues(dimensions.gap4),
        modifier = Modifier.fillMaxSize(),
    ) {
        items(courts) { court ->
            CourtView(
                court = court,
                viewModel = viewModel,
            )
        }
    }
}

@Composable
private fun CourtView(
    court: Court,
    viewModel: CourtsViewModel,
) {
    val shape = RoundedCornerShape(dimensions.gap8)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .border(
                width = dimensions.borderThickness,
                color = themeColors.dividerColor,
                shape = RoundedCornerShape(dimensions.gap8)
            )
            .clickable { viewModel.handleCourtTapped(url = court.mapsUrl) }
    ) {
        Image(
            painter = painterResource(court.imageRes),
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = themeColors.secondaryColor)
                .padding(dimensions.gap4)
        ) {
            Text(
                text = court.name,
                style = typographies.h3
            )
            Text(
                text = court.address,
                style = typographies.body
            )
        }
    }
}
