package com.example.moviews.data.remote

import android.net.Uri
import com.example.moviews.BuildConfig
import com.example.moviews.data.OnLoadDataCallback
import com.example.moviews.data.SearchDataSource
import com.example.moviews.data.model.Genre
import com.example.moviews.data.model.Movie
import com.example.moviews.utils.Constant

class SearchRemoteDataSource : SearchDataSource.Remote {

    private fun buildUrl(paths: List<String>) = Uri.Builder()
        .scheme(Constant.BASE_HTTPS)
        .authority(Constant.BASE_AUTHORITY)
        .appendPath(Constant.BASE_VERSION)
        .apply {
            paths.forEach {
                this.appendPath(it)
            }
        }
        .appendQueryParameter(Constant.BASE_KEY, BuildConfig.API_KEY)
        .toString()

    private val urlGenres =
        buildUrl(
            listOf(
                Constant.BASE_GENRES,
                Constant.BASE_MOVIE,
                Constant.BASE_LIST
            )
        )
    private val urlTopSearch =
        buildUrl(
            listOf(
                Constant.BASE_MOVIE,
                Constant.BASE_TOP_RATE
            )
        )
    private val urlSearchMovie =
        buildUrl(
            listOf(
                Constant.BASE_SEARCH,
                Constant.BASE_MOVIE
            )
        )

    override fun getGenres(callback: OnLoadDataCallback<MutableList<Genre>>) {
        GetJsonFromUrl(Genre.GENRES, callback).execute(urlGenres)
    }

    override fun getTopSearches(callback: OnLoadDataCallback<MutableList<Movie>>) {
        GetJsonFromUrl(Movie.MOVIE_RESULTS, callback).execute(urlTopSearch)
    }

    override fun searchMovie(name: String, callback: OnLoadDataCallback<MutableList<Movie>>) {
        GetJsonFromUrl(
            Movie.MOVIE_RESULTS,
            callback
        ).execute(urlSearchMovie + Constant.BASE_QUERY + name)
    }

    companion object {
        private var instance: SearchRemoteDataSource? = null
        fun getInstance(): SearchRemoteDataSource =
            instance ?: SearchRemoteDataSource().also { instance = it }
    }
}