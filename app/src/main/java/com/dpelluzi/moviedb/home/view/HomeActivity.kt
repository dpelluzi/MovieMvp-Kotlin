package com.dpelluzi.moviedb.home.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import com.dpelluzi.moviedb.R
import com.dpelluzi.moviedb.detail.view.MovieDetailActivity
import com.dpelluzi.moviedb.extension.displayToast
import com.dpelluzi.moviedb.extension.hide
import com.dpelluzi.moviedb.extension.show
import com.dpelluzi.moviedb.home.adapter.EndlessRecyclerViewScrollListener
import com.dpelluzi.moviedb.home.adapter.MovieListAdapter
import com.dpelluzi.moviedb.home.adapter.OnItemClickListener
import com.dpelluzi.moviedb.search.view.SearchMovieActivity
import dpelluzi.moviedb.home.HomeContract
import dpelluzi.moviedb.home.presenter.HomePresenter
import dpelluzi.moviedb.model.Movie
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(), HomeContract.View {

    private lateinit var presenter: HomeContract.Presenter

    private val adapter: MovieListAdapter by lazy { MovieListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        presenter = HomePresenter(this)
        presenter.onViewCreated()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onViewDestroyed()
    }

    override fun setupViews() {
        val layoutManager = LinearLayoutManager(this)
        list.layoutManager = layoutManager

        adapter.onItemClickListener = object : OnItemClickListener {

            override fun onItemClicked(movie: Movie) {
                presenter.onMovieItemClicked(movie)
            }
        }

        list.addOnScrollListener(object : EndlessRecyclerViewScrollListener(layoutManager) {

            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                presenter.loadMoreData()
            }
        })

        list.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_activity_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_search) {
            presenter.onSearchClicked()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun showProgressBar() = progressBar.show()

    override fun showError() {
        displayToast(R.string.error_connection)
    }

    override fun dismissProgressBar() = progressBar.hide()

    override fun addMovies(movies: List<Movie>) = adapter.addItems(movies)

    override fun showList() = list.show()

    override fun showMovieDetail(movie: Movie) = startActivity(MovieDetailActivity.createIntent(this, movie))

    override fun showSearchScreen() = startActivity(SearchMovieActivity.createIntent(this))


}
