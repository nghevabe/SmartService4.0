package stackjava.com.sbjwt.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import stackjava.com.sbjwt.entities.HouseDeviceEntity;
import stackjava.com.sbjwt.entities.UserEntity;
import stackjava.com.sbjwt.exception.AppException;
import stackjava.com.sbjwt.repo.UserRepo;
import stackjava.com.sbjwt.service.HouseDeviceService;
import stackjava.com.sbjwt.service.JwtService;

@RestController
@RequestMapping("/rest")
public class HouseDeviceRestController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private HouseDeviceService houseDeviceService;

    /* ---------------- GET ALL DEVICE ------------------------ */
//    @CrossOrigin
//    @RequestMapping(value = "/house_devices", method = RequestMethod.GET)
//    public ResponseEntity<List<HouseDeviceEntity>> getAllUser() {
//        return new ResponseEntity<List<HouseDeviceEntity>>(houseDeviceService.findAll(), HttpStatus.OK);
//    }


    @CrossOrigin
    @RequestMapping(value = "/house_devices", method = RequestMethod.GET)
    public ResponseEntity<List<HouseDeviceEntity>> getAllUser(@RequestHeader(name="Authorization") String token) {

        String username = jwtService.getUsernameFromToken(token);

        return new ResponseEntity<List<HouseDeviceEntity>>(houseDeviceService.findAllByUserName(username), HttpStatus.OK);
    }

}
