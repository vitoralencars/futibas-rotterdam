package components.stepper

import org.jetbrains.compose.resources.StringResource

data class Step(
    val titleResId: StringResource,
    val status: StepStatus,
)
