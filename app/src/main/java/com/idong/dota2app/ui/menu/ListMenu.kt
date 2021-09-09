package com.idong.dota2app.ui.menu

import android.content.Context
import androidx.core.content.ContextCompat
import com.idong.dota2app.R
import com.idong.dota2app.ui.menu.model.BottombarModel

/**
 * Created by ridhopratama on 31,August,2021
 */
object ListMenu {

    fun mainMenu(context: Context): ArrayList<BottombarModel> {
        return arrayListOf(
            BottombarModel(
                0,
                R.drawable.ic_home_24dp,
                R.drawable.ic_home_24dp,
                context.getString(R.string.menu_home),
                "#" + Integer.toHexString(
                    ContextCompat.getColor(
                        context,
                        R.color.drk_menu
                    )
                ),
                "#" + Integer.toHexString(
                    ContextCompat.getColor(
                        context,
                        R.color.colorWhite
                    )
                )
            ),
            BottombarModel(
                1,
                R.drawable.ic_favorite_24dp,
                R.drawable.ic_favorite_24dp,
                context.getString(R.string.menu_favorite),
                "#" + Integer.toHexString(
                    ContextCompat.getColor(
                        context,
                        R.color.drk_menu
                    )
                ),
                "#" + Integer.toHexString(
                    ContextCompat.getColor(
                        context,
                        R.color.colorWhite
                    )
                )
            ),
            BottombarModel(
                2,
                R.drawable.ic_profile_24dp,
                R.drawable.ic_profile_24dp,
                context.getString(R.string.menu_account),
                "#" + Integer.toHexString(
                    ContextCompat.getColor(
                        context,
                        R.color.drk_menu

                    )
                ),
                "#" + Integer.toHexString(
                    ContextCompat.getColor(
                        context,
                        R.color.colorWhite
                    )
                )
            )
        )
    }

}