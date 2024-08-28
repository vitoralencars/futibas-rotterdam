package profile.domain.usecase

import network.ServiceResult
import profile.domain.model.playerdata.UploadPlayerPhotoRequest
import profile.domain.repository.ProfileRepository

class UploadPlayerPhotoUseCase(
    private val repository: ProfileRepository
) {

    suspend operator fun invoke(
        uploadPlayerPhotoRequest: UploadPlayerPhotoRequest,
    ): ServiceResult<String> {
        return repository.uploadPlayerPhoto(uploadPlayerPhotoRequest)
    }
}
