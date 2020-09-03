package stackjava.com.sbjwt.process;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import stackjava.com.sbjwt.entities.DeviceActivityEntity;
import stackjava.com.sbjwt.entities.HouseDeviceEntity;
import stackjava.com.sbjwt.service.DeviceActivityService;
import stackjava.com.sbjwt.service.HouseDeviceService;
import stackjava.com.sbjwt.service.JwtService;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class AnalysisHabit {

    private String token;
    private List<HouseDeviceEntity> listHouseDevice;

    public static ArrayList<DeviceActivityEntity> listActivity = new ArrayList<>();
    // ArrayList<DeviceActivityEntity> listActivity = new ArrayList<>();

    String timeGroup = "";

    String nameDevice = "";
    int countTurnOn = 0;
    int countTurnOff = 0;
    int countTurnRed = 0;
    int countTurnGreen = 0;
    int countTurnBlue = 0;
    int countTurnYellow = 0;
    int countTurnViolet = 0;
    int countTurnAqua = 0;
    int countTurnWhite = 0;
    int power = 0;


    @Autowired
    private JwtService jwtService;

    @Autowired
    private HouseDeviceService houseDeviceService;

    public AnalysisHabit(List<HouseDeviceEntity> listHouseDevice) {
        this.listHouseDevice = listHouseDevice;
    }

    public void createGroupDeviceActivityArray(List<DeviceActivityEntity> listGroupDeviceActivity, JSONObject timeGroupDetails){

        //String username = jwtService.getUsernameFromToken(token);

        //List<HouseDeviceEntity> listHouseDevice = houseDeviceService.findAllByUserName(username);

        int columns = listHouseDevice.size();
        int rows = listGroupDeviceActivity.size();

        int indexRow = 0;
        int indexColumn = 0;

        DeviceActivityEntity groupDeviceActivityArray[][] = new DeviceActivityEntity[columns][rows];

        for(int i=0; i < columns ; i++)
        {
            int idGroup = listHouseDevice.get(i).getId();
            indexRow=0;

            for(int j=0; j < rows ; j++){
                //System.out.println(idGroup+" - "+listGroupDeviceActivity.get(j).getId());
                if(idGroup == listGroupDeviceActivity.get(j).getDeviceid()){
                    groupDeviceActivityArray[indexColumn][indexRow] = listGroupDeviceActivity.get(j);
                    indexRow++;
                }
            }
            indexColumn++;
        }
        processData(groupDeviceActivityArray,timeGroupDetails);
    }

    public void processData(DeviceActivityEntity groupDeviceActivityArray[][], JSONObject timeGroupDetails){

        int columns = groupDeviceActivityArray.length;
        JSONArray deviceActivityList = new JSONArray();

        for(int i=0; i < columns ; i++)
        {

            countTurnOn = 0;
            countTurnOff = 0;
            countTurnRed = 0;
            countTurnGreen = 0;
            countTurnBlue = 0;
            countTurnYellow = 0;
            countTurnViolet = 0;
            countTurnAqua = 0;
            countTurnWhite = 0;

            int ids = -1;

            int rows = groupDeviceActivityArray[i].length;

            for(int j=0; j < rows ; j++){

                if(groupDeviceActivityArray[i][j] != null) {
//                    System.out.print(" [] " + groupDeviceActivityArray[i][j].getId()
//                            + " - " + groupDeviceActivityArray[i][j].getDatetime());

                    // process

                    ids = groupDeviceActivityArray[i][j].getDeviceid();

                    if(groupDeviceActivityArray[i][j].isPoweron()){
                        countTurnOn++;
                    } else {
                        countTurnOff++;
                    }

                    if(groupDeviceActivityArray[i][j].getLightcolor().equals("WHITE"))
                    {
                        countTurnWhite++;
                    }

                    if(groupDeviceActivityArray[i][j].getLightcolor().equals("RED"))
                    {
                        countTurnRed++;
                    }

                    if(groupDeviceActivityArray[i][j].getLightcolor().equals("GREEN"))
                    {
                        countTurnGreen++;
                    }

                    if(groupDeviceActivityArray[i][j].getLightcolor().equals("BLUE"))
                    {
                        countTurnBlue++;
                    }

                    if(groupDeviceActivityArray[i][j].getLightcolor().equals("YELLOW"))
                    {
                        countTurnYellow++;
                    }

                    if(groupDeviceActivityArray[i][j].getLightcolor().equals("VIOLET"))
                    {
                        countTurnViolet++;
                    }

                    if(groupDeviceActivityArray[i][j].getLightcolor().equals("AQUA"))
                    {
                        countTurnAqua++;
                    }

                }

            }

            if(ids>0)
            {

               // System.out.println(ids+" - Count ON: "+countTurnOn+" Count OFF: "+countTurnOff
                //        +" Count Red: "+countTurnRed+" Count White: "+countTurnWhite);

                JSONObject deviceActivityDetail = new JSONObject();
                deviceActivityDetail.put("idDevice",ids);
                deviceActivityDetail.put("countOn",countTurnOn);
                deviceActivityDetail.put("countOff",countTurnOff);
                deviceActivityDetail.put("countTurnRed",countTurnRed);
                deviceActivityDetail.put("countTurnWhite",countTurnWhite);

                deviceActivityList.add(deviceActivityDetail);

                timeGroupDetails.put("device",deviceActivityList);


            }

            //System.out.println();
        }

        //System.out.println(timeGroupDetails.toJSONString());
    }

    public void createGroupTimeActivityArray(List<DeviceActivityEntity> listResponseEntity){

        JSONArray timeGroupList = new JSONArray();

        int rows = listResponseEntity.size();
        int columns = 100;

        DeviceActivityEntity groupTimeActivityArray[][] = new DeviceActivityEntity[columns][rows];

        int indexRow = 0;
        int indexColumn = 0;

        int check=0;

        int i_=0;
        while(i_<listResponseEntity.size()){

            int timeAverage = 0;

            indexRow = 0;

            groupTimeActivityArray[indexColumn][0] = listResponseEntity.get(i_);
            indexRow++;
            //listResponseEntity.remove(i_);

            check=0;
            if(i_<listResponseEntity.size()-1) {
                int j = i_ + 1;
                while (j < listResponseEntity.size()) {

                    String stringTimer1 = listResponseEntity.get(i_).getDatetime();
                    String stringTimer2 = listResponseEntity.get(j).getDatetime();

                    int kq = getMinuteDurations(stringTimer1, stringTimer2);

                    if (kq < 15) {
                        groupTimeActivityArray[indexColumn][indexRow] = listResponseEntity.get(j);

//                        System.out.println("Column: " + indexColumn + " Rows: " + indexRow);
//                        System.out.println("ID: " + listResponseEntity.get(j).getId());
                        listResponseEntity.remove(j);
                        indexRow++;
                        check++;
                    } else {
                        j++;
                    }

                }
            }

            indexColumn++;
            if(check==0){

                i_++;

            } else {
                listResponseEntity.remove(i_);

            }

        }

        for(int i=0; i < columns ; i++)
        {
            List<DeviceActivityEntity> listGroupDeviceActivity = new ArrayList<>();


            for(int j=0; j < rows ; j++){

                if(groupTimeActivityArray[i][j] != null) {
                    //System.out.print(" | " + groupTimeActivityArray[i][j].getId());
                    listGroupDeviceActivity.add(groupTimeActivityArray[i][j]);
                }

            }

            //System.out.println();
            if(listGroupDeviceActivity.size()>10) {
                timeGroup = groupTimeActivityArray[i][0].getDatetime();
                JSONObject timeGroupDetails = new JSONObject();
                timeGroupDetails.put("time",timeGroup);
                createGroupDeviceActivityArray(listGroupDeviceActivity, timeGroupDetails);
                timeGroupList.add(timeGroupDetails);
            }

        }

        try {
            FileWriter writer = new FileWriter("MyData.txt", false);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            bufferedWriter.write(timeGroupList.toJSONString());

            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //System.out.println(timeGroupList.toJSONString());

    }

    public void readData(){



        try {
            FileReader reader = new FileReader("MyData.txt");
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);

            }


            //System.out.println(obj.getString("name")); //John

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    public int timeClassify(String timeInput){

        ArrayList<Integer> listDurations = new ArrayList<>();

        try {
            Object obj = new JSONParser().parse(new FileReader("MyData.txt"));

            JSONArray jsonArray = (JSONArray) obj;

            for(int i=0;i<jsonArray.size();i++){

                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                String time = (String) jsonObject.get("time");

                int timeDurations = getMinuteDurations0(timeInput, time);
                listDurations.add(timeDurations);

            }

            //System.out.println(lastName);

        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Integer minIndex = listDurations.indexOf(Collections.min(listDurations));
        System.out.println("Min durations time: "+minIndex);

        analysisObject(minIndex);

        return 0;
    }

    public void analysisObject(int index){

        //ArrayList<DeviceActivityEntity> listActivity = new ArrayList<>();


        try {
            Object obj = new JSONParser().parse(new FileReader("MyData.txt"));
            JSONArray jsonArray = (JSONArray) obj;

            JSONObject activityObject = (JSONObject) jsonArray.get(index);
            String time = (String) activityObject.get("time");

            JSONArray deviceArray = (JSONArray) activityObject.get("device");

            for(int i=0; i<deviceArray.size();i++){

                DeviceActivityEntity deviceActivityEntity = new DeviceActivityEntity();

                JSONObject deviceObject = (JSONObject) deviceArray.get(i);
                long id = (long) deviceObject.get("idDevice");
                long countOn = (long) deviceObject.get("countOn");
                long countOff = (long) deviceObject.get("countOff");
                long countTurnRed = (long) deviceObject.get("countTurnRed");
                long countTurnWhite = (long) deviceObject.get("countTurnWhite");

                deviceActivityEntity.setId(i);
                deviceActivityEntity.setDatetime(time);
                deviceActivityEntity.setDeviceid((int) id);
                deviceActivityEntity.setLightcolor("WHITE");
                deviceActivityEntity.setPoweron(findPowerStatus(countOn,countOff));
                deviceActivityEntity.setPowervalue(50);

                listActivity.add(deviceActivityEntity);
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }


    }

    public boolean findPowerStatus(long countOn, long countOff){

        //System.out.println("Count On: "+countOn + " - Count Off"+countOff);
        if(countOn > countOff){
            return true;
        } else {
            return false;
        }
        

    }

    public int getMinuteDurations0(String firstDate, String secondDate){

        int result = 0;

        String[] strFirstDateCut = firstDate.split(" ");
        String[] strSecondDateCut = secondDate.split(" ");

        firstDate = strFirstDateCut[1];
        secondDate = strSecondDateCut[1];

        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-yy hh:mm:ss");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");

        try {
            Date dateTime1 = timeFormat.parse(firstDate);
            Date dateTime2 = timeFormat.parse(secondDate);
            
            long diffInMillies = Math.abs(dateTime2.getTime() - dateTime1.getTime());
            long minutesDurations = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);

            result = (int) minutesDurations;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result;
    }

    public int getMinuteDurations(String firstDate, String secondDate){

        int result = 0;

        String[] strFirstDateCut = firstDate.split(" ");
        String[] strSecondDateCut = secondDate.split(" ");

        firstDate = strFirstDateCut[1];
        secondDate = strSecondDateCut[1];

        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-yy hh:mm:ss");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");

        try {
            Date dateTime1 = timeFormat.parse(firstDate);
            Date dateTime2 = timeFormat.parse(secondDate);

            long diffInMillies = Math.abs(dateTime2.getTime() - dateTime1.getTime());
            long minutesDurations = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);

            result = (int) minutesDurations;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result;
    }

}
