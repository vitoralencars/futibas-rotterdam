package components.stepper

sealed interface StepStatus {

    data object Done : StepStatus

    data object Current : StepStatus

    data object Future : StepStatus

}
