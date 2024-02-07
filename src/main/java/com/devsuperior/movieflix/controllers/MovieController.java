package com.devsuperior.movieflix.controllers;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.services.MovieService;
import com.devsuperior.movieflix.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/movies")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @Autowired
    private ReviewService reviewService;

    @PreAuthorize("hasAnyRole('ROLE_MEMBER','ROLE_VISITOR')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<MovieDetailsDTO> findById(@PathVariable Long id){
        MovieDetailsDTO movieDetail = movieService.findById(id);
        return ResponseEntity.ok(movieDetail);
    }

    @PreAuthorize("hasAnyRole('ROLE_MEMBER','ROLE_VISITOR')")
    @GetMapping
    public ResponseEntity<Page<MovieCardDTO>> findAll(@RequestParam(name = "genreId", defaultValue = "0") String genreId,
                                                      Pageable pageable){
        Page<MovieCardDTO> movieCard = movieService.findAll(genreId,pageable);
        return ResponseEntity.ok(movieCard);
    }

    @PreAuthorize("hasAnyRole('ROLE_MEMBER','ROLE_VISITOR')")
    @GetMapping(value = "/{id}/reviews")
    public ResponseEntity<List<ReviewDTO>> findByMovie(@PathVariable Long id){
        List<ReviewDTO> reviews = reviewService.findByMovie(id);
        return ResponseEntity.ok(reviews);
    }

}
