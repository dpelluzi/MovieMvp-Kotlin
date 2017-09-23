package com.dpelluzi.moviedb.search.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import com.dpelluzi.moviedb.R
import com.dpelluzi.moviedb.detail.view.MovieDetailActivity
import com.dpelluzi.moviedb.extension.afterTextChanged
import com.dpelluzi.moviedb.extension.displayToast
import com.dpelluzi.moviedb.extension.hide
import com.dpelluzi.moviedb.extension.show
import com.dpelluzi.moviedb.home.adapter.MovieListAdapter
import com.dpelluzi.moviedb.home.adapter.OnItemClickListener
import com.dpelluzi.moviedb.search.presenter.SearchMoviePresenter
import dpelluzi.moviedb.model.Movie
import dpelluzi.moviedb.search.SearchContract
import kotlinx.android.synthetic.main.activity_search_movie.*

class SearchMovieActivity : AppCompatActivity(), SearchContract.View {

    companion object {
        fun createIntent(context: Context) = Intent(context, SearchMovieActivity::class.java)
    }

    private lateinit var presenter: SearchContract.Presenter

    private val adapter: MovieListAdapter by lazy { MovieListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_movie)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        presenter = SearchMoviePresenter(this)
        presenter.onViewCreated()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onViewDestroyed()
    }

    override fun setupViews() {
        list.layoutManager = LinearLayoutManager(this)

        adapter.onItemClickListener = object : OnItemClickListener {

            override fun onItemClicked(movie: Movie) {
                presenter.onMovieItemClicked(movie)
            }
        }

        list.adapter = adapter

        input_search.afterTextChanged { text -> presenter.onSearchTextChanged(text) }
    }

    override fun showMovieDetail(movie: Movie) = startActivity(MovieDetailActivity.createIntent(this, movie))

    override fun showError() = displayToast(R.string.error_connection)

    override fun clearList() = adapter.clear()

    override fun addMovies(movies: List<Movie>) = adapter.addItems(movies)

    override fun showLoading() = progressBar.show()

    override fun dismissLoading() = progressBar.hide()

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }

}
