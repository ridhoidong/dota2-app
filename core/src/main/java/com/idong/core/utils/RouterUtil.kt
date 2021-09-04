package com.idong.core.utils

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by ridhopratama on 04,September,2021
 */
class RouterUtil(context: Context) {

    var context: Context? = null
    var intent: Intent? = null
    init {
        this.context = context
    }

    fun openActivity(nextClass: Class<Any>) {
        try {
            intent = Intent(context, nextClass)
            context?.startActivity(intent)
        }
        catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun openActivityAndClearAllPrevious(nextClass: Class<*>) {
        try {
            intent = Intent(context, nextClass)
            intent!!.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            context?.startActivity(intent)
            (context as AppCompatActivity).finish()
        }
        catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}