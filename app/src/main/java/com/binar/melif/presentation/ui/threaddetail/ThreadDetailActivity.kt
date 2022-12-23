package com.binar.melif.presentation.ui.threaddetail

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.transform.CircleCropTransformation
import com.binar.melif.R
import com.binar.melif.base.BaseViewModelActivity
import com.binar.melif.base.wrapper.Resource
import com.binar.melif.data.firebase.model.ThreadItem
import com.binar.melif.databinding.ActivityThreadDetailBinding
import com.binar.melif.presentation.adapter.SubThreadListAdapter
import com.binar.melif.utils.ButtonEnablerTextWatcher
import com.binar.melif.utils.OnReplyScrollObserver

import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ThreadDetailActivity :
    BaseViewModelActivity<ActivityThreadDetailBinding, ThreadDetailViewModel>(
        ActivityThreadDetailBinding::inflate
    ) {

    override val viewModel: ThreadDetailViewModel by viewModel {
        parametersOf(intent.extras ?: bundleOf())
    }

    private val adapter: SubThreadListAdapter by lazy {
        SubThreadListAdapter(
            viewModel.getSubThread(),
            viewModel.parentThread,
            viewModel.getCurrentUser(),
            onDataExist = {
                showData()
            },
            onLoading = {
                showLoading(it)
            },
            onDataError = {
                showError()
                setErrorMessage(it.message.orEmpty())
            },
            onDataEmpty = {
                showEmptyData()
            }
        )
    }

    override fun onPause() {
        super.onPause()
        adapter.stopListening()
    }

    override fun onResume() {
        super.onResume()
        adapter.startListening()
    }

    override fun initView() {
        super.initView()
        setupToolbar()
        setParentThreadData(item = viewModel.parentThread)
        setupReply()
        setupRecyclerView()
    }

    private fun setupReply() {
        binding.etSubThread.apply {
            addTextChangedListener(
                ButtonEnablerTextWatcher(
                    this@ThreadDetailActivity,
                    binding.btnSendSubThread
                )
            )

        }
        binding.btnSendSubThread.setOnClickListener { sendSubThread() }
    }

    private fun setupRecyclerView() {
        val manager = LinearLayoutManager(this)
        manager.stackFromEnd = true
        binding.rvDetailThread.layoutManager = manager
        adapter.registerAdapterDataObserver(
            OnReplyScrollObserver(
                binding.rvDetailThread,
                adapter,
                manager,
            )
        )
        binding.rvDetailThread.adapter = adapter
    }

    override fun observeData() {
        super.observeData()
        viewModel.replyThreadResult.observe(this) {
            when (it) {
                is Resource.Empty -> {
                    //nothing
                }
                is Resource.Error -> {
                    binding.pbDetail.isVisible = false
                    binding.etSubThread.isEnabled = true
                    Toast.makeText(this, it.exception?.message.orEmpty(), Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    binding.etSubThread.isEnabled = false
                    binding.pbDetail.isVisible = true
                }
                is Resource.Success -> {
                    binding.pbDetail.isVisible = false
                    binding.etSubThread.isEnabled = true
                    binding.etSubThread.setText("")
                    Toast.makeText(this, "Reply Success", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun sendSubThread() {
        val content = binding.etSubThread.text.toString().trim()
        viewModel.replyThread(content)
    }

    private fun setupToolbar() {
        title = viewModel.parentThread?.title.orEmpty()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun showLoading(isShowLoading: Boolean) {
        binding.rvDetailThread.isVisible = !isShowLoading
        binding.tvErrorHome.isVisible = !isShowLoading
        binding.pbDetail.isVisible = isShowLoading
    }

    private fun showError() {
        binding.rvDetailThread.isVisible = false
        binding.pbDetail.isVisible = false
        binding.tvErrorHome.isVisible = true
    }

    private fun showData() {
        binding.rvDetailThread.isVisible = true
        binding.pbDetail.isVisible = false
        binding.tvErrorHome.isVisible = false
    }

    private fun showEmptyData() {
        showError()
        setErrorMessage(getText(R.string.text_empty_data_subthread).toString())
    }

    private fun setErrorMessage(msg: String) {
        binding.tvErrorHome.text = msg
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setParentThreadData(item: ThreadItem?) {
        item?.let {
            binding.ivProfilePict.load(item.creator?.photoProfileUrl) {
                crossfade(true)
                placeholder(R.drawable.ic_person)
                error(R.drawable.ic_person)
                transformations(CircleCropTransformation())
            }
            binding.tvTitleThread.text = item.title
            binding.tvContentThread.text = item.content
            binding.tvNameThreadStarter.text = getString(
                R.string.text_container_display_creator_thread,
                item.creator?.displayName
            )

        }
    }

    companion object {
        const val EXTRAS_PARENT_THREAD = "EXTRAS_PARENT_THREAD"
        fun startActivity(context: Context, parentThread: ThreadItem) {
            context.startActivity(Intent(context, ThreadDetailActivity::class.java).apply {
                putExtra(EXTRAS_PARENT_THREAD, parentThread)
            })
        }
    }
}