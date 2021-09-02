package com.idong.core.utils

import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.math.BigDecimal
import java.math.RoundingMode

/**
 * Created by ridhopratama on 02,September,2021
 */
object GeneralUtil {

    fun String.toRound(scale: Int) : String {
        val decimal = BigDecimal(this).setScale(scale, RoundingMode.HALF_EVEN)
        return decimal.toString()
    }

    fun String.toSpanned(): Spanned {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
        } else {
            @Suppress("DEPRECATION")
            return Html.fromHtml(this)
        }
    }

    fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
        observe(lifecycleOwner, object : Observer<T> {
            override fun onChanged(t: T?) {
                observer.onChanged(t)
                removeObserver(this)
            }
        })
    }

}