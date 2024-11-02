package com.empasset.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.empasset.model.Asset;

@Repository
public interface AssetRepo extends JpaRepository<Asset, Integer>{

}
