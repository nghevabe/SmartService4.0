package stackjava.com.sbjwt.controller;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import stackjava.com.sbjwt.entities.DeviceActivityEntity;
import stackjava.com.sbjwt.entities.HouseDeviceEntity;
import stackjava.com.sbjwt.entities.UserEntity;
import stackjava.com.sbjwt.exception.AppException;
import stackjava.com.sbjwt.repo.UserRepo;
import stackjava.com.sbjwt.service.DeviceActivityService;
import stackjava.com.sbjwt.service.HouseDeviceService;
import stackjava.com.sbjwt.service.JwtService;

@RestController
@RequestMapping("/rest")
public class DeviceActivityRestController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private DeviceActivityService deviceActivityService;

    /* ---------------- GET ALL DEVICE ------------------------ */
//    @CrossOrigin
//    @RequestMapping(value = "/test_activity", method = RequestMethod.GET)
//    public ResponseEntity<List<DeviceActivityEntity>> getAllActivity() {
//        return new ResponseEntity<List<DeviceActivityEntity>>(deviceActivityService.findAll(), HttpStatus.OK);
//    }

    @CrossOrigin
    @RequestMapping(value = "/test_activity", method = RequestMethod.GET)
    public ResponseEntity<List<DeviceActivityEntity>> getActivitiesByToken(@RequestHeader(name="Authorization") String token) {

        String username = jwtService.getUsernameFromToken(token);

        return new ResponseEntity<List<DeviceActivityEntity>>(deviceActivityService.findAllByUserName(username), HttpStatus.OK);
    }

}
