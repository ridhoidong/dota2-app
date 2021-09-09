package com.idong.dota2app.ui.account

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.idong.core.utils.viewBinding
import com.idong.dota2app.R
import com.idong.dota2app.databinding.FragmentAccountBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by ridhopratama on 04,September,2021
 */

@AndroidEntryPoint
class AccountFragment : Fragment(R.layout.fragment_account) {

    private val binding by viewBinding(FragmentAccountBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
    }

    private fun setupToolbar() {
        with(binding.toolbar) {
            btnBack.visibility = View.GONE
            tvTitle.text = getString(R.string.menu_account)
        }
    }
}