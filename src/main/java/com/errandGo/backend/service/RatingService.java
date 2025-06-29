package com.errandGo.backend.service;

import com.errandGo.backend.entities.Rating;
import com.errandGo.backend.repositories.RatingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;

    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    public Rating getRatingById(Long ratingId) {
        return ratingRepository.findById(ratingId)
                .orElseThrow(() -> new IllegalArgumentException("Rating not Found"));
    }

    @Transactional
    public Rating createRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    @Transactional
    public Rating updateRating(Long id, Rating updatingRating) {
        Rating existing = ratingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rating not Found"));
        existing.setRating(updatingRating.getRating());
        existing.setReview(updatingRating.getReview());
        return ratingRepository.save(existing);
    }

    public void deleteRating(Long id) {
        if (!ratingRepository.existsById(id)) {
            throw new IllegalArgumentException("Rating not Found");
        }
        ratingRepository.deleteById(id);
    }

    public List<Rating> getRatingsForErrandBoy(Long errandBoyId) {
        return ratingRepository.findByErrandBoyId(errandBoyId);
    }

    public List<Rating> getRatingsByUserId(Long userId) {
        return ratingRepository.findByUserId(userId);
    }
}
