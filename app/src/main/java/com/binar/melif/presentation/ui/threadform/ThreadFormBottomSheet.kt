package com.binar.melif.presentation.ui.threadform

import android.widget.Toast
import androidx.core.view.isVisible
import com.binar.melif.R
import com.binar.melif.base.BaseVMBottomSheetFragment
import com.binar.melif.base.wrapper.Resource
import com.binar.melif.databinding.FragmentThreadFormBottomSheetBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ThreadFormBottomSheet :
    BaseVMBottomSheetFragment<FragmentThreadFormBottomSheetBinding, ThreadFormViewModel>(
        FragmentThreadFormBottomSheetBinding::inflate
    ) {
    override val viewModel: ThreadFormViewModel by viewModel()

    override fun initView() {
        super.initView()
        binding.btnCreateThread.setOnClickListener {
            createThread()
        }
    }

    override fun observeData() {
        super.observeData()
        viewModel.createThreadResult.observe(this){
            when(it){
                is Resource.Empty -> {
                    //nothing
                }
                is Resource.Error -> {
                    binding.pbForm.isVisible = false
                    Toast.makeText(requireContext(), it.exception?.message.orEmpty(), Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    binding.pbForm.isVisible = true
                }
                is Resource.Success -> {
                    binding.pbForm.isVisible = false
                    dismiss()
                }
            }
        }
    }

    private fun createThread() {
        if (checkFormValidation()) {
            val title = binding.etThreadTitle.text.toString()
            val body = binding.etThreadBody.text.toString()
        viewModel.createThread(title, body)

        }
    }

    private fun checkFormValidation(): Boolean {
        val title = binding.etThreadTitle.text.toString()
        val body = binding.etThreadBody.text.toString()
        var isFormValid = true
        if (title.isEmpty()) {
            isFormValid = false
            binding.tilThreadTitle.isErrorEnabled = true
            binding.tilThreadTitle.error = getString(R.string.text_error_empty_title)
        } else {
            binding.tilThreadTitle.isErrorEnabled = false
        }
        if (body.isEmpty()) {
            isFormValid = false
            binding.tilThreadBody.isErrorEnabled = true
            binding.tilThreadBody.error = getString(R.string.text_error_empty_body)
        } else {
            binding.tilThreadBody.isErrorEnabled = false
        }

        return isFormValid
    }
}