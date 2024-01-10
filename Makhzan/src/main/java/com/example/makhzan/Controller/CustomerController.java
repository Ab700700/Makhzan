package com.example.makhzan.Controller;

import com.example.makhzan.DTO.CustomerDTO;
import com.example.makhzan.Model.User;
import com.example.makhzan.Service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/makhzan/customer")
public class CustomerController {
    private final CustomerService customerService;
    @GetMapping("/get")
    public ResponseEntity getAllCustomers() {
        return ResponseEntity.status(200).body(customerService.getAllCustomers());
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid CustomerDTO customerDTO) {
        customerService.register(customerDTO);
        return ResponseEntity.status(200).body("Customer Register");
    }



    @PutMapping("/update")
    public ResponseEntity updateCustomer(@RequestBody @Valid CustomerDTO customerDTO, @AuthenticationPrincipal User user){
        customerService.updateCustomer(customerDTO,user.getId());
        return ResponseEntity.status(200).body("Customer updated");

    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteCustomer(@AuthenticationPrincipal User user){
        customerService.deleteCustomer(user.getId());
        return ResponseEntity.status(200).body("Customer deleted");
    }
    @GetMapping("/user")
    public ResponseEntity customerDetails(@AuthenticationPrincipal User user){
        return ResponseEntity.status(HttpStatus.OK).body(customerService.customerDetails(user.getId()));
    }

    @GetMapping("/orders")
    public ResponseEntity customerOrders(@AuthenticationPrincipal User user){
        return ResponseEntity.status(HttpStatus.OK).body(customerService.customerOrders(user.getId()));
    }
    @GetMapping("/accepted-orders")
    public ResponseEntity customerAcceptedOrders(@AuthenticationPrincipal User user){
        return ResponseEntity.status(HttpStatus.OK).body(customerService.customerAcceptedOrders(user.getId()));
    }

    @GetMapping("/reject-orders")
    public ResponseEntity customerRejectedOrders(@AuthenticationPrincipal User user){
        return ResponseEntity.status(HttpStatus.OK).body(customerService.customerRejectedOrders(user.getId()));
    }
    @GetMapping("/pending-orders")
    public ResponseEntity customerPendingOrders(@AuthenticationPrincipal User user){
        return ResponseEntity.status(HttpStatus.OK).body(customerService.customerPendingOrders(user.getId()));
    }
    @GetMapping("/mostrented")
    public ResponseEntity mostRented(){
        return ResponseEntity.status(HttpStatus.OK).body(customerService.mostRented());
    }
    @GetMapping("/lessrented")
    public ResponseEntity lessRented(){
        return ResponseEntity.status(HttpStatus.OK).body(customerService.lessRented());
    }


}
