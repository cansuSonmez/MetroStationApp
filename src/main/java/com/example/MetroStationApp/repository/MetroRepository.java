package com.example.MetroStationApp.repository;

import com.example.MetroStationApp.model.Metro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetroRepository extends JpaRepository<Metro,Long> {
}
