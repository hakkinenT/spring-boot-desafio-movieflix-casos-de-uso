package com.devsuperior.movieflix.services;

import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository repository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private AuthService authService;

    @Transactional
    public ReviewDTO insert(ReviewDTO reviewDTO){
        Review review = new Review();
        review.setText(reviewDTO.getText());

        Movie movie = movieRepository.getReferenceById(reviewDTO.getMovieId());
        review.setMovie(movie);

        User user = authService.authenticated();
        review.setUser(user);

        review = repository.save(review);

        return new ReviewDTO(review, review.getMovie(), review.getUser());

    }

    @Transactional(readOnly = true)
    public List<ReviewDTO> findByMovie(Long movieId){
        List<Review> reviews = repository.searchByMovie(movieId);
        return reviews.stream().map(r -> new ReviewDTO(r, r.getMovie(), r.getUser())).toList();
    }
}
