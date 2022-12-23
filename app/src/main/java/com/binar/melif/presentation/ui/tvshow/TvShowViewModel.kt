package com.binar.melif.presentation.ui.tvshow

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.melif.R
import com.binar.melif.base.wrapper.Resource
import com.binar.melif.data.repository.TvShowRepository
import com.binar.melif.presentation.adapter.TvShowItem
import kotlinx.coroutines.launch

class TvShowViewModel(private val tvShowRepository: TvShowRepository) : ViewModel() {

    val tvShowResult = MutableLiveData<Resource<List<TvShowItem>>>()

    fun getTvShow() {
        viewModelScope.launch {
            tvShowResult.postValue(Resource.Loading())
            val newRelease = tvShowRepository.getAiringTvList()
            val sectionPopularList = tvShowRepository.getPopularTvList()
            val sectionTopRatedList = tvShowRepository.getTopRatedTvList()

            val tvShowItems = mutableListOf<TvShowItem>()
            tvShowItems.apply {
                sectionPopularList.payload?.results.let {
                    add(TvShowItem.TvShowHeaderItem(it?.random()))
                }
                sectionPopularList.payload?.results.let {
                    add(TvShowItem.TvShowSectionItem(R.string.popular_tv_section, it))
                }
                newRelease.payload?.results.let {
                    add(TvShowItem.TvShowSectionItem(R.string.text_new_release, it))
                }
                sectionTopRatedList.payload?.results.let {
                    add(TvShowItem.TvShowSectionItem(R.string.top_rated_tv_section, it))
                }
            }

            if (tvShowItems.isNotEmpty()) {
                tvShowResult.postValue(Resource.Success(tvShowItems))
            } else {
                tvShowResult.postValue(Resource.Empty())
            }
        }
    }
}