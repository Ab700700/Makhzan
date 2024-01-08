package com.example.makhzan.Repository;

import com.example.makhzan.Model.Customer;
import com.example.makhzan.Model.Review;
import com.example.makhzan.Model.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Integer> {
    Review findReviewById(Integer id);
    Review findReviewByCustomerAndStorage(Customer customer, Storage storage);
}
