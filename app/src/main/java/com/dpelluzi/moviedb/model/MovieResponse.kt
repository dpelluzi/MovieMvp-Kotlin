package dpelluzi.moviedb.model

import com.google.gson.annotations.SerializedName

class MovieResponse {

    @SerializedName("page")
    var page: Int = 0

    @SerializedName("total_pages")
    var totalPages: Int = 0

    @SerializedName("total_results")
    var totalResult: Int = 0

    @SerializedName("results")
    lateinit var movies: List<Movie>
}