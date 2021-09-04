package com.idong.dota2app.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.idong.dota2app.R
import com.idong.dota2app.databinding.FragmentAccountBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by ridhopratama on 04,September,2021
 */

@AndroidEntryPoint
class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupToolbar() {
        with(binding.toolbar) {
            btnBack.visibility = View.GONE
            tvTitle.text = getString(R.string.menu_account)
        }
    }
}