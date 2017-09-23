package com.dpelluzi.moviedb.search.presenter

import dpelluzi.moviedb.model.Movie
import dpelluzi.moviedb.model.MovieResponse
import dpelluzi.moviedb.repository.MovieRepository
import dpelluzi.moviedb.search.SearchContract
import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class SearchMoviePresenter(val view: SearchContract.View) : SearchContract.Presenter {

    private val MIN_SEARCH_STRING = 3
    
    private val compositeDisposable = CompositeDisposable()
    
    override fun onViewCreated() = view.setupViews()

    override fun onViewDestroyed() = compositeDisposable.clear()
    
    override fun onSearchTextChanged(searchText: String) {
        if (searchText.length >= MIN_SEARCH_STRING) {
            view.showLoading()
            searchMovie(searchText)
        } else {
            view.dismissLoading()
            view.clearList()
        }
    }

    private fun searchMovie(searchText: String) = MovieRepository.searchMovie(searchText, RepositoryObserver())

    override fun onMovieItemClicked(movie: Movie) = view.showMovieDetail(movie)

    private fun handleSuccess(response: MovieResponse) {
        view.dismissLoading();
        view.clearList();
        view.addMovies(response.movies);
    }

    private fun handleError() {
        view.dismissLoading();
        view.clearList();
        view.showError();
    }

    private inner class RepositoryObserver : SingleObserver<MovieResponse> {
        
        override fun onSubscribe(disposable: Disposable) {
            compositeDisposable.add(disposable)
        }

        override fun onSuccess(movieResponse: MovieResponse) {
            handleSuccess(movieResponse)
        }

        override fun onError(e: Throwable) {
            handleError()
        }

    }
}