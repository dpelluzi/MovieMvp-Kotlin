package com.dpelluzi.moviedb.detail.presenter

import dpelluzi.moviedb.detail.DetailContract
import dpelluzi.moviedb.model.Movie
import dpelluzi.moviedb.repository.MovieRepository

class MovieDetailPresenter(val view: DetailContract.View, val movie: Movie?) : DetailContract.Presenter {

    override fun onViewCreated() {
        view.setMovieTitle(movie?.title)
        view.setOverview(movie?.overview)
        view.setRating(movie?.voteAverage)
        view.setPoster(MovieRepository.getImageUrl(movie?.posterPath))
    }
}