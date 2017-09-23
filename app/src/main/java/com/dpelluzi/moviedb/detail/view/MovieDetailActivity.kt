package com.dpelluzi.moviedb.detail.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.dpelluzi.moviedb.R
import com.dpelluzi.moviedb.detail.presenter.MovieDetailPresenter
import com.squareup.picasso.Picasso
import dpelluzi.moviedb.detail.DetailContract
import dpelluzi.moviedb.model.Movie
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : AppCompatActivity(), DetailContract.View {

    companion object {

        private val EXTRA_MOVIE = "extra.movie"

        fun createIntent(context: Context, movie: Movie): Intent {
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra(EXTRA_MOVIE, movie)
            return intent
        }
    }

    private lateinit var presenter: DetailContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val movie = intent.getParcelableExtra<Movie>(EXTRA_MOVIE)
        presenter = MovieDetailPresenter(this, movie)
        presenter.onViewCreated()
    }

    override fun setMovieTitle(title: String?) = setTitle(title)

    override fun setOverview(overview: String?) {
        text_overview.text = overview
    }

    override fun setRating(voteAverage: Float?) {
        text_rating.text = getString(R.string.text_rating, voteAverage)
    }

    override fun setPoster(posterPath: String?) {
        Picasso.with(this).load(posterPath).resizeDimen(R.dimen.image_width, R.dimen.image_height)
                .centerCrop().into(img_poster)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}
