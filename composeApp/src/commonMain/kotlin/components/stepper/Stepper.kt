package components.stepper

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import common.ui.theme.dimensions
import common.ui.theme.themeColors
import common.ui.theme.typographies
import org.jetbrains.compose.resources.stringResource

@Composable
fun Stepper(
    viewState: StepperViewState,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        viewState.steps.forEachIndexed { index, step ->
            StepView(step = step)
            if (index < viewState.steps.lastIndex) {
                StepDivider(
                    stepStatus = step.status,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun StepView(
    step: Step,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.defaultMinSize(minWidth = dimensions.minStepViewWidth)
    ) {
        StepIndicator(stepStatus = step.status)
        Spacer(modifier = Modifier.height(dimensions.gap1))
        StepTitle(step = step)
    }
}

@Composable
private fun StepTitle(step: Step) {
    val typography = when (step.status) {
        is StepStatus.Done,
            StepStatus.Current -> typographies.stepperViewed
        is StepStatus.Future -> typographies.stepperFuture
    }

    Text(
        text = stringResource(step.titleResId),
        style = typography,
    )
}

@Composable
private fun StepDivider(
    stepStatus: StepStatus,
    modifier: Modifier,
) {
    val color = when (stepStatus) {
        is StepStatus.Done -> themeColors.mainColor
        else -> themeColors.typographyFutureStepperColor
    }

    Divider(
        thickness = dimensions.dividerThickness,
        color = color,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = dimensions.stepViewSize / 2)
    )
}

@Composable
private fun StepIndicator(stepStatus: StepStatus) {
    when (stepStatus) {
        is StepStatus.Done -> DoneStepIndicator()
        is StepStatus.Current -> CurrentStepIndicator()
        is StepStatus.Future -> FutureStepIndicator()
    }
}

@Composable
private fun DoneStepIndicator() {
    Icon(
        imageVector = Icons.Filled.Done,
        contentDescription = null,
        tint = themeColors.secondaryColor,
        modifier = Modifier
            .clip(shape = CircleShape)
            .background(color = themeColors.mainColor)
            .size(dimensions.stepViewSize)
            .padding(dimensions.gap1)
    )
}

@Composable
private fun CurrentStepIndicator() {
    Box(
        modifier = Modifier
            .border(
                shape = CircleShape,
                width = dimensions.borderThickness,
                color = themeColors.mainColor,
            )
            .background(themeColors.secondaryColor)
            .size(dimensions.stepViewSize)
    )
}

@Composable
private fun FutureStepIndicator() {
    Box(
        modifier = Modifier
            .border(
                shape = CircleShape,
                width = dimensions.borderThickness,
                color = themeColors.typographyFutureStepperColor,
            )
            .background(themeColors.secondaryColor)
            .size(dimensions.stepViewSize)
    )
}
