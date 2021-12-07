package com.temankerja.temankerja.ui.recruiter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.temankerja.temankerja.R
import com.temankerja.temankerja.data.UserPreference
import com.temankerja.temankerja.databinding.ActivityRecruiterBinding
import com.temankerja.temankerja.models.Jobs
import com.temankerja.temankerja.models.Users
import com.temankerja.temankerja.ui.AboutUsActivity
import com.temankerja.temankerja.ui.applicants.ApplicantsActivity
import com.temankerja.temankerja.ui.change.ChangeActivity
import com.temankerja.temankerja.ui.createjobs.CreateJobsActivity
import com.temankerja.temankerja.ui.login.LoginActivity
import com.temankerja.temankerja.ui.user.biodata.BiodataActivity
import com.temankerja.temankerja.ui.user.detail.DetailActivity
import com.temankerja.temankerja.ui.user.detailbiodata.DetailBiodataActivity
import com.temankerja.temankerja.ui.user.home.HomeAdapter
import com.temankerja.temankerja.ui.user.notification.NotificationActivity
import com.temankerja.temankerja.utils.AuthType
import com.temankerja.temankerja.utils.StatusBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class RecruiterActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityRecruiterBinding
    private lateinit var toggle: ActionBarDrawerToggle
    private val viewModel: RecruiterViewModel by viewModels()
    private val adapter by lazy { RecruiterAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecruiterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val userPreference = UserPreference(this)
        setSupportActionBar(findViewById(R.id.toolbar2))
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        val tvSidebarEmail =
            binding.navView.getHeaderView(0).findViewById<TextView>(R.id.tv_sidebar_email)
        val imgHeader = binding.navView.getHeaderView(0).findViewById<ImageView>(R.id.img_header)
        val tvSidebarName = binding.navView.getHeaderView(0).findViewById<TextView>(R.id.tv_sidebar_name)
        tvSidebarEmail.text = userPreference.getUser().email
        tvSidebarName.text = userPreference.getUser().fullname
        if(userPreference.getUser().photo != ""){
            Glide.with(this)
                .load(userPreference.getUser().photo)
                .into(imgHeader)
        }
        StatusBar.changeColor(window, this)
        window.statusBarColor = ContextCompat.getColor(this, R.color.purple)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.drawer_profile -> {
                    Intent(this, DetailBiodataActivity::class.java).apply {
                        startActivity(this)
                    }
                }
                R.id.drawer_about -> startActivity(Intent(this, AboutUsActivity::class.java))
                R.id.drawer_setting -> startActivity(Intent(this, ChangeActivity::class.java))
                R.id.drawer_logout -> {
                    Intent(this, LoginActivity::class.java).apply {
                        userPreference.setUser(Users(), AuthType.LOGOUT)
                        finishAndRemoveTask()
                        startActivity(this)
                    }
                }
            }
            true
        }

        binding.fab.setOnClickListener(this)

        setupRv()
        runBlocking {
            viewModel.jobsList.observe(this@RecruiterActivity, {
                it.data?.let { data -> adapter.setJobsData(data) }
            })
        }

        binding.textInputSearch.addTextChangedListener {
            viewModel.searchNameChanged(it.toString())
            binding.rvLowongan.adapter!!.notifyDataSetChanged()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_home_menu_recruiter, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        when (item.itemId) {
            R.id.nav_notif -> {
                Intent(this, ApplicantsActivity::class.java).apply {
                    startActivity(this)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupRv() {
        binding.rvLowongan.adapter = adapter
        binding.rvLowongan.layoutManager = LinearLayoutManager(this)
        binding.rvLowongan.setHasFixedSize(true)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fab -> {
                startActivity(Intent(this, CreateJobsActivity::class.java))
            }
        }
    }
}