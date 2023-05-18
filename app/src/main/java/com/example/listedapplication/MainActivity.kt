package com.example.listedapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.example.listedapplication.databinding.ActivityMainBinding
import com.example.listedapplication.ui.FragmentPagerAdapter
import com.example.listedapplication.ui.RecentLinksFragment
import com.example.listedapplication.ui.TopLinksFragment
import com.example.listedapplication.viewmodel.MainActivityViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy {
        viewModelFactory.create(
            MainActivityViewModel::class.java
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as MyApplication).appComponent.inject(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewPager(binding.pager)
        binding.tabLayout.setupWithViewPager(binding.pager)

        viewModel.callDashboardRemotely()
        observeData()
    }

    private fun observeData() {
        viewModel.resLiveData.observe(this) {
            it?.let {
                when(it) {
                    MainActivityViewModel.DashFailed -> {
                        Toast.makeText(this, "API failed to load data", Toast.LENGTH_LONG).show()
                    }
                    MainActivityViewModel.DashInProgress -> {
                        Toast.makeText(this, "Please, wait while data loaded", Toast.LENGTH_LONG).show()
                    }
                    is MainActivityViewModel.DashSuccess -> {
                        Toast.makeText(this, "API successfully called", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = FragmentPagerAdapter(supportFragmentManager)
        adapter.addFragment(TopLinksFragment(), getString(R.string.txt_top_links))
        adapter.addFragment(RecentLinksFragment(), getString(R.string.txt_rec_links))
        viewPager.adapter = adapter
    }
}