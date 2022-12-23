package com.binar.melif.presentation.ui.tvshow

import android.content.ContentValues.TAG
import android.util.Log
import androidx.core.view.isVisible
import com.binar.melif.R
import com.binar.melif.base.BaseViewModelFragment
import com.binar.melif.base.wrapper.Resource
import com.binar.melif.databinding.FragmentTvShowBinding
import com.binar.melif.presentation.adapter.TvShowAdapter
import com.binar.melif.presentation.adapter.TvShowItem

import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class TvShowFragment :
    BaseViewModelFragment<FragmentTvShowBinding, TvShowViewModel>
        (FragmentTvShowBinding::inflate) {

    override val viewModel: TvShowViewModel by viewModel()

    private val adapter: TvShowAdapter by lazy {
       TvShowAdapter()
    }

    override fun initView() {
        super.initView()
        activity?.title= getString(R.string.text_tvshow_list)
        binding.rvHome.adapter = adapter
        viewModel.getTvShow()
    }

    override fun observeData() {
        super.observeData()
        viewModel.tvShowResult.observe(this) {
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

    private fun showData(data: List<TvShowItem>?) {
        binding.rvHome.isVisible = true
        binding.pbHome.isVisible = false
        binding.tvErrorHome.isVisible = false
        data?.let { adapter.setItemsTv(it) }
    }

    private fun showEmptyData() {
        showError()
        setErrorMessage(getText(R.string.text_empty_data).toString())
    }

    private fun setErrorMessage(msg: String) {
        binding.tvErrorHome.text = msg
    }
}