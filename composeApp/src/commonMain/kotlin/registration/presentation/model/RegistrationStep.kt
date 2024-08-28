package registration.presentation.model

sealed interface RegistrationStep {
    val stepIndex: Int

    data class Identification(
        override val stepIndex: Int = 0
    ): RegistrationStep
    data class Location(
        override val stepIndex: Int = 1
    ): RegistrationStep
    data class Photo(
        override val stepIndex: Int = 2
    ): RegistrationStep
}
