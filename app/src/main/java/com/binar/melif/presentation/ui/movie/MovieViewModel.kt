package com.binar.melif.presentation.ui.movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.melif.R
import com.binar.melif.base.wrapper.Resource
import com.binar.melif.data.repository.MovieRepository
import com.binar.melif.presentation.adapter.MovieItem
import kotlinx.coroutines.launch
import java.util.logging.Handler

class MovieViewModel(private val MovieRepository: MovieRepository) : ViewModel() {

    val movieResult = MutableLiveData<Resource<List<MovieItem>>>()


    fun getTvShow() {
        viewModelScope.launch {
            movieResult.postValue(Resource.Loading())
            val nowPlaying = MovieRepository.getNowPlayingMovie()
            val sectionPopularList = MovieRepository.getPopularMovie()
            val sectionTopRatedList = MovieRepository.getTopRatedMovie()

            val movieItems = mutableListOf<MovieItem>()
            movieItems.apply {
                nowPlaying.payload?.results.let {
                            add(MovieItem.MovieHeaderItem(it?.random()))

                }
                sectionPopularList.payload?.results.let {
                    add(MovieItem.MovieSectionItem(R.string.popular_movie_section, it))
                }
                nowPlaying.payload?.results.let {
                    add(MovieItem.MovieSectionItem(R.string.text_new_playing, it))
                }
                sectionTopRatedList.payload?.results.let {
                    add(MovieItem.MovieSectionItem(R.string.top_rated_movie_section, it))
                }
            }

            if (movieItems.isNotEmpty()) {
                movieResult.postValue(Resource.Success(movieItems))
            } else {
                movieResult.postValue(Resource.Empty())
            }
        }
    }
}