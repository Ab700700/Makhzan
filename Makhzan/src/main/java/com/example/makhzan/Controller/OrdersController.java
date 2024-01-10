package com.example.makhzan.Controller;

import com.example.makhzan.DTO.OrdersDTO;
import com.example.makhzan.Model.User;
import com.example.makhzan.Service.OrdersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/makhzan/order")
public class OrdersController {
    private final OrdersService ordersService;

    @GetMapping("/get")
    public ResponseEntity getOrders(){
        return ResponseEntity.status(HttpStatus.OK).body(ordersService.getOrders());
    }
    @PostMapping("/add")
    public ResponseEntity createOrder(@RequestBody @Valid OrdersDTO ordersDTO, @AuthenticationPrincipal User user){
        ordersService.createOrder(ordersDTO,user.getId());
        return ResponseEntity.status(HttpStatus.OK).body("Order created");
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteOrder(@PathVariable Integer id, @AuthenticationPrincipal User user){
        ordersService.deleteOrder(id,user.getId());
        return ResponseEntity.status(HttpStatus.OK).body("Order removed");
    }

    @GetMapping("/get-accepted")
    public ResponseEntity findOrdersAccepted(){
        return ResponseEntity.status(200).body(ordersService.findOrdersAccepted());
    }

    @GetMapping("/get-pending")
    public ResponseEntity findOrdersPending(){
        return ResponseEntity.status(200).body(ordersService.findOrdersPending());
    }

    @GetMapping("/get-rejected")
    public ResponseEntity findOrdersRejected(){
        return ResponseEntity.status(200).body(ordersService.findOrdersRejected());
    }



    @GetMapping("/order-start-before/{date}")
    public ResponseEntity findOrdersStartingBeforeDate(@PathVariable LocalDate date){
        return ResponseEntity.status(200).body(ordersService.findOrdersStartingBeforeDate(date));
    }

    @GetMapping("/order-start-after/{date}")
    public ResponseEntity findOrdersStartingAfterDate(@PathVariable LocalDate date){
        return ResponseEntity.status(200).body(ordersService.findOrdersStartingAfterDate(date));
    }
}
