package com.example.makhzan.Repository;

import com.example.makhzan.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    Customer findCustomerById(Integer id);

    @Query("select c from Customer c order by  c.rentedTimes DESC")
    List<Customer> findCustomerByRentTimesDesc();
    @Query("select c from Customer c order by  c.rentedTimes ASC ")
    List<Customer> findCustomerByRentTimesASC();
}
