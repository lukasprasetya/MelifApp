package com.binar.melif.presentation.ui.movie

import androidx.core.view.isVisible
import com.binar.melif.R
import com.binar.melif.base.BaseViewModelFragment
import com.binar.melif.base.wrapper.Resource
import com.binar.melif.databinding.FragmentMovieBinding
import com.binar.melif.presentation.adapter.MovieAdapter
import com.binar.melif.presentation.adapter.MovieItem
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieFragment :
    BaseViewModelFragment<FragmentMovieBinding, MovieViewModel>
        (FragmentMovieBinding::inflate) {

    override val viewModel: MovieViewModel by viewModel()

    private val adapter: MovieAdapter by lazy {
        MovieAdapter()
    }

    override fun initView() {
        super.initView()
        activity?.title= getString(R.string.text_movie_list)
        binding.rvHome.adapter = adapter
        viewModel.getTvShow()
    }

    override fun observeData() {
        super.observeData()
        viewModel.movieResult.observe(this) {
            when (it) {
                is Resource.Empty -> {
                    showEmptyData()
                }

                is Resource.Error -> {
                    showError()
                    setErrorMessage(it.exception?.message.orEmpty())
                }
                is Resource.Loading -> showLoading()
                is Resource.Success -> showData(it.payload)
                }
            }
        }

    private fun showLoading() {
        binding.rvHome.isVisible = false
        binding.tvErrorHome.isVisible = false
        binding.pbHome.isVisible = true
    }

    private fun showError() {
        binding.rvHome.isVisible = false
        binding.pbHome.isVisible = false
        binding.tvErrorHome.isVisible = true
    }

    private fun showData(data: List<MovieItem>?) {
        binding.rvHome.isVisible = true
        binding.pbHome.isVisible = false
        binding.tvErrorHome.isVisible = false
        data?.let { adapter.setItems(it) }
    }

    private fun showEmptyData() {
        showError()
        setErrorMessage(getText(R.string.text_empty_data).toString())
    }

    private fun setErrorMessage(msg: String) {
        binding.tvErrorHome.text = msg
    }
}