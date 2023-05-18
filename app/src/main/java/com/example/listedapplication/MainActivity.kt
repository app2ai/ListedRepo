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
import com.example.listedapplication.utils.FetchLinksFromRemoteListener
import com.example.listedapplication.utils.FetchRecLinksFromRemoteListener
import com.example.listedapplication.viewmodel.MainActivityViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var listener: FetchLinksFromRemoteListener
    private lateinit var recListener: FetchRecLinksFromRemoteListener

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

        viewModel.callDashboardRemotely()
        observeData()

        setupViewPager(binding.pager)
        binding.tabLayout.setupWithViewPager(binding.pager)
    }

    // This method is responsible for getting object of listener
    fun passInterface(listener: FetchLinksFromRemoteListener) {
        this.listener = listener
    }

    fun passRecInterface(listener: FetchRecLinksFromRemoteListener) {
        recListener = listener
    }

    override fun onResume() {
        super.onResume()
        viewModel.checkForGreeting()
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

        viewModel.greeting.observe(this) {
            binding.greetingTxt.text = it
        }
        viewModel.data.observe(this) {
            binding.todaysClick.text = if (it.today_clicks == 0) "No clicks" else it.today_clicks.toString()
            binding.location.text = it.top_location.ifEmpty { "NA" }
            binding.socialMedia.text = it.top_source.ifEmpty { "NA" }
            listener.onLinksFetched(it.mData.top_links)
            recListener.onRecLinksFetched(it.mData.recent_links)
        }
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = FragmentPagerAdapter(supportFragmentManager)
        adapter.addFragment(TopLinksFragment(), getString(R.string.txt_top_links))
        adapter.addFragment(RecentLinksFragment(), getString(R.string.txt_rec_links))
        viewPager.adapter = adapter
    }
}