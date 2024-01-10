package com.example.makhzan;
import com.example.makhzan.Model.LandLord;
import com.example.makhzan.Model.Storage;
import com.example.makhzan.Model.User;
import com.example.makhzan.Repository.LandLordRepository;
import com.example.makhzan.Repository.StorageRepository;
import com.example.makhzan.Repository.UserRepository;
import org.antlr.v4.runtime.misc.LogManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StorageRepositoryTest {

    @Autowired
    StorageRepository storageRepository;
    @Autowired
    LandLordRepository landLordRepository;
    @Autowired
    UserRepository userRepository;
    LandLord landlord;

    Storage storage1, storage2, storage3;

    List<Storage> storages;

    Storage storage;

    @BeforeEach
    void setUp() {
        User user = new User();  // Initialize User object
        user.setUsername("username");  // Set username
        user.setPhonenumber("1234567890");  // Set phone number
        user.setName("Test User");  // Set name
        user.setEmail("test@example.com");  // Set email
        user.setRole("ADMIN");  // Set role
        user.setPassword("password");  // Set password

        userRepository.save(user);  // Save User to the database

        landlord = new LandLord(null, "status1", true, "license1", user, null);
        landLordRepository.save(landlord);  // Save LandLord to the database

        storage1 = new Storage(null, "storage1", "SMALL", 100.0, "address1", "city1", "PENDING", true, 0, 0.0, null, landlord, null, null);
        storage2 = new Storage(null, "storage2", "MEDIUM", 200.0, "address2", "city2", "ACTIVE", false, 1, 5.0, null, landlord, null, null);
        storage3 = new Storage(null, "storage3", "LARGE", 300.0, "address3", "city3", "PENDING", true, 2, 4.5, null, landlord, null, null);
    }


    @Test
    public void findStorageByNameTest() {
        storageRepository.save(storage1);

        List<Storage> storages = storageRepository.findStorageByName(storage1.getName());
        Assertions.assertThat(storages.stream().anyMatch(s -> s.getName().equals(storage1.getName())));
    }

    @Test
    public void findStorageById() {
        storageRepository.save(storage1);
        storage = storageRepository.findStorageById(storage1.getId());
        Assertions.assertThat(storage).isEqualTo(storage1);
    }

    @Test
    public void findStorageBySize() {
        storageRepository.save(storage1);
        storages = storageRepository.findStorageBySize(storage1.getSize());
        Assertions.assertThat(storages.get(0).getSize()).isEqualTo(storage1.getSize());
    }


//    @Test
//    public void findStorageByLandlordNameTesting() {
//        storageRepository.save(storage1);
//        storageRepository.save(storage2);
//        storageRepository.save(storage3);
//        storages = storageRepository.findStorageByLandlordName(landlord.getUser().getUsername());
//        Assertions.assertThat(storages.get(0).getLandlord().getUser().getUsername()).isEqualTo(landlord.getUser().getUsername());
//    }

    @Test
    public void findStorageByAddress() {
        storageRepository.save(storage1);
        storages = storageRepository.findStorageByAddress(storage1.getAddress());
        Assertions.assertThat(storages.get(0).getAddress()).isEqualTo(storage1.getAddress());
    }

    @Test
    public void findStorageByCity() {
        storageRepository.save(storage1);
        storages = storageRepository.findStorageByCity(storage1.getCity());
        Assertions.assertThat(storages.get(0).getCity()).isEqualTo(storage1.getCity());
    }

}
