package com.application.pis.catatuang.utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

/**
 * Created by ridhopratama on 31,August,2021
 */

@Suppress("DEPRECATION")
abstract class BackStackFragment : Fragment() {
    protected fun onBackPressed(): Boolean {
        val fm = childFragmentManager
        if (handleBackPressed(fm)) {
            return true
        } else if (userVisibleHint && fm.backStackEntryCount > 0) {
            fm.popBackStack()
            return true
        }
        return false
    }

    companion object {
        fun handleBackPressed(fm: FragmentManager): Boolean {
            if (fm.fragments != null) {
                for (frag in fm.fragments) {
                    if (frag != null && frag.isVisible && frag is BackStackFragment) {
                        if (frag.onBackPressed()) {
                            return true
                        }
                    }
                }
            }
            return false
        }
    }
}
