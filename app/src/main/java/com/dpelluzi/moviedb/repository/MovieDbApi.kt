package dpelluzi.moviedb.repository

import dpelluzi.moviedb.model.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDbApi {

    @GET("/{version}/movie/now_playing")
    fun getNowPlayingMovies(@Path("version") version: Int, @Query("api_key") apiKey: String,
                                     @Query("page") page: Int): Single<MovieResponse>

    @GET("/{version}/search/movie")
    fun searchMovie(@Path("version") version: Int, @Query("api_key") apiKey: String,
                             @Query("query") query: String): Single<MovieResponse>
}