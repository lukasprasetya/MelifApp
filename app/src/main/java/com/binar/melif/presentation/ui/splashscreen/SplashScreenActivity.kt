package com.binar.melif.presentation.ui.splashscreen

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import com.binar.melif.base.BaseViewModelActivity
import com.binar.melif.base.wrapper.Resource
import com.binar.melif.data.repository.LocalRepository
import com.binar.melif.databinding.ActivitySplashScreenBinding
import com.binar.melif.presentation.ui.main.MainActivity
import com.binar.melif.presentation.ui.slider.LandingPageActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class SplashScreenActivity : BaseViewModelActivity<ActivitySplashScreenBinding, SplahViewModel>(
    ActivitySplashScreenBinding::inflate
) {

    override val viewModel: SplahViewModel by viewModel()

    private var timer: CountDownTimer? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()


        setTimerSplashScreen()
//        viewModel.isSkipIntro()

        try {
            val pInfo: PackageInfo = packageManager.getPackageInfo(packageName, 0)
            val version = pInfo.versionName
            binding.tvVersi.text = "Versi App : $version"
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }



    }



        override fun observeData() {
            super.observeData()
            viewModel.prefData.observe(this) {
                when (it) {
                    is Resource.Empty -> {

                    }
                    is Resource.Error -> {

                    }
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {




                    }
                }
            }
        }


    override fun onDestroy() {
        super.onDestroy()

        if (timer != null) {
            timer?.cancel()
            timer = null
        }
    }
    private fun setTimerSplashScreen() {
        timer = object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
//            Log.d("dataskip",viewModel.isSkipIntro().toString())
//                if ( data ){
//                    val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
//                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                    startActivity(intent)
//                    finish()
//                }else{
                    val intent = Intent(this@SplashScreenActivity, LandingPageActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
//                }

            }
        }
        timer?.start()
    }


}