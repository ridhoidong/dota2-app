package com.application.idong.mypdam.ui.bottomnavigation

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.application.idong.mypdam.models.BottombarModel
import com.google.android.material.card.MaterialCardView
import com.idong.dota2app.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by ridhopratama on 30,August,2021
 */

class BottomNavigation (private val context: Context) {

    private val itemLimit = 5
    private var customBottombarParent: MaterialCardView? = null
    private var customRecyclerView: RecyclerView? = null
    private var items: ArrayList<BottombarModel> = arrayListOf()
    private var defaultBackground = ""
    private var defaultTint = ""
    private var bottomNavigationAdapter: BottomNavigationAdapter? = null
    private var itemSelectorInterface: BottomNavigationAdapter.ItemSelectorInterface? = null

    fun setView(view: View) {
        this.customBottombarParent = view.findViewById(R.id.custom_bottom_bar_parent)
        this.customRecyclerView = view.findViewById(R.id.rvBottomMenu)
    }

    fun setListener(itemSelectorInterface: BottomNavigationAdapter.ItemSelectorInterface) {
        this.itemSelectorInterface = itemSelectorInterface
    }

    private fun addItem(item: BottombarModel) {
        if (items.size <= itemLimit - 1) {
            items.add(item)
        }
    }

    fun addAllItem(itemData: ArrayList<BottombarModel>) {
        items.clear()
        items.addAll(itemData)
    }

    fun setBackgroundBar(color: String) {
        customBottombarParent?.setCardBackgroundColor(Color.parseColor(color))
    }

    private fun getDefaultBackground(): String {
        return defaultBackground
    }

    fun setDefaultBackground(defaultBackground: String) {
        this.defaultBackground = defaultBackground
    }

    private fun getDefaultTint(): String {
        return defaultTint
    }

    fun setDefaultTint(defaultTint: String) {
        this.defaultTint = defaultTint
    }

    private fun setAdapter(defaultOpenIndex: Int) {
        bottomNavigationAdapter = itemSelectorInterface?.let { BottomNavigationAdapter(defaultOpenIndex, items, it) }
        bottomNavigationAdapter?.setDefaultBackground(getDefaultBackground())
        bottomNavigationAdapter?.setDefaultTint(getDefaultTint())
        customRecyclerView?.layoutManager = GridLayoutManager(context, items.size)
        customRecyclerView?.adapter = bottomNavigationAdapter
    }

    fun apply(defaultOpenIndex: Int) {
        setAdapter(defaultOpenIndex)
    }

    fun removeSelectedAndOpenNew(position: Int) {
        bottomNavigationAdapter?.removeSelectedAndOpenNew(position)
    }
}