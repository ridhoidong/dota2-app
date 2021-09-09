package com.idong.core.utils

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.idong.core.BuildConfig
import com.idong.core.R


/**
 * Created by ridhopratama on 20,December,2020
 */

object ViewUtil {

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun ImageView.load(imageSource: String) {
        Glide.with(context.applicationContext)
                .load(BuildConfig.CDN_HERO_ABILITY + imageSource + ".png")
                .apply(RequestOptions.placeholderOf(R.drawable.img_loading)
                        .error(R.drawable.img_logo_dota2))
                .into(this)
    }

    fun showNotification(context: Context, view: View, message: String, length: Int = Snackbar.LENGTH_SHORT) {
        val snack = Snackbar.make(view, message, length)
        snack.apply {
            setBackgroundTint(ContextCompat.getColor(context, android.R.color.black))
        }.show()
    }
}