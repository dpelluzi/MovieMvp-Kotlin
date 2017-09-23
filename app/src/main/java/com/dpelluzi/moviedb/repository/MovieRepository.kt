package dpelluzi.moviedb.repository

import com.dpelluzi.moviedb.BuildConfig
import dpelluzi.moviedb.model.MovieResponse
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object MovieRepository {

    private val URL_MOVIE_API = "https://api.themoviedb.org"
    private val MOVIE_DB_API_VER = 3
    private val BASE_URL_IMAGE = "http://image.tmdb.org/t/p/"
    private val POSTER_IMAGE_SIZE = "w500"

    private val movieDbApi: MovieDbApi

    init {
        val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(URL_MOVIE_API)
                .build()

        movieDbApi = retrofit.create(MovieDbApi::class.java)
    }

    fun getNowPlayingMovies(page: Int, observer: SingleObserver<MovieResponse>) {
        movieDbApi.getNowPlayingMovies(MOVIE_DB_API_VER, BuildConfig.MOVIE_DB_API_KEY, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)
    }

    fun searchMovie(query: String, observer: SingleObserver<MovieResponse>) {
        movieDbApi.searchMovie(MOVIE_DB_API_VER, BuildConfig.MOVIE_DB_API_KEY, query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)
    }

    fun getImageUrl(imagePath: String?) =  BASE_URL_IMAGE + POSTER_IMAGE_SIZE + imagePath

}