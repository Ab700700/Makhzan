package com.example.makhzan.Repository;

import com.example.makhzan.Model.LandLord;
import com.example.makhzan.Model.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
@Repository
public interface StorageRepository extends JpaRepository<Storage,Integer> {

    Storage findStorageById(Integer id);

    @Query("select s from Storage s where s.landlord.user.username=?1")
    List<Storage> findStorageByLandlordName(String name);

    @Query("select s from Storage s order by  s.rentedTimes DESC")
    List<Storage> findAllStorageByRentTimesDesc();
    @Query("select s from Storage s order by  s.rentedTimes ASC")
    List<Storage> findAllStorageByRentTimes();
    List<Storage> findStorageByCity(String city);

    List<Storage> findStorageByAddress(String address);

    List<Storage> findStorageBySize(String size);
    @Query("select s from Storage s order by  s.price DESC")
    List<Storage> findAllStorageByHigh();


    @Query("select s from Storage s where s.name=?1")
    List<Storage> findStorageByName(String name);

    @Query("select s from Storage s where s.name=?1 and s.status='ACTIVE'")
    List<Storage> findStorageByNameandActive(String name);

    @Query("select s from Storage s where s.landlord.user.username=?1 and s.status='ACTIVE'")
    List<Storage> findStorageByLandlordNameandActive(String name);

    @Query("select s from Storage s where s.available=true")
    List<Storage> findStorageAvailable();


    @Query("select s from Storage s where s.size='SMALL'")
    List<Storage> findStorageSmall();

    @Query("select s from Storage s where s.size='MEDIUM'")
    List<Storage> findStorageMedium();

    @Query("select s from Storage s where s.size='LARGE'")
    List<Storage> findStorageLarge();

    @Query("select s from Storage s where s.size='OUTSIDE'")
    List<Storage> findStorageOutside();

    @Query("select s from Storage s where s.size='COMPANY'")
    List<Storage> findStorageCompany();

    @Query("select s from Storage s where s.landlord.id=?1 and s.id=?2")
    Storage findStorageByLandlordIdAndStorageId(Integer landlordId,Integer storageId);

    List<Storage> findStoragesByStatus(String status);

}
