package com.example.makhzan;


import com.example.makhzan.Model.LandLord;
import com.example.makhzan.Model.Media;
import com.example.makhzan.Model.Storage;
import com.example.makhzan.Repository.LandLordRepository;
import com.example.makhzan.Repository.StorageRepository;
import com.example.makhzan.Service.StorageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class StorageServiceTest {

    @InjectMocks
    StorageService storageService;
    @Mock
    StorageRepository storageRepository;
    @Mock
    LandLordRepository landLordRepository;

    LandLord landlord;
    Storage storage1, storage2, storage3;
    List<Storage> storages;

    @BeforeEach
    void setUp() {
        landlord = new LandLord();  // Initialize LandLord object
        // Set properties of LandLord object as needed

        storage1 = new Storage();  // Initialize Storage object
        // Set properties of Storage object as needed
        storage1.setLandlord(landlord);

        storage2 = new Storage();  // Initialize Storage object
        // Set properties of Storage object as needed
        storage2.setLandlord(landlord);

        storage3 = new Storage();  // Initialize Storage object
        // Set properties of Storage object as needed
        storage3.setLandlord(landlord);

        storages = new ArrayList<>();
        storages.add(storage1);
        storages.add(storage2);
        storages.add(storage3);
    }

    @Test
    public void getStoragesTest() {
        when(storageRepository.findAll()).thenReturn(storages);
        List<Storage> storageList = storageService.getStorages();
        Assertions.assertEquals(3, storageList.size());
        verify(storageRepository, times(1)).findAll();
    }

    @Test
    public void addStorageTest() {
        Storage storage = new Storage();
        LandLord landlord = new LandLord();
        landlord.setStatus("ACTIVE");
        when(landLordRepository.findLandLordById(anyInt())).thenReturn(landlord);
        storageService.addStorage(storage, 1);
        verify(storageRepository, times(2)).save(storage);
    }
    @Test
    public void updateStorageTest() {
        when(storageRepository.findStorageById(storage1.getId())).thenReturn(storage1);
        when(landLordRepository.findLandLordById(landlord.getId())).thenReturn(landlord);
        storageService.updateStorage(storage1.getId(), landlord.getId(), storage2);
        verify(storageRepository, times(1)).findStorageById(storage1.getId());
        verify(landLordRepository, times(1)).findLandLordById(landlord.getId());
        verify(storageRepository, times(1)).save(storage2);
    }

    @Test
    public void deleteStorageTest() {
        when(storageRepository.findStorageById(storage1.getId())).thenReturn(storage1);
        when(landLordRepository.findLandLordById(landlord.getId())).thenReturn(landlord);
        storageService.deleteStorage(storage1.getId(), landlord.getId());
        verify(storageRepository, times(1)).findStorageById(storage1.getId());
        verify(landLordRepository, times(1)).findLandLordById(landlord.getId());
        verify(storageRepository, times(1)).delete(storage1);
    }
    @Test
    public void getMediaTest() {
        Storage storage = new Storage();
        storage.setMedias(new HashSet<>());
        when(storageRepository.findStorageById(anyInt())).thenReturn(storage);
        assertNotNull(storageService.getMedia(1));
    }


}
