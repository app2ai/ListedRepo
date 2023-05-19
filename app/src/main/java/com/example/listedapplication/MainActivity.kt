package com.example.listedapplication

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.example.listedapplication.databinding.ActivityMainBinding
import com.example.listedapplication.ui.FragmentPagerAdapter
import com.example.listedapplication.ui.RecentLinksFragment
import com.example.listedapplication.ui.TopLinksFragment
import com.example.listedapplication.utils.DateUtils
import com.example.listedapplication.utils.FetchLinksFromRemoteListener
import com.example.listedapplication.utils.FetchRecLinksFromRemoteListener
import com.example.listedapplication.utils.greatestValue
import com.example.listedapplication.viewmodel.MainActivityViewModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend.LegendForm
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import javax.inject.Inject

class MainActivity : AppCompatActivity(), OnChartValueSelectedListener {
    lateinit var binding: ActivityMainBinding
    private lateinit var listener: FetchLinksFromRemoteListener
    private lateinit var recListener: FetchRecLinksFromRemoteListener
    private lateinit var chart: LineChart
    private lateinit var whatsappNumber: String

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

        binding.whatsappCard.setOnClickListener {
            gotoWhatsapp()
        }
        binding.faqCard.setOnClickListener {
            Toast.makeText(this, "Feature not implemented yet...", Toast.LENGTH_SHORT).show()
        }
    }

    private fun gotoWhatsapp() {
        if (whatsappNumber.isNotEmpty()) {
            val url = getString(R.string.whatsapp_string, whatsappNumber)
            try {
                val pm: PackageManager = this.packageManager
                pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES)
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                this.startActivity(i)
            } catch (e: PackageManager.NameNotFoundException) {
                Toast.makeText(
                    this,
                    "Whatsapp app not installed in your phone",
                    Toast.LENGTH_LONG
                ).show()
                e.printStackTrace()
            }
        } else {
            Toast.makeText(this, "Number not found", Toast.LENGTH_LONG).show()
        }
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
                when (it) {
                    MainActivityViewModel.DashFailed -> {
                        Toast.makeText(this, "API failed to load data", Toast.LENGTH_SHORT).show()
                        switchProgressBar(false)
                    }
                    MainActivityViewModel.DashInProgress -> {
                        switchProgressBar(true)
                    }
                    is MainActivityViewModel.DashSuccess -> {
                        Toast.makeText(this, "API successfully called", Toast.LENGTH_SHORT).show()
                        switchProgressBar(false)
                    }
                }
            }
        }

        viewModel.greeting.observe(this) {
            binding.greetingTxt.text = it
        }
        viewModel.data.observe(this) {
            binding.todaysClick.text =
                if (it.today_clicks == 0) "No clicks" else it.today_clicks.toString()
            binding.location.text = it.top_location.ifEmpty { "NA" }
            binding.socialMedia.text = it.top_source.ifEmpty { "NA" }
            listener.onLinksFetched(it.mData.top_links)
            recListener.onRecLinksFetched(it.mData.recent_links)
            setDataToChart(it.mData.overall_url_chart)
            whatsappNumber = it.support_whatsapp_number
        }
    }

    private fun setDataToChart(overallUrlChart: Map<String, Int>) {
        val count = overallUrlChart.size
        val topValue = overallUrlChart.greatestValue()
        setData(count, topValue, overallUrlChart)
    }

    private fun setData(count: Int, top: Int, arr: Map<String, Int>) {
        val fDate = DateUtils.dateFormatForSameYear(arr.entries.first().key)
        val lDate = DateUtils.dateFormatForSameYear(arr.entries.last().key)
        binding.dateToAndFrom.text = "$fDate - $lDate"
        chart = binding.lineChart

        chart.setBackgroundColor(getColor(R.color.white))
        chart.description.isEnabled = false
        chart.setTouchEnabled(true)

        // set listeners
        chart.setOnChartValueSelectedListener(this)
        // enable scaling and dragging
        chart.isDragEnabled = true
        chart.setScaleEnabled(true)
        chart.zoom(2.8f, 0f, 1f, 1f)
        // get the legend (only possible after setting data)
        val l = chart.legend
        // draw legend entries as lines
        l.form = LegendForm.EMPTY

        val xAxis = chart.xAxis
        xAxis.axisLineColor = getColor(R.color.toolbar_color)
        xAxis.labelRotationAngle = 90f
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        val yAxis = chart.axisLeft
        chart.axisRight.isEnabled = false

        // axis range
        yAxis.axisMaximum = top.toFloat()
        yAxis.axisMinimum = 0f

        val values: ArrayList<Entry> = ArrayList()
        var occ = 0f
        for (i in arr.values) {
            occ += 1
            values.add(Entry(occ, i.toFloat()))
        }

        // create a dataset and give it a type
        val set1 = LineDataSet(values, getString(R.string.txt_overview)) // This will show first to last date on chart

        set1.color = getColor(R.color.toolbar_color)

        // customize legend entry
        set1.formLineWidth = 4f
        set1.formSize = 15f

        // text size of values
        set1.valueTextSize = 9f

        // draw selection line as dashed
        set1.enableDashedHighlightLine(10f, 5f, 0f)

        // set the filled area
        set1.setDrawFilled(true)
        set1.fillFormatter = IFillFormatter { dataSet, dataProvider ->
            chart.axisLeft.axisMinimum
        }

        val drawable = getDrawable(R.drawable.fade_blue)
        set1.fillDrawable = drawable

        var dataSets = ArrayList<ILineDataSet>()
        dataSets.add(set1) // add the data sets

        // create a data object with the data sets
        val data = LineData(dataSets)

        // set data
        chart.data = data
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = FragmentPagerAdapter(supportFragmentManager)
        adapter.addFragment(TopLinksFragment(), getString(R.string.txt_top_links))
        adapter.addFragment(RecentLinksFragment(), getString(R.string.txt_rec_links))
        viewPager.adapter = adapter
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
    }

    override fun onNothingSelected() {
    }

    private fun switchProgressBar(isActive: Boolean) {
        if (isActive) {
            binding.progressCircular.visibility = View.VISIBLE
        } else {
            binding.progressCircular.visibility = View.GONE
        }
    }
}