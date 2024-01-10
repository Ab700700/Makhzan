package com.example.makhzan.Controller;

import com.example.makhzan.Model.Storage;
import com.example.makhzan.Model.User;
import com.example.makhzan.Service.StorageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/makhzan/storage")
public class StorageController {
    private final StorageService storageService;

    @GetMapping("/get")
    public ResponseEntity getStorages(){
        return ResponseEntity.status(200).body(storageService.getStorages());
    }

    @PostMapping("/add")
    public ResponseEntity addStorage(@RequestBody @Valid Storage storage, @AuthenticationPrincipal User user){
        storageService.addStorage(storage, user.getId() );
        return ResponseEntity.status(200).body("Storage added");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateStorage(@RequestBody @Valid Storage storage, @PathVariable Integer id,@AuthenticationPrincipal User user){
        storageService.updateStorage(id, user.getId(), storage);
        return ResponseEntity.status(200).body("Storage updated");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteStorage(@PathVariable Integer id,@AuthenticationPrincipal User user){
        storageService.deleteStorage(id, user.getId());
        return ResponseEntity.status(200).body("Storage deleted");
    }

    @GetMapping("/media/{storageid}")
    public ResponseEntity getMedia(@PathVariable Integer storageid){
        return ResponseEntity.status(HttpStatus.OK).body(storageService.getMedia(storageid));
    }

    @GetMapping("/get-rent-times")
    public ResponseEntity rentTimes(){
        return ResponseEntity.status(200).body(storageService.findsStoragesByRentTimes());
    }
    @GetMapping("/get-city/{city}")
    public ResponseEntity findsStoragesByCity(@PathVariable String city){
        return ResponseEntity.status(200).body(storageService.findsStoragesByCity(city));
    }

    @GetMapping("/get-address/{address}")
    public ResponseEntity findsStoragesByAddress(@PathVariable String address){
        return ResponseEntity.status(200).body(storageService.findsStoragesByAddress(address));
    }


    @GetMapping("/get-size/{size}")
    public ResponseEntity findsStoragesBySize(@PathVariable String size){
        return ResponseEntity.status(200).body(storageService.findsStoragesBySize(size));
    }

    @GetMapping("/get-high")
    public ResponseEntity findAllStorageByHigh(){
        return ResponseEntity.status(200).body(storageService.findAllStorageByHigh());
    }

    @GetMapping("/get-name/{name}")
    public ResponseEntity findStorageByLandlordName(@PathVariable String name){
        return ResponseEntity.status(200).body(storageService.findStorageByLandlordName(name));
    }
    @GetMapping("/get-storagename/{name}")
    public ResponseEntity findStorageByName(@PathVariable String name){
        return ResponseEntity.status(200).body(storageService.findStorageByName(name));
    }

    @GetMapping("/get-available")
    public ResponseEntity findStorageAvailable(){
        return ResponseEntity.status(200).body(storageService.findStorageAvailable());
    }

    @GetMapping("/get-small")
    public ResponseEntity findStorageSmall(){
        return ResponseEntity.status(200).body(storageService.findStorageSmall());
    }

    @GetMapping("/get-medium")
    public ResponseEntity findStorageMedium(){
        return ResponseEntity.status(200).body(storageService.findStorageMedium());
    }

    @GetMapping("/get-large")
    public ResponseEntity findStorageLarge(){
        return ResponseEntity.status(200).body(storageService.findStorageLarge());
    }

    @GetMapping("/get-outside")
    public ResponseEntity findStorageOutside(){
        return ResponseEntity.status(200).body(storageService.findStorageOutside());
    }

    @GetMapping("/get-company")
    public ResponseEntity findStorageCompany(){
        return ResponseEntity.status(200).body(storageService.findStorageCompany());
    }

    @PutMapping("/verify/{landlordId}/{storageId}")
    public ResponseEntity verifyStorage(@PathVariable Integer landlordId,@PathVariable Integer storageId){
        storageService.verifyStorage(landlordId, storageId);
        return ResponseEntity.status(200).body("Storage Verified");
    }
    @GetMapping("/get-reviews/{storageid}")
    public ResponseEntity getReviews(@PathVariable Integer storageid){
        return ResponseEntity.status(HttpStatus.OK).body(storageService.getStorageReviews(storageid));
    }

    @GetMapping("/search/{localDate1}/{localDate2}")
    public ResponseEntity searchBetween(@PathVariable LocalDate localDate1, @PathVariable LocalDate localDate2){
        return ResponseEntity.status(HttpStatus.OK).body(storageService.search(localDate1,localDate2));
    }
    @GetMapping("/search-storage-name/{lcoalDate1}/{localDate2}/{storagename}")
    public ResponseEntity searchStorageName(@PathVariable LocalDate localDate1 ,@PathVariable LocalDate localDate2,@PathVariable String storagename){
        return ResponseEntity.status(HttpStatus.OK).body(storageService.searchStorageName(localDate1,localDate2,storagename));
    }
    @GetMapping("/search-storage-landlord-name/{lcoalDate1}/{localDate2}/{landlord}")
    public ResponseEntity searchStorageLandlordName(@PathVariable LocalDate localDate1 ,@PathVariable LocalDate localDate2,@PathVariable String landlord){
        return ResponseEntity.status(HttpStatus.OK).body(storageService.searchStorageLandlordName(localDate1,localDate2,landlord));
    }
}
