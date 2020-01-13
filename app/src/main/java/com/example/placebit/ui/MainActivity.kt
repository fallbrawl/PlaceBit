package com.example.placebit.ui

import android.app.Dialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.attractgroup.bluemoon.extensions.obtainViewModel
import com.example.placebit.R
import com.example.placebit.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : ProgressActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var viewDataBinding: ActivityMainBinding
    private lateinit var currModel: MainViewModel
    private lateinit var logoutDialog: Dialog

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId) {
            R.id.logout -> {

            }
        }
        return true
    }

    override fun getProgressBar(): ProgressBar {
        return progressBar
    }

    private lateinit var host: NavHostFragment
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewDataBinding.apply {

            viewModel = obtainViewModel(MainViewModel::class.java)
            currModel = viewModel as MainViewModel
            viewModel?.apply {

            }
        }
        setToolbar()
        setupNavController()
    }

    private fun setToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    private fun setupNavController() {
        val drawerLayout: DrawerLayout? = findViewById(R.id.drawer_layout)
        val navView: NavigationView? = findViewById(R.id.nav_view)

        host = nav_host_fragment as NavHostFragment
        navController = host.navController

        val actionBarDrawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.openDrawer,
            R.string.closeDrawer
        ) {
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
            }

            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
            }
        }

        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.eventsListFragment
        ), drawerLayout)

        drawerLayout?.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        navView?.setupWithNavController(navController)
        navView?.setNavigationItemSelectedListener(this)
        setupActionBar(navController, appBarConfiguration)
        navView?.menu?.getItem(0)?.isChecked = false
    }

    private fun setupActionBar(
        navController: NavController,
        appBarConfig: AppBarConfiguration
    ) {
        setupActionBarWithNavController(navController, appBarConfig)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            closeDrawer(View(this))
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val retValue = super.onCreateOptionsMenu(menu)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        if (navigationView == null) {
            menuInflater.inflate(R.menu.nav_drawer_menu, menu)
            return true
        }
        return retValue
    }

    fun closeDrawer(v: View) {
        drawer_layout.closeDrawer(GravityCompat.START)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        return item!!.onNavDestinationSelected(findNavController(R.id.nav_host_fragment))
                || super.onOptionsItemSelected(item)
    }
}
