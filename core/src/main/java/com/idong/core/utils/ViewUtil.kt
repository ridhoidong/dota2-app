package com.idong.core.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.navigation.ActivityNavigator


/**
 * Created by ridhopratama on 20,December,2020
 */

object ViewUtil {

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun navigateNextActivity(context: Context, clazz: Class<*>, bundle: Bundle?) {
        val destination = ActivityNavigator(context)
                                .createDestination()
                                .setIntent(Intent(context, clazz))
        ActivityNavigator(context).navigate(destination, bundle ?: bundle, null, null)
    }
}