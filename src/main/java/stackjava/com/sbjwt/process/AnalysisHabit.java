package stackjava.com.sbjwt.process;

import org.springframework.beans.factory.annotation.Autowired;
import stackjava.com.sbjwt.entities.DeviceActivityEntity;
import stackjava.com.sbjwt.entities.HouseDeviceEntity;
import stackjava.com.sbjwt.service.DeviceActivityService;
import stackjava.com.sbjwt.service.HouseDeviceService;
import stackjava.com.sbjwt.service.JwtService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class AnalysisHabit {

    private String token;
    private List<HouseDeviceEntity> listHouseDevice;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private HouseDeviceService houseDeviceService;

    public AnalysisHabit(List<HouseDeviceEntity> listHouseDevice) {
        this.listHouseDevice = listHouseDevice;
    }

    public void createGroupDeviceActivityArray(List<DeviceActivityEntity> listGroupDeviceActivity){

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

        System.out.println("");
        System.out.println("***");
        System.out.println("");


        for(int i=0; i < columns ; i++)
        {


            for(int j=0; j < rows ; j++){

                if(groupDeviceActivityArray[i][j] != null) {
                    System.out.print(" [] " + groupDeviceActivityArray[i][j].getId());

                }

            }

            System.out.println();

        }

        System.out.println("");
        System.out.println("***");
        System.out.println("");


    }

    public void createGroupTimeActivityArray(List<DeviceActivityEntity> listResponseEntity){

        DeviceActivityEntity groupTimeActivityArray[][] = new DeviceActivityEntity[10][10];

        int indexRow = 0;
        int indexColumn = 0;

        int check=0;

        int i_=0;
        while(i_<listResponseEntity.size()){

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

                        System.out.println("Column: " + indexColumn + " Rows: " + indexRow);
                        System.out.println("ID: " + listResponseEntity.get(j).getId());
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

        for(int i=0; i < 10 ; i++)
        {
            List<DeviceActivityEntity> listGroupDeviceActivity = new ArrayList<>();

            for(int j=0; j < 10 ; j++){

                if(groupTimeActivityArray[i][j] != null) {
                    System.out.print(" | " + groupTimeActivityArray[i][j].getId());
                    listGroupDeviceActivity.add(groupTimeActivityArray[i][j]);
                }

            }

            System.out.println();
            createGroupDeviceActivityArray(listGroupDeviceActivity);

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
