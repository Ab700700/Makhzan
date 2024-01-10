package com.example.makhzan.Controller;

import com.example.makhzan.DTO.ReviewDTO;
import com.example.makhzan.Model.User;
import com.example.makhzan.Service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/makhzan/review")
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/get")
    public ResponseEntity getReviews(){
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.getReviews());
    }
    @PostMapping("/add")
    public ResponseEntity addReview(@RequestBody @Valid ReviewDTO reviewDTO, @AuthenticationPrincipal User user){
        reviewService.addReview(reviewDTO,user.getId());
        return ResponseEntity.status(HttpStatus.OK).body("Review added");
    }
    @PutMapping("/update/{id}")
    public ResponseEntity updateReview(@PathVariable Integer id, @RequestBody @Valid ReviewDTO reviewDTO,@AuthenticationPrincipal User user){
        reviewService.updateReview(id,reviewDTO,user.getId());
        return ResponseEntity.status(HttpStatus.OK).body("Review updated");
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteReivew(@PathVariable Integer id , @AuthenticationPrincipal User user){
        reviewService.deleteReview(id,user.getId());
        return ResponseEntity.status(HttpStatus.OK).body("Review deleted");
    }
}
