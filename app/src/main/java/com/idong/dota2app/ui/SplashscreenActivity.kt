package com.idong.dota2app.ui

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.idong.core.utils.Constant
import com.idong.core.utils.RouterUtil
import com.idong.dota2app.databinding.ActivitySplashscreenBinding

/**
 * Created by ridhopratama on 04,September,2021
 */

class SplashscreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashscreenBinding
    private val appRoute by lazy {
        RouterUtil(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashscreenBinding.inflate(layoutInflater)
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
}