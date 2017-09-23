package dpelluzi.moviedb.detail

interface DetailContract {

    interface Presenter {

        fun onViewCreated()

    }

    interface View {

        fun setMovieTitle(title: String?)

        fun setOverview(overview: String?)

        fun setRating(voteAverage: Float?)

        fun setPoster(posterPath: String?)

    }
}