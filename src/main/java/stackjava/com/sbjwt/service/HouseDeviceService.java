package stackjava.com.sbjwt.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import stackjava.com.sbjwt.entities.HouseDeviceEntity;
import stackjava.com.sbjwt.entities.UserEntity;
import stackjava.com.sbjwt.exception.AppException;
import stackjava.com.sbjwt.repo.HouseDeviceRepo;
import stackjava.com.sbjwt.repo.UserRepo;

@Service
public class HouseDeviceService {

    @Autowired
    HouseDeviceRepo houseDeviceRepo;

    public List<HouseDeviceEntity> findAll() {
        return houseDeviceRepo.findAll();
    }

    public HouseDeviceEntity findById(int id) {
        return houseDeviceRepo.findById(id).get();
    }

    public List<HouseDeviceEntity> findAllByUserName(String username) {
        return houseDeviceRepo.getHouseDeviceByUserName(username);
    }

}
