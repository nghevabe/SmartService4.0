package stackjava.com.sbjwt.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stackjava.com.sbjwt.entities.DeviceActivityEntity;
import stackjava.com.sbjwt.entities.HouseDeviceEntity;
import stackjava.com.sbjwt.process.AnalysisHabit;
import stackjava.com.sbjwt.service.DeviceActivityService;
import stackjava.com.sbjwt.service.HouseDeviceService;
import stackjava.com.sbjwt.service.JwtService;

@RestController
@RequestMapping("/rest")
public class HouseDeviceRestController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private HouseDeviceService houseDeviceService;

    @Autowired
    private DeviceActivityService deviceActivityService;

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

    @CrossOrigin
    @RequestMapping(value = "/analysis_activity_data", method = RequestMethod.GET)
    public ResponseEntity<List<DeviceActivityEntity>> getActivitiesByToken(@RequestHeader(name="Authorization") String token) {

        String username = jwtService.getUsernameFromToken(token);

        List<HouseDeviceEntity> listHouseDevice = houseDeviceService.findAllByUserName(username);

        AnalysisHabit analysisHabit = new AnalysisHabit(listHouseDevice);

        List<DeviceActivityEntity> listResponseEntity
                = deviceActivityService.findAllByUserName(username);

        //analysisHabit.createGroupTimeActivityArray(listResponseEntity);

        analysisHabit.readData();
        int x = analysisHabit.timeClassify("2020-09-01 20:07:05");

        return new ResponseEntity<List<DeviceActivityEntity>>(AnalysisHabit.listActivity, HttpStatus.OK);
    }



}
