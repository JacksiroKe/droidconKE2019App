package com.android254.droidconke19.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android254.droidconke19.datastates.Result
import com.android254.droidconke19.models.AboutDetailsModel
import com.android254.droidconke19.repository.AboutDetailsRepo
import com.android254.droidconke19.utils.NonNullMediatorLiveData
import kotlinx.coroutines.launch

class AboutViewModel(private val aboutDetailsRepo: AboutDetailsRepo) : ViewModel() {
    private val detailsStateMediatorLiveData = NonNullMediatorLiveData<List<AboutDetailsModel>>()
    private val detailsError = NonNullMediatorLiveData<String>()
    private val organizersMediatorLiveData = NonNullMediatorLiveData<List<AboutDetailsModel>>()
    private val organizersError = NonNullMediatorLiveData<String>()
    private val sponsorsMediatorLiveData = NonNullMediatorLiveData<List<AboutDetailsModel>>()
    private val sponsorsError = NonNullMediatorLiveData<String>()


    fun getAboutDetailsResponse(): LiveData<List<AboutDetailsModel>> = detailsStateMediatorLiveData

    fun getAboutDetailsError(): LiveData<String> = detailsError

    fun getOrganizersResponse(): LiveData<List<AboutDetailsModel>> = organizersMediatorLiveData

    fun getOrganizerError(): LiveData<String> = organizersError

    fun getSponsorsResponse(): LiveData<List<AboutDetailsModel>> = sponsorsMediatorLiveData

    fun getSponsorsError(): LiveData<String> = sponsorsError

    fun fetchAboutDetails(aboutType: String) {
        viewModelScope.launch {
            when (val value = aboutDetailsRepo.getAboutDetails(aboutType)) {
                is Result.Success -> detailsStateMediatorLiveData.postValue(value.data)
                is Result.Error -> detailsError.postValue(value.exception)
            }
        }
    }

    fun getOrganizers(aboutType: String) {
        viewModelScope.launch {
            when (val value = aboutDetailsRepo.getAboutDetails(aboutType)) {
                is Result.Success -> organizersMediatorLiveData.postValue(value.data)
                is Result.Error -> organizersError.postValue(value.exception)
            }
        }

    }

    fun getSponsors(aboutType: String) {
        viewModelScope.launch {
            when (val value = aboutDetailsRepo.getAboutDetails(aboutType)) {
                is Result.Success -> sponsorsMediatorLiveData.postValue(value.data)
                is Result.Error -> sponsorsError.postValue(value.exception)
            }
        }
    }

}
