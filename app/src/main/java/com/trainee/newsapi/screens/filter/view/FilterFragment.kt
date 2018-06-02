package com.trainee.newsapi.screens.filter.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.format.DateUtils
import android.util.Log
import android.view.*
import com.trainee.newsapi.*
import com.trainee.newsapi.App.Companion.appComponent
import com.trainee.newsapi.screens.base.view.BaseFragment
import com.trainee.newsapi.screens.filter.presenter.FilterPresenterImpl
import kotlinx.android.synthetic.main.fragment_filters.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class FilterFragment : BaseFragment(), FilterView, View.OnClickListener {

    @Inject
    lateinit var presenter: FilterPresenterImpl<FilterView>

    private val dateFrom = Calendar.getInstance()
    private val dateTo = Calendar.getInstance()

    companion object {
        fun newInstance() = FilterFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_filters, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.toolbar_menu_news_filter, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.checkFilter -> checkFilter()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        setClickListener()
        initTextOnPickers()
        presenter.onAttach(this)

    }

    private fun initTextOnPickers() {
        tvFromDate.text = DateUtils.formatDateTime(this.context, dateFrom.timeInMillis, DateUtils.FORMAT_SHOW_DATE)
        tvFromTime.text = DateUtils.formatDateTime(this.context, dateFrom.timeInMillis, DateUtils.FORMAT_SHOW_TIME)
        tvToDate.text = DateUtils.formatDateTime(this.context, dateTo.timeInMillis, DateUtils.FORMAT_SHOW_DATE)
        tvToTime.text = DateUtils.formatDateTime(this.context, dateTo.timeInMillis, DateUtils.FORMAT_SHOW_TIME)

    }

    override fun checkFilter() {
        val radioButton =
                when (groupSort.checkedRadioButtonId) {
                    R.id.rbRelevance -> SORT_BY_RELEVANCY
                    R.id.rbPopularity -> SORT_BY_POPULARITY
                    else -> SORT_BY_PUBLISHED_AT
                }
        presenter.openNewsScreen(radioButton,
                SimpleDateFormat(DATE_FORMAT_SERVER_POST, Locale.getDefault()).format(dateFrom.timeInMillis),
                SimpleDateFormat(DATE_FORMAT_SERVER_POST, Locale.getDefault()).format(dateTo.time))
        Log.d("TAG", SimpleDateFormat(DATE_FORMAT_SERVER_POST, Locale.getDefault()).format(dateFrom.timeInMillis))
    }

    private fun setClickListener() {
        llFromDate.setOnClickListener(this)
        llToDate.setOnClickListener(this)
        llFromTime.setOnClickListener(this)
        llToTime.setOnClickListener(this)
        // btnApply.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.llFromDate -> DatePickerDialog(this.context, dateFromListener,
                    dateFrom.get(Calendar.YEAR),
                    dateFrom.get(Calendar.MONTH),
                    dateFrom.get(Calendar.DAY_OF_MONTH))
                    .show()
            R.id.llToDate -> DatePickerDialog(this.context, dateToListener,
                    dateTo.get(Calendar.YEAR),
                    dateTo.get(Calendar.MONTH),
                    dateTo.get(Calendar.DAY_OF_MONTH))
                    .show()
            R.id.llFromTime -> TimePickerDialog(this.context, TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                dateFrom.set(Calendar.HOUR_OF_DAY, hourOfDay)
                dateFrom.set(Calendar.MINUTE, minute)
                dateFrom.set(Calendar.SECOND, 0)
                tvFromTime.text = DateUtils.formatDateTime(this.context, dateFrom.timeInMillis, DateUtils.FORMAT_SHOW_TIME)
            },
                    dateFrom.get(Calendar.HOUR_OF_DAY),
                    dateFrom.get(Calendar.MINUTE), true)
                    .show()
            R.id.llToTime -> TimePickerDialog(this.context, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                dateTo.set(Calendar.HOUR_OF_DAY, hourOfDay)
                dateTo.set(Calendar.MINUTE, minute)
                dateFrom.set(Calendar.SECOND, 0)
                tvToTime.text = DateUtils.formatDateTime(this.context, dateTo.timeInMillis, DateUtils.FORMAT_SHOW_TIME)
            },
                    dateTo.get(Calendar.HOUR_OF_DAY),
                    dateTo.get(Calendar.MINUTE), true)
                    .show()
        // R.id.btnApply -> checkFilter()
        }
    }


    private var dateFromListener: DatePickerDialog.OnDateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
        dateFrom.set(Calendar.YEAR, year)
        dateFrom.set(Calendar.MONTH, monthOfYear)
        dateFrom.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        tvFromDate.text = DateUtils.formatDateTime(this.context, dateFrom.timeInMillis, DateUtils.FORMAT_SHOW_DATE)

    }


    private var dateToListener: DatePickerDialog.OnDateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
        dateTo.set(Calendar.YEAR, year)
        dateTo.set(Calendar.MONTH, monthOfYear)
        dateTo.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        tvToDate.text = DateUtils.formatDateTime(this.context, dateTo.timeInMillis, DateUtils.FORMAT_SHOW_DATE)
    }

    override fun initToolbar() {
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).apply {
            setSupportActionBar(toolbarFilter as Toolbar)
            title = "Filter"
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
        }
    }

    override fun onDestroyView() {
        presenter.onDetach()
        super.onDestroyView()
    }
}