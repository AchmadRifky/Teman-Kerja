package com.temankerja.temankerja.ui.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.temankerja.temankerja.R
import com.temankerja.temankerja.data.UserPreference
import com.temankerja.temankerja.databinding.ActivityUserBinding
import com.temankerja.temankerja.models.Users
import com.temankerja.temankerja.ui.AboutUsActivity
import com.temankerja.temankerja.ui.change.ChangeActivity
import com.temankerja.temankerja.ui.login.LoginActivity
import com.temankerja.temankerja.ui.user.biodata.BiodataActivity
import com.temankerja.temankerja.ui.user.detailbiodata.DetailBiodataActivity
import com.temankerja.temankerja.ui.user.home.HomeFragment
import com.temankerja.temankerja.ui.user.informasi.InformasiFragment
import com.temankerja.temankerja.ui.user.notification.NotificationActivity
import com.temankerja.temankerja.ui.user.sertifikasi.SertifikasiFragment
import com.temankerja.temankerja.utils.AuthType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserBinding
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val userPreference = UserPreference(this)
        setSupportActionBar(findViewById(R.id.toolbar2))
        addFragment(HomeFragment())
        binding.navMenu.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.nav_home -> {
                    binding.userInclude.tvNavTitle.text = "Home"
                    addFragment(HomeFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.nav_informasi -> {
                    addFragment(InformasiFragment())
                    binding.userInclude.tvNavTitle.text = "Informasi"
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.nav_sertifikasi -> {
                    addFragment(SertifikasiFragment())
                    binding.userInclude.tvNavTitle.text = "Sertifikasi"
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        val tvSidebarEmail = binding.navView.getHeaderView(0).findViewById<TextView>(R.id.tv_sidebar_email)
        val imgHeader = binding.navView.getHeaderView(0).findViewById<ImageView>(R.id.img_header)
        val tvSidebarName = binding.navView.getHeaderView(0).findViewById<TextView>(R.id.tv_sidebar_name)
        tvSidebarEmail.text = userPreference.getUser().email
        tvSidebarName.text = userPreference.getUser().fullname
        if(userPreference.getUser().photo != ""){
            Glide.with(this)
                .load(userPreference.getUser().photo)
                .into(imgHeader)
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.drawer_profile -> {
                    Intent(this, DetailBiodataActivity::class.java).apply {
                        startActivity(this)
                    }
                }
                R.id.drawer_about -> startActivity(Intent(this, AboutUsActivity::class.java))
                R.id.drawer_setting -> startActivity(Intent(this, ChangeActivity::class.java))
                R.id.drawer_logout -> {
                    Intent(this,  LoginActivity::class.java).apply {
                        userPreference.setUser(Users(), AuthType.LOGOUT)
                        finishAndRemoveTask()
                        startActivity(this)
                    }
                }
            }
            true
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_home_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)) {
            return true
        }
        when(item.itemId) {
            R.id.nav_notif -> {
                Intent(this, NotificationActivity::class.java).apply {
                    startActivity(this)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
}