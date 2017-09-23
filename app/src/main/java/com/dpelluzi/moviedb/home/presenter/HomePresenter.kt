package dpelluzi.moviedb.home.presenter

import dpelluzi.moviedb.home.HomeContract
import dpelluzi.moviedb.model.Movie
import dpelluzi.moviedb.model.MovieResponse
import dpelluzi.moviedb.repository.MovieRepository
import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class HomePresenter(val view: HomeContract.View) : HomeContract.Presenter {

    private var nextPage = 1
    private var totalPages: Int = 0
    private val compositeDisposable = CompositeDisposable()

    override fun onViewCreated() {
        view.setupViews()
        view.showProgressBar()

        getMovies()
    }

    override fun onViewDestroyed() = compositeDisposable.clear()

    private fun getMovies() = MovieRepository.getNowPlayingMovies(nextPage, RepositoryObserver())

    override fun onMovieItemClicked(movie: Movie) = view.showMovieDetail(movie)

    override fun onSearchClicked() = view.showSearchScreen()

    override fun loadMoreData() {
        if (nextPage < totalPages) {
            getMovies()
        }
    }

    private fun handleError() {
        view.dismissProgressBar();
        view.showError();
    }

    private fun handleSuccess(movieResponse: MovieResponse) {
        nextPage = movieResponse.page + 1;
        totalPages = movieResponse.totalPages;
        view.dismissProgressBar();
        view.showList();
        view.addMovies(movieResponse.movies);
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