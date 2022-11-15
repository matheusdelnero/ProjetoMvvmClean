package com.br.natanfc.filmesflix.framework.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.br.natanfc.filmesflix.framework.api.MovieRestApiTask
import com.br.natanfc.filmesflix.data.MovieRepository
import com.br.natanfc.filmesflix.domain.Movie
import com.br.natanfc.filmesflix.implementations.MovieDataSourceImplementation
import com.br.natanfc.filmesflix.usecase.MovieListUseCase

class MovieListViewModel: ViewModel() {

    companion object {
        const val TAG = "MovieRepository"
    }

    private val movieRestApiTask = MovieRestApiTask()
    private val moviedataSource = MovieDataSourceImplementation(movieRestApiTask)
    private val movieRepository = MovieRepository(moviedataSource)
    private val moviesListUseCase = MovieListUseCase(movieRepository)



    private var _moviesList = MutableLiveData<List<Movie>>()
    val moviesList: LiveData<List<Movie>>
        get() = _moviesList

    fun init() {
        getAllMovies()
    }

    private fun getAllMovies() {
        Thread {
            try {
                _moviesList.postValue(moviesListUseCase.invoke())
            } catch (exception: Exception) {
                Log.d(TAG, exception.message.toString())
            }
        }.start()
    }

}