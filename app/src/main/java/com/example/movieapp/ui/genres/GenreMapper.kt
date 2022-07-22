package com.example.movieapp.ui.genres

class GenreMapper {

    fun map(genreResponse: GenreResponse) : Genre{
        return Genre(
            id = genreResponse.id,
            name = genreResponse.name,
            isSelected = false
        )
    }
}