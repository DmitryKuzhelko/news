package com.trainee.newsapi.screens.news.view

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.view.*
import com.trainee.newsapi.App.Companion.appComponent
import com.trainee.newsapi.FROM_DATE
import com.trainee.newsapi.R
import com.trainee.newsapi.SORT_BY
import com.trainee.newsapi.TO_DATE
import com.trainee.newsapi.data.local.entity.News
import com.trainee.newsapi.screens.NewsAdapter
import com.trainee.newsapi.screens.base.view.BaseFragment
import com.trainee.newsapi.screens.news.presenter.NewsPresenterImpl
import kotlinx.android.synthetic.main.fragment_news_list.*
import javax.inject.Inject

class NewsFragment : BaseFragment(), NewsView {

    @Inject
    lateinit var presenter: NewsPresenterImpl<NewsView>

    @Inject
    lateinit var newsAdapter: NewsAdapter

    companion object {
        fun newInstance(sortBy: String = "", from: String = "", to: String = "") =
                NewsFragment().apply {
                    arguments = Bundle(3).apply {
                        putString(SORT_BY, sortBy)
                        putString(FROM_DATE, from)
                        putString(TO_DATE, to)
                    }
                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_news_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initRecyclerView()
        initFabFavourites()
        setAdapterClickListener()
        presenter.onAttach(this)
        searchNewsByParam(arguments.getString(SORT_BY), arguments.getString(FROM_DATE), arguments.getString(TO_DATE))
    }

    override fun loadNews() {
        presenter.loadNews()
    }

    override fun searchNewsByQuery(query: String) {
        presenter.loadNewsByQuery(query)
    }

    override fun searchNewsByParam(sortBy: String, from: String, to: String) {
        presenter.loadNewsByParam(sortBy, from, to)
    }

    override fun showNews(news: List<News>) {
        newsAdapter.updateAdapter(news)
    }

    override fun openDetailScreen(id: String) {
        presenter.openDetailScreen(id)
    }

    override fun openFavouritesScreen() {
        presenter.openFavouritesScreen()
    }

    override fun openFilterScreen() {
        presenter.openFilterScreen()
    }

    override fun initToolbar() {
        this.setHasOptionsMenu(true)
        (activity as AppCompatActivity).apply {
            setSupportActionBar(toolbarNewsList as Toolbar)
            title = "News"
        }
    }

    override fun initRecyclerView() {
        (newsList as RecyclerView).apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = newsAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy > 0) {
                        fabFavourite.hide()
                    } else {
                        fabFavourite.show()
                    }
                }
            })
        }
    }

    override fun initFabFavourites() {
        fabFavourite.setOnClickListener {
            openFavouritesScreen()
        }
    }

    override fun showSnackbar(isSearch: Boolean) {
        if (isSearch) {
            Snackbar.make(fabFavourite, resources.getString(R.string.snackbar_error_connection), Snackbar.LENGTH_LONG)
                    .show()
        } else {
            Snackbar.make(fabFavourite, resources.getString(R.string.snackbar_error_offline), Snackbar.LENGTH_INDEFINITE)
                    .setAction(resources.getString(R.string.snackbar_action_yes), { presenter.loadNewsByParam() })
                    .show()
        }
    }

    override fun setAdapterClickListener() {
        newsAdapter.setClickListener(object : NewsAdapter.ClickListener {
            override fun onClick(news: News, position: Int) {
                openDetailScreen(news.id)
            }
        })
    }

    override fun setRV(isRV: Boolean) {
        if (isRV) {
            tvNoNews.visibility = View.GONE
        } else {
            tvNoNews.visibility = View.VISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu_news_list, menu)

        val menuItem = menu.findItem(R.id.findNews)
        val searchView = menuItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchNewsByQuery(query)
                return true
            }

            override fun onQueryTextChange(s: String): Boolean {
                return true
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.filterNews -> presenter.openFilterScreen()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun onDestroyView() {
        presenter.onDetach()
        super.onDestroyView()
    }
}