package com.trainee.newsapi.screens.detail.view

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.format.DateUtils
import android.view.*
import com.bumptech.glide.Glide
import com.trainee.newsapi.*
import com.trainee.newsapi.App.Companion.appComponent
import com.trainee.newsapi.data.local.entity.News
import com.trainee.newsapi.screens.base.view.BaseFragment
import com.trainee.newsapi.screens.detail.presenter.DetailPresenterImpl
import kotlinx.android.synthetic.main.fragment_news_description.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class DetailFragment : BaseFragment(), DetailView {

    @Inject
    lateinit var presenter: DetailPresenterImpl<DetailView>

    companion object {
        fun newInstance(newsId: String) =
                DetailFragment().apply {
                    arguments = Bundle(1).apply {
                        putString(NEWS_ID, newsId)
                    }
                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_news_description, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            initFabFavourites()
        }
        initBtnWeb()
        presenter.onAttach(this)
        getDetail(arguments.getString(NEWS_ID))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu_news_description, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.shareNews -> shareNews()
            else -> return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getDetail(id: String) {
        presenter.loadNewsById(id)
    }

    override fun showDetail(news: News) {
        tvTitle.text = news.title
        tvBody.text = news.description
        tvAuthor.text = news.author
        tvDate.text = news.publishedAt
        tvDate.text = news.publishedAt.let {
            DateUtils.formatDateTime(context,
                    SimpleDateFormat(DATE_FORMAT_SERVER, Locale.getDefault()).parse(it).time,
                    DateUtils.FORMAT_SHOW_DATE + DateUtils.FORMAT_SHOW_TIME)
        }
        if (news.urlToImage.isNotEmpty()) {
            Glide.with(this)
                    .load(news.urlToImage)
                    .into(ivDescription)

        }
        when (news.isFavourite) {
            true -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                fabFavourite.setImageDrawable(resources.getDrawable(R.drawable.ic_star_white,activity.theme))
            }
            false -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                fabFavourite.setImageDrawable(resources.getDrawable(R.drawable.ic_star_border_white,activity.theme))
            }
        }
    }

    override fun showFullNews(url: String) {
        CustomTabsIntent.Builder().build().launchUrl(context, Uri.parse(url))
    }

    override fun changeFavourites(id: String) {
        presenter.changeFavourites(id)
    }

    override fun showFavouriteState(isFavourite: Boolean) {

    }

    override fun shareNews() {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = TYPE_TEXT
        shareIntent.putExtra(Intent.EXTRA_TEXT, presenter.news.url)
        startActivity(Intent.createChooser(shareIntent, SHARE_TEXT))

    }

    override fun initToolbar() {
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).apply {
            setSupportActionBar(toolbarNewsDescription as Toolbar)
            title = "Detail news"
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
        }
    }

    override fun initFabFavourites() {
        fabFavourite.setOnClickListener {
            changeFavourites(arguments.getString(NEWS_ID))

            if (presenter.news.isFavourite){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    fabFavourite.setImageDrawable(resources.getDrawable(R.drawable.ic_star_border_white,activity.theme))
                }
                presenter.news.isFavourite = false
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    fabFavourite.setImageDrawable(resources.getDrawable(R.drawable.ic_star_white,activity.theme))
                }
                presenter.news.isFavourite = true
            }
        }
    }

    override fun initBtnWeb() {
        btnWeb.setOnClickListener {
            presenter.showFullNews(arguments.getString(NEWS_ID))
        }
    }

    override fun onDestroyView() {
        presenter.onDetach()
        super.onDestroyView()
    }
}