package com.example.makhzan.Controller;

import com.example.makhzan.DTO.LandLordDTO;
import com.example.makhzan.Model.User;
import com.example.makhzan.Service.LandLordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/makhzan/landlord")
public class LandLordContoller {
    private final LandLordService landLordService;

    @GetMapping("/get")
    public ResponseEntity getAllLandlord() {
        return ResponseEntity.status(200).body(landLordService.getAllLandlords());
    }

    //All
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid LandLordDTO landlordDTO) {
        landLordService.register(landlordDTO);
        return ResponseEntity.status(200).body("Landlord Register");
    }

    @PutMapping("/update")
    public ResponseEntity updateLandlord(@RequestBody @Valid LandLordDTO landlordDTO, @AuthenticationPrincipal User user){
        landLordService.updateLandlord(landlordDTO,user.getId());
        return ResponseEntity.status(200).body("Landlord updated");

    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteLandlord(@AuthenticationPrincipal User user){
        landLordService.deleteLandlord(user.getId());
        return ResponseEntity.status(200).body("Landlord deleted");
    }
    @PutMapping("/accept/{orderid}")
    public ResponseEntity acceptOrder(@PathVariable Integer orderid,@AuthenticationPrincipal User user){
        landLordService.acceptOrder(orderid,user.getId());
        return ResponseEntity.status(HttpStatus.OK).body("Order accepted");
    }
    @GetMapping("/storage-orders/{storageid}")
    public ResponseEntity storageOrders(@PathVariable Integer storageid){
        return ResponseEntity.status(HttpStatus.OK).body(landLordService.storageOrders(storageid));
    }
    @GetMapping("/get-landlord")
    public ResponseEntity getLandlordDetails(@AuthenticationPrincipal User user){
        return ResponseEntity.status(HttpStatus.OK).body(landLordService.getLandlordDetails(user.getId()));
    }
    @GetMapping("/get-storages")
    public ResponseEntity getStorages(@AuthenticationPrincipal User user){
        return ResponseEntity.status(HttpStatus.OK).body(landLordService.storages(user.getId()));
    }

}
