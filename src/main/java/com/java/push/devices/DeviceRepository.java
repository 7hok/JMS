package com.java.push.devices;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device,String> {

	List<Device> findByAppIdAndStatus(String appId, Character status);

	List<Device> findByAppIdAndPlatformIdAndStatus(String appId, String platFormId, Character status);

    @Query(nativeQuery = true, value = "SELECT * FROM ps_device_client WHERE device_id=:deviceId AND  app_id=:appId ")
	Device findCByDeviceIdAndAppId(String deviceId, Character appId);
    
    @Query(nativeQuery = true, value = "SELECT * FROM ps_device_client WHERE device_id=:deviceId AND  app_id=:appId LIMIT 1")
    Device findByDeviceIdAndAppId(String deviceId, String appId);
        //SELECT id ,token , status , platform ,app_id ,device_id FROM ps_device_client WHERE device_id=:deviceId AND  app_id=:appId 
    // public List<Device> findByUserIdAndStatus(String userId,Character Status);
    // /**
    // * Using native query instead of joining 
    // */
    // @Query(nativeQuery = true, value = "SELECT * FROM device WHERE user_id = :userId AND appId = :appId AND status = :active" )
	// public List<Device> findByUserIdAndAppIdAndStatus(String userId, String appId, Character active);

}