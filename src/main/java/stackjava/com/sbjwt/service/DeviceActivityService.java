package stackjava.com.sbjwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stackjava.com.sbjwt.entities.DeviceActivityEntity;
import stackjava.com.sbjwt.entities.HouseDeviceEntity;
import stackjava.com.sbjwt.entities.UserEntity;
import stackjava.com.sbjwt.repo.DeviceActivityRepo;
import stackjava.com.sbjwt.repo.HouseDeviceRepo;

import java.util.List;

@Service
public class DeviceActivityService {

    @Autowired
    DeviceActivityRepo deviceActivityRepo;

    public List<DeviceActivityEntity> findAll() {
        return deviceActivityRepo.findAll();
    }

    public DeviceActivityEntity findById(int id) {
        return deviceActivityRepo.findById(id).get();
    }

    public List<DeviceActivityEntity> findAllByUserName(String userName) {
        return deviceActivityRepo.getDeviceActivityByUserName(userName);
    }

    public boolean add(DeviceActivityEntity deviceActivityEntity) {
        deviceActivityRepo.save(deviceActivityEntity);
        return true;
    }

    public boolean saveActivity(int userId, boolean powerOn, int powerValue, String lightColor) {
        deviceActivityRepo.saveDeviceActivity(userId, powerOn, powerValue, lightColor);
        return true;
    }

}
