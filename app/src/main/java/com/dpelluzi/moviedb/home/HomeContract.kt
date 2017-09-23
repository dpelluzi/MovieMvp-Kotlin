package dpelluzi.moviedb.home

import dpelluzi.moviedb.model.Movie

interface HomeContract {

    interface Presenter {

        fun onViewCreated()

        fun onViewDestroyed()

        fun onMovieItemClicked(movie: Movie)

        fun onSearchClicked()

        fun loadMoreData()
    }

    interface View {

        fun setupViews()

        fun showProgressBar()

        fun showError()

        fun dismissProgressBar()

        fun addMovies(movies: List<Movie>)

        fun showList()

        fun showMovieDetail(movie: Movie)

        fun showSearchScreen()
    }
}