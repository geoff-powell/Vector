package com.greenmist.vector.sample.screens

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.MenuItem
import com.greenmist.vector.sample.R
import kotlinx.android.synthetic.main.activity_drawer.*

abstract class BaseActivity : AppCompatActivity() {

    abstract val layoutId: Int
    abstract val drawerItemId: Int
    open val toolbarTitleId: Int = R.string.app_name

    private lateinit var drawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawer)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setTitle(toolbarTitleId)

        layoutInflater.inflate(layoutId, content_frame, true)

        navigation.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                drawerItemId -> {}
                R.id.vector -> {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                }
                R.id.transforms -> {
                    startActivity(Intent(this, TransformsActivity::class.java))
                }
                R.id.simple_shapes -> {
                    startActivity(Intent(this, SimpleShapesActivity::class.java))
                }
                R.id.path -> {
                    startActivity(Intent(this, PathActivity::class.java))
                }
                R.id.fill -> {
                    startActivity(Intent(this, FillActivity::class.java))
                }
                R.id.advanced -> {
                    startActivity(Intent(this, AdvancedActivity::class.java))
                }
            }
            drawer_layout.closeDrawers()

            return@setNavigationItemSelectedListener true
        }

        drawerToggle = ActionBarDrawerToggle(this, drawer_layout, R.string.app_name, R.string.app_name)

        drawer_layout.addDrawerListener(drawerToggle)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        drawerToggle.syncState()
    }

    override fun onOptionsItemSelected(item: MenuItem) : Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                drawer_layout.openDrawer(Gravity.START)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(Gravity.START)) {
            drawer_layout.closeDrawers()
        } else {
            super.onBackPressed()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        drawerToggle.onConfigurationChanged(newConfig)
    }
}