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
    @RequestMapping(value = "/test_group", method = RequestMethod.GET)
    public String getActivitiesByToken(@RequestHeader(name="Authorization") String token) {

        String username = jwtService.getUsernameFromToken(token);

        List<HouseDeviceEntity> listHouseDevice = houseDeviceService.findAllByUserName(username);

        AnalysisHabit analysisHabit = new AnalysisHabit(listHouseDevice);

        List<DeviceActivityEntity> listResponseEntity
                = deviceActivityService.findAllByUserName(username);

        analysisHabit.createGroupTimeActivityArray(listResponseEntity);

        //createGroupTimeActivityArray(token);

        //First Employee
//        JSONObject employeeDetails = new JSONObject();
//        employeeDetails.put("firstName", "Linh Tran");
//        employeeDetails.put("lastName", "Gupta");
//        employeeDetails.put("website", "howtodoinjava.com");
//
//        JSONObject employeeObject = new JSONObject();
//        employeeObject.put("employee", employeeDetails);
//
//        //Second Employee
//        JSONObject employeeDetails2 = new JSONObject();
//        employeeDetails2.put("firstName", "Brian");
//        employeeDetails2.put("lastName", "Schultz");
//        employeeDetails2.put("website", "example.com");
//
//        JSONObject employeeObject2 = new JSONObject();
//        employeeObject2.put("employee", employeeDetails2);
//
//        //Add employees to list
//        JSONArray employeeList = new JSONArray();
//        employeeList.add(employeeObject);
//        employeeList.add(employeeObject2);
//
//        System.out.println(employeeList.toJSONString());

        return "OK" ;
    }

    public void createGroupDeviceActivityArray(List<DeviceActivityEntity> listGroupDeviceActivity){

        int columns = 10;
        int rows = listGroupDeviceActivity.size();

        DeviceActivityEntity groupTimeActivityArray[][] = new DeviceActivityEntity[columns][rows];

    }

//    public void createGroupTimeActivityArray(String token){
//
//        DeviceActivityEntity groupTimeActivityArray[][] = new DeviceActivityEntity[10][10];
//
//        int indexRow = 0;
//        int indexColumn = 0;
//
//        String username = jwtService.getUsernameFromToken(token);
//
//        List<DeviceActivityEntity> listResponseEntity
//                = deviceActivityService.findAllByUserName(username);
//
//
//        for(int i=0; i<listResponseEntity.size()-1;i++){
//
//            if(i==0){
//                groupTimeActivityArray[0][0] = listResponseEntity.get(0);
//                indexRow++;
//            }
//
//            String stringTimer1 = listResponseEntity.get(i).getDatetime();
//            String stringTimer2 = listResponseEntity.get(i+1).getDatetime();
//
//            int kq = getMinuteDurations(stringTimer1, stringTimer2);
//
//            if(kq < 15){
//                groupTimeActivityArray[indexColumn][indexRow] = listResponseEntity.get(i+1);
//                indexRow++;
//            } else {
//                indexColumn++;
//                indexRow = 0;
//                groupTimeActivityArray[indexColumn][indexRow] = listResponseEntity.get(i+1);
//                indexRow++;
//            }
//
//
//        }
//
//        for(int i=0; i < 10 ; i++)
//        {
//            List<DeviceActivityEntity> listGroupDeviceActivity = null;
//
//            for(int j=0; j < 10 ; j++){
//
//                if(groupTimeActivityArray[i][j] != null) {
//                    System.out.print("  " + groupTimeActivityArray[i][j].getId());
//                    listGroupDeviceActivity.add(groupTimeActivityArray[i][j]);
//                }
//
//            }
//
//            System.out.println();
//
//        }
//
//    }
//
//    public int getMinuteDurations(String firstDate, String secondDate){
//
//        int result = 0;
//
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-yy hh:mm:ss");
//
//        try {
//            Date dateTime1 = dateFormat.parse(firstDate);
//            Date dateTime2 = dateFormat.parse(secondDate);
//
//            long diffInMillies = Math.abs(dateTime2.getTime() - dateTime1.getTime());
//            long minutesDurations = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
//
//            result = (int) minutesDurations;
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        return result;
//    }


}
