package com.devsuperior.movieflix.services;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.repositories.GenreRepository;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Transactional(readOnly = true)
    public MovieDetailsDTO findById(Long id){
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Filme não encontrado"));
        return new MovieDetailsDTO(movie, movie.getGenre());
    }

    @Transactional(readOnly = true)
    public Page<MovieCardDTO> findAll(String genreId, Pageable pageable){
        long id = Long.parseLong(genreId);
        Page<Movie> movies = Page.empty();

        if(id == 0){
            movies = movieRepository.searchByGenre(null, pageable);
        }else{
            movies = movieRepository.searchByGenre(id, pageable);
        }
        return movies.map(MovieCardDTO::new);
    }


}
