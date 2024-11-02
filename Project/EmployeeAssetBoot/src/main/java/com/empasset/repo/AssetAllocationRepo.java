package com.empasset.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.empasset.model.AssetAllocation;

@Repository
public interface AssetAllocationRepo extends JpaRepository<AssetAllocation, Integer>{

}
