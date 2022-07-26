package com.example.movieapp.ui.actors

class ActorMapper {

    fun map(actorResponse: ActorResponse) : Actor  {
        return Actor (
            id = actorResponse.id,
            name = actorResponse.name,
            profile_path = actorResponse.profile_path,
            isSelected = false
        )
    }
}