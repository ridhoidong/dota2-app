package com.idong.dota2app.ui.detail

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.idong.core.BuildConfig
import com.idong.core.data.source.local.entity.Ability
import com.idong.core.utils.GeneralUtil.toSpanned
import com.idong.dota2app.R
import com.idong.dota2app.databinding.BottomSheetDetailAbilityBinding


/**
 * Created by ridhopratama on 03,September,2021
 */

class AbilityBottomSheet () : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetDetailAbilityBinding
    private var ability: Ability? = null

    companion object {
        const val TAG_BS = "ability_bottom_sheet"
    }

    fun setData(data: Ability) {
        this.ability = data
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppBottomSheetDialogTheme)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog =  super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        bottomSheetDialog.setOnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
            val bottomSheet = d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout?
            val behavior = BottomSheetBehavior.from(bottomSheet!!)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        return bottomSheetDialog
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val dialog = inflater.inflate(R.layout.bottom_sheet_detail_ability, null)
        binding = BottomSheetDetailAbilityBinding.inflate(layoutInflater, dialog as ViewGroup, false)

        with(binding) {
            ability.let {
                tvAbilityName.text = ability?.localizedName
                Glide.with(requireContext())
                    .load(BuildConfig.CDN_HERO_ABILITY + ability?.name + ".png")
                    .apply(RequestOptions.placeholderOf(R.drawable.img_loading)
                        .error(R.drawable.img_logo_dota2))
                    .into(ivAbility)
                tvAbilitiyDescription.text = ability?.description?.toSpanned()
            }
        }

        return binding.root
    }
}