package com.example.makhzan.Repository;

import com.example.makhzan.Model.LandLord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface LandLordRepository extends JpaRepository<LandLord,Integer> {

    LandLord findLandLordById(Integer id);


}
