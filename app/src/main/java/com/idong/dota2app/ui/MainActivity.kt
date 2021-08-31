package com.idong.dota2app.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.application.idong.mypdam.models.BottombarModel
import com.application.idong.mypdam.ui.bottomnavigation.BottomNavigation
import com.application.idong.mypdam.ui.bottomnavigation.BottomNavigationAdapter
import com.application.pis.catatuang.utils.BackStackFragment
import com.idong.core.utils.ViewUtil
import com.idong.dota2app.R
import com.idong.dota2app.databinding.ActivityMainBinding
import com.idong.dota2app.databinding.FragmentHomeBinding
import com.idong.dota2app.ui.menu.ListMenu
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), BottomNavigationAdapter.ItemSelectorInterface {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    @Inject
    lateinit var bottomNavigation: BottomNavigation
    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.customBottomBar.root.visibility = View.VISIBLE
        initializeMenuAndSubmenu()
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (!BackStackFragment.handleBackPressed(supportFragmentManager)) {
                    if (doubleBackToExitPressedOnce) {
                        finishAffinity()
                        return
                    }
                    bottomNavigation.removeSelectedAndOpenNew(0)
                    doubleBackToExitPressedOnce = true
                    ViewUtil.showToast(this@MainActivity, getString(R.string.label_press_once_again_to_exit_application))
                    Handler(Looper.getMainLooper()).postDelayed({
                        doubleBackToExitPressedOnce = false
                    }, 2000)
                }
            }
        })
    }

    private fun initializeMenuAndSubmenu() {
        bottomNavigation.apply {
            setView(binding.customBottomBar.root)
            setListener(this@MainActivity)
            addAllItem(ListMenu.mainMenu(this@MainActivity))
            setBackgroundBar("#" + Integer.toHexString(ContextCompat.getColor(this@MainActivity, R.color.colorBottomNavigationBackground)))
            setDefaultBackground("#" + Integer.toHexString(ContextCompat.getColor(this@MainActivity, R.color.colorBottomNavigationBackground)))
            setDefaultTint("#" + Integer.toHexString(ContextCompat.getColor(this@MainActivity, R.color.grey_600)))
            apply(0)
        }
    }

    override fun itemSelected(bottombarModel: BottombarModel) {
        when(bottombarModel.itemTitle) {
            getString(R.string.menu_home) -> findNavController(R.id.nav_host_fragment).navigate(R.id.homeFragment)
            else -> Toast.makeText(this, "Not ready yet", Toast.LENGTH_SHORT)
        }
    }
}