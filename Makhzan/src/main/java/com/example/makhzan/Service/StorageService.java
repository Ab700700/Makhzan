package com.example.makhzan.Service;

import com.example.makhzan.Api.ApiException;
import com.example.makhzan.Model.LandLord;
import com.example.makhzan.Model.Media;
import com.example.makhzan.Model.Review;
import com.example.makhzan.Model.Storage;
import com.example.makhzan.Repository.LandLordRepository;
import com.example.makhzan.Repository.StorageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class StorageService {
    private final StorageRepository storageRepository;
    private final LandLordRepository landLordRepository;
    public List<Storage> getStorages(){
        return storageRepository.findAll();
    }

    public void addStorage(Storage storage, Integer userid){
        LandLord landlord = landLordRepository.findLandLordById(userid);
        if(landlord == null) throw  new ApiException("User not found");
        if(!landlord.getStatus().equals("ACTIVE")) throw new ApiException("User not allowed to add storage");
        storage.setStatus("PENDING");
        storage.setAvailable(false);
        storageRepository.save(storage);
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
}
