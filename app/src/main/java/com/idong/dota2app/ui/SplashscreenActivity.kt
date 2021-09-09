package com.idong.dota2app.ui

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.idong.core.utils.Constant
import com.idong.core.utils.RouterUtil
import com.idong.dota2app.databinding.ActivitySplashscreenBinding

/**
 * Created by ridhopratama on 04,September,2021
 */

class SplashscreenActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivitySplashscreenBinding::inflate)
    private val appRoute by lazy {
        RouterUtil(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        }
        else {
            @Suppress("DEPRECATION")
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        gotoNextActivity()
    }

    private fun gotoNextActivity() {
        Handler(Looper.getMainLooper()).postDelayed({
            appRoute.openActivityAndClearAllPrevious(MainActivity::class.java)
        }, Constant.DELAY_SPLASHSCREEN)
    }

    private inline fun <T : ViewBinding> AppCompatActivity.viewBinding(
            crossinline bindingInflater: (LayoutInflater) -> T) =
            lazy(LazyThreadSafetyMode.NONE) {
                bindingInflater.invoke(layoutInflater)
            }
}