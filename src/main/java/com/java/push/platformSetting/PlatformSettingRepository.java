package com.java.push.platformSetting;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlatformSettingRepository extends JpaRepository<PlatformSetting,String>{

	public List<PlatformSetting> findByApplicationIdAndStatus(String appId, Character status);

	public PlatformSetting findByApplicationIdAndPlatformId(String appId, String platformId);

    
}