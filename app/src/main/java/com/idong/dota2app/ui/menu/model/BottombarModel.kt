package com.idong.dota2app.ui.menu.model

/**
 * Created by ridhopratama on 30,Agustus,2021
 */

data class BottombarModel(
    val itemId: Int,
    val itemIconId: Int,
    val itemIconIdFill: Int,
    val itemTitle: String,
    val itemBackgroundColor: String,
    val itemTintColor: String
) {
    private var isOpen: Boolean = false

    fun setOpen(open: Boolean) {
        isOpen = open
    }

    fun getOpen(): Boolean {
        return isOpen
    }
}