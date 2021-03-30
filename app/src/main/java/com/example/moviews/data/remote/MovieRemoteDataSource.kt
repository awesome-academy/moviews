package com.example.moviews.data.remote

import android.net.Uri
import com.example.moviews.BuildConfig
import com.example.moviews.data.MovieDataSource
import com.example.moviews.data.OnLoadDataCallback
import com.example.moviews.data.model.Movie
import com.example.moviews.utils.Constant

class MovieRemoteDataSource : MovieDataSource.Remote {

    private fun builUrl(paths: List<String>): String {
        val builder = Uri.Builder()
        builder.scheme(Constant.BASE_HTTPS)
            .authority(Constant.BASE_AUTHORITY)
            .appendPath(Constant.BASE_VERSION)
            .apply {
                paths.forEach {
                    this.appendPath(it)
                }
            }
            .appendQueryParameter(Constant.BASE_KEY, BuildConfig.API_KEY)
        return builder.toString()
    }

    private var urlTrending = builUrl(
        listOf(
            Constant.BASE_TRENDING,
            Constant.BASE_MOVIE,
            Constant.BASE_TRENDING_TIME
        )
    )
    private var urlUpcoming = builUrl(
        listOf(
            Constant.BASE_MOVIE,
            Constant.BASE_UPCOMING
        )
    )
    private var urlPopular = builUrl(
        listOf(
            Constant.BASE_MOVIE,
            Constant.BASE_POPULAR
        )
    )
    private var urlNowPlaying = builUrl(
        listOf(
            Constant.BASE_MOVIE,
            Constant.BASE_NOW_PLAYING
        )
    )

    override fun getTrendingMovie(callback: OnLoadDataCallback<MutableList<Movie>>) {
        GetJsonFromUrl(Movie.MOVIE_RESULTS, callback).execute(urlTrending)
    }

    override fun getUpcomingMovie(callback: OnLoadDataCallback<MutableList<Movie>>) {
        GetJsonFromUrl(Movie.MOVIE_RESULTS, callback).execute(urlUpcoming)
    }

    override fun getNowPlayingMovie(callback: OnLoadDataCallback<MutableList<Movie>>) {
        GetJsonFromUrl(Movie.MOVIE_RESULTS, callback).execute(urlNowPlaying)
    }

    override fun getPopularMovie(callback: OnLoadDataCallback<MutableList<Movie>>) {
        GetJsonFromUrl(Movie.MOVIE_RESULTS, callback).execute(urlPopular)
    }

    companion object {
        private var instance: MovieRemoteDataSource? = null
        fun getInstance(): MovieRemoteDataSource = instance ?: MovieRemoteDataSource()
            .also { instance = it }
    }
}
