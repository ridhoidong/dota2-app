package com.application.idong.mypdam.ui.bottomnavigation

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.application.idong.mypdam.models.BottombarModel
import com.idong.dota2app.R

/**
 * Created by ridhopratama on 30,August,2021
 */

class BottomNavigationAdapter(defaultOpenIndex: Int,
                              items: ArrayList<BottombarModel>,
                              itemSelectorInterface: ItemSelectorInterface): RecyclerView.Adapter<BottomNavigationAdapter.BottomBarViewHolder>() {

    private var maxTitleLength: Int = 9
    private var items: ArrayList<BottombarModel> = arrayListOf()
    private var defaultBackground: String  = ""
    private var defaultTint = ""
    private var itemSelectorInterface: ItemSelectorInterface

    init {
        this.items = items
        this.itemSelectorInterface = itemSelectorInterface
        setDefaultOpen(defaultOpenIndex)
        this.itemSelectorInterface.itemSelected(items[defaultOpenIndex])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BottomBarViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_bottombar, parent, false)
        return BottomBarViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: BottomBarViewHolder, position: Int) {
        holder.let {
            setOnClickItem(it.clickParent, position, items[position].itemId)
            setIcon(it.itemIcon, items[position].itemIconId)
            setTitle(it.itemTitle, items[position].itemTitle)
            setSelectedItemStyle(it.itemParent, it.itemTitle, it.itemIcon, items[position].getOpen(), items[position])
        }
    }

    private fun setDefaultOpen(index: Int) {
        if (index > -1 && index <= items.size - 1) {
            items.get(index).setOpen(true)
        }
        else {
            if (items.isNotEmpty()) {
                items.get(0).setOpen(true)
            }
        }
    }

    private fun setIcon(iconImage: ImageView, iconId: Int) {
        iconImage.setImageResource(iconId)
    }

    private fun setTitle(textView: TextView, title: String) {
        if (title.length > maxTitleLength)
            textView.text = StringBuilder(title.substring(0, maxTitleLength) + "...")
        else
            textView.text = title
    }

    private fun setSelectedItemStyle(itemParent: CardView, title: TextView, icon: ImageView, isOpen: Boolean, item: BottombarModel) {
        if (isOpen) {
            title.visibility = View.VISIBLE
            itemParent.setCardBackgroundColor(Color.parseColor(item.itemBackgroundColor))
            title.setTextColor(Color.parseColor(item.itemTintColor))
            icon.setImageResource(item.itemIconId)
            icon.setColorFilter(Color.parseColor(item.itemTintColor), PorterDuff.Mode.SRC_IN)
        }
        else {
            title.visibility = View.GONE
            itemParent.setCardBackgroundColor(Color.parseColor(getDefaultBackground()))
            title.setTextColor(Color.parseColor(getDefaultTint()))
            icon.setImageResource(item.itemIconId)
            icon.setColorFilter(Color.parseColor(getDefaultTint()), PorterDuff.Mode.SRC_IN)
        }
    }

    private fun setOnClickItem(parent: RelativeLayout, position: Int, itemId: Int) {
        parent.setOnClickListener { view ->
            changeCloseData()
            items[position].setOpen(true)
            notifyDataSetChanged()
            itemSelectorInterface.itemSelected(items[position])
        }
    }

    private fun changeCloseData() {
        for (i in 0 until items.size) {
            items[i].setOpen(false)
        }
    }

    private fun getDefaultTint(): String {
        return defaultTint
    }

    fun setDefaultTint(defaultTint: String) {
        this.defaultTint = defaultTint
    }

    private fun getDefaultBackground(): String? {
        return defaultBackground
    }

    fun setDefaultBackground(defaultBackground: String) {
        this.defaultBackground = defaultBackground
    }

    fun removeSelectedAndOpenNew(position: Int) {
        changeCloseData()
        items[position].setOpen(true)
        notifyDataSetChanged()
        itemSelectorInterface.itemSelected(items[position])
    }

    inner class BottomBarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var itemParent: CardView = itemView.findViewById(R.id.item_parent)
        internal var clickParent: RelativeLayout = itemView.findViewById(R.id.click_parent)
        internal var itemIcon: ImageView = itemView.findViewById(R.id.item_icon)
        internal var itemTitle: TextView = itemView.findViewById(R.id.item_title)
    }

    interface ItemSelectorInterface {
        fun itemSelected (bottombarModel: BottombarModel)
    }
}
