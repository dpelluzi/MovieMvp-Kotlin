package dpelluzi.moviedb.search

import dpelluzi.moviedb.model.Movie

interface SearchContract {

    interface Presenter {

        fun onViewCreated()

        fun onSearchTextChanged(searchText: String)

        fun onViewDestroyed()

        fun onMovieItemClicked(movie: Movie)
    }

    interface View {

        fun setupViews()

        fun showError()

        fun clearList()

        fun addMovies(movies: List<Movie>)

        fun showLoading()

        fun dismissLoading()

        fun showMovieDetail(movie: Movie)
    }
}