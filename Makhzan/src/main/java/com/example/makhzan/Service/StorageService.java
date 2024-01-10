package com.example.makhzan.Service;

import com.example.makhzan.Api.ApiException;
import com.example.makhzan.Model.*;
import com.example.makhzan.Repository.LandLordRepository;
import com.example.makhzan.Repository.OrdersRepository;
import com.example.makhzan.Repository.StorageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class StorageService {
    private final StorageRepository storageRepository;
    private final LandLordRepository landLordRepository;
    private final OrdersRepository ordersRepository;
    public List<Storage> getStorages(){
        return storageRepository.findAll();
    }

    public void addStorage(Storage storage, Integer userid){
        LandLord landlord = landLordRepository.findLandLordById(userid);
        if(landlord == null) throw  new ApiException("User not found");
        if(!landlord.getStatus().equals("ACTIVE")) throw new ApiException("User not allowed to add storage");
        storage.setStatus("PENDING");
        storage.setAvailable(false);
        storage.setLandlord(landlord);
        storageRepository.save(storage);
    }


    public void updateStorage(Integer id, Integer userid, Storage storage){
        Storage oldStorage = storageRepository.findStorageById(id);
        if(oldStorage == null) throw  new ApiException("Storage not found");
        LandLord landlord = landLordRepository.findLandLordById(userid);
        if (landlord == null) throw new ApiException("User not found");
        if(!oldStorage.getLandlord().equals(landlord)) throw new ApiException("User is not allowed to update");
        storage.setId(id);
        storageRepository.save(storage);
    }

    public void deleteStorage(Integer id , Integer userid){
        Storage storage = storageRepository.findStorageById(id);
        if(storage == null) throw  new ApiException("Storage not found");
        LandLord landlord = landLordRepository.findLandLordById(userid);
        if(landlord == null) throw  new ApiException("User not found");
        if(!storage.getLandlord().equals(landlord)) throw new ApiException("User not allowed to delete");
        storageRepository.delete(storage);
    }

    public Set<Media> getMedia(Integer storageid){
        Storage storage = storageRepository.findStorageById(storageid);
        if(storage == null) throw  new ApiException("Storage not found");
        return storage.getMedias();
    }

    public List<Storage> findsStoragesByCity(String city){
        return storageRepository.findStorageByCity(city);
    }

    public List<Storage> findsStoragesByAddress(String address){
        return storageRepository.findStorageByAddress(address);
    }

    public List<Storage> findsStoragesByRentTimes(){
        return storageRepository.findAllStorageByRentTimesDesc();
    }

    public List<Storage> findsStoragesBySize(String size){
        return storageRepository.findStorageBySize(size);
    }

    public List<Storage> findAllStorageByHigh(){
        return storageRepository.findAllStorageByHigh();
    }

    public List<Storage> findStorageByLandlordName(String name){
        return storageRepository.findStorageByLandlordName(name);
    }

    public List<Storage> findStorageByName(String name){
        return storageRepository.findStorageByName(name);
    }


    public List<Storage> findStorageAvailable(){
        return storageRepository.findStorageAvailable();
    }

    public List<Storage> findStorageSmall(){
        return storageRepository.findStorageSmall();
    }

    public List<Storage> findStorageMedium(){
        return storageRepository.findStorageMedium();
    }

    public List<Storage> findStorageLarge(){
        return storageRepository.findStorageLarge();
    }

    public List<Storage> findStorageOutside(){
        return storageRepository.findStorageOutside();
    }

    public List<Storage> findStorageCompany(){
        return storageRepository.findStorageCompany();
    }

    public void verifyStorage(Integer landlordId,Integer storageId){
        Storage storage=storageRepository.findStorageByLandlordIdAndStorageId(landlordId, storageId);
        if(storage==null){
            throw new ApiException("Storage not found");
        }
        storage.setStatus("ACTIVE");
        storageRepository.save(storage);
    }

    public Set<Review> getStorageReviews(Integer storageid){
        Storage storage = storageRepository.findStorageById(storageid);
        if(storage == null) throw new ApiException("Storage not found");
        return storage.getReviews();
    }

    public List<Storage> search(LocalDate localDate1, LocalDate localDate2){
        List<Storage> activeStorages = storageRepository.findStoragesByStatus("ACTIVE");
        List<Storage> avaliableStorages = new ArrayList<>();
        for(Storage s : activeStorages){
            List<Orders> acceptedOrders =ordersRepository.getOrdersBetweenAndAccptedtwo(localDate1,localDate2,s);
            if(acceptedOrders.isEmpty()) activeStorages.add(s);
        }
        return avaliableStorages;
    }

    public List<Storage> searchStorageName(LocalDate localDate1, LocalDate localDate2, String storagename){
        List<Storage> storages = storageRepository.findStorageByNameandActive(storagename);
        List<Storage> avaliableStorages = new ArrayList<>();
        for(Storage s : storages){
            List<Orders> acceptedOrders =ordersRepository.getOrdersBetweenAndAccptedtwo(localDate1,localDate2,s);
            if(acceptedOrders.isEmpty()) storages.add(s);
        }
        return avaliableStorages;
    }

    public List<Storage> searchStorageLandlordName(LocalDate localDate1, LocalDate localDate2, String Landlordname){
        List<Storage> storages = storageRepository.findStorageByLandlordNameandActive(Landlordname);
        List<Storage> avaliableStorages = new ArrayList<>();
        for(Storage s : storages){
            List<Orders> acceptedOrders =ordersRepository.getOrdersBetweenAndAccptedtwo(localDate1,localDate2,s);
            if(acceptedOrders.isEmpty()) storages.add(s);
        }
        return avaliableStorages;
    }

    @Scheduled(fixedRate = 60000*60*24)
    public void checkAv(){
        List<Storage> storages = storageRepository.findAll();
        for(Storage  s : storages){
                Orders orders = ordersRepository.findOrdersByStartDate(LocalDate.now(),s);
                Orders orders1 = ordersRepository.findOrdersByEndDate(LocalDate.now(),s);
                if(orders!=null) s.setAvailable(false);
                if(orders1!=null) s.setAvailable(true);
                storageRepository.save(s);

        }

    }
}
