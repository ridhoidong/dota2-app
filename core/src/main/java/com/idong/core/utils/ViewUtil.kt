package com.idong.core.utils

import android.content.Context
import android.widget.Toast


/**
 * Created by ridhopratama on 20,December,2020
 */

object ViewUtil {

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}