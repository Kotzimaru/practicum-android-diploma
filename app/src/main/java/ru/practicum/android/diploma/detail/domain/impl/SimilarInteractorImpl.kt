package ru.practicum.android.diploma.detail.domain.impl

import android.util.Log
import ru.practicum.android.diploma.detail.domain.SimilarInteractor
import ru.practicum.android.diploma.detail.domain.api.SimilarRepository
import ru.practicum.android.diploma.detail.domain.models.SimilarVacanciesInfo
import ru.practicum.android.diploma.search.domain.api.DtoConsumer
import ru.practicum.android.diploma.search.domain.models.ResponseCodes.ERROR
import ru.practicum.android.diploma.search.domain.models.ResponseCodes.NO_NET_CONNECTION
import ru.practicum.android.diploma.search.domain.models.ResponseCodes.SUCCESS

class SimilarInteractorImpl(private val repository: SimilarRepository) : SimilarInteractor {
    override suspend fun execute(vacancy: String): SimilarVacanciesInfo {
        return when (val result = repository.doRequest(vacancy)) {
            is DtoConsumer.Success -> {
                Log.d("TAG result", "result - ${result.data}")
                SimilarVacanciesInfo(
                    responseCodes = SUCCESS,
                    vacancies = result.data
                )
            }

            is DtoConsumer.Error -> {
                Log.d("TAG result", "result - ${result.errorCode}")
                SimilarVacanciesInfo(
                    responseCodes = ERROR,
                    vacancies = null
                )
            }

            is DtoConsumer.NoInternet -> {
                Log.d("TAG result", "result - ${result.errorCode}")
                SimilarVacanciesInfo(
                    responseCodes = NO_NET_CONNECTION,
                    vacancies = null
                )
            }
        }
    }
}
