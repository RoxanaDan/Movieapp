package com.example.movieapp.ui.actors

import com.example.movieapp.network.executeAndDeliver
import com.example.movieapp.util.Constants
import retrofit2.Retrofit

class ActorRemoteDataSource (retrofit: Retrofit) {
    private val apiService: ActorsAPIService = retrofit.create(ActorsAPIService::class.java)

    private var actorMapper = ActorMapper()

    fun getActors(): List<Actor> {
        return apiService.getActors(Constants.API_KEY, Constants.LANGUAGE)
            .executeAndDeliver()
            .actors
            .map {actorMapper.map(it) }
    }
}