package com.idong.dota2app.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.idong.core.utils.BackStackFragment
import com.idong.core.utils.ViewUtil
import com.idong.core.utils.viewBinding
import com.idong.dota2app.R
import com.idong.dota2app.databinding.ActivityMainBinding
import com.idong.dota2app.ui.menu.BottomNavigation
import com.idong.dota2app.ui.menu.BottomNavigationAdapter
import com.idong.dota2app.ui.menu.ListMenu
import com.idong.dota2app.ui.menu.model.BottombarModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), BottomNavigationAdapter.ItemSelectorInterface {

    private val binding by viewBinding(ActivityMainBinding::inflate)
    @Inject
    lateinit var bottomNavigation: BottomNavigation
    private var doubleBackToExitPressedOnce = false
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
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
            getString(R.string.menu_home) -> navController.navigate(R.id.homeFragment)
            getString(R.string.menu_favorite) -> navController.navigate(R.id.favoriteFragment)
            getString(R.string.menu_account) -> navController.navigate(R.id.accountFragment)
        }
    }
}