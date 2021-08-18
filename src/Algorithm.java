import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Algorithm {
    public String algorize(String full){
        String[] dateTime = full.split(" ");
        String date = dateTime[0];
        String time = dateTime[1];

        //Separating time
        String[] Time = time.split(":");
        String hour = Time[0];
        String minute = Time[1];
        String second = Time[2];

        //Separating date
        String[] Date = date.split("/");
        String year = Date[0];
        String month = Date[1];
        String day = Date[2];




        //current time to compare
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String previous = "" + dtf.format(now);

        String[] dateTimeNow = previous.split(" ");
        String dateNow = dateTimeNow[0];
        String timeNow = dateTimeNow[1];

        //Separating time
        String[] TimeNow = timeNow.split(":");
        String hourNow = TimeNow[0];
        String minuteNow = TimeNow[1];
        String secondNow = TimeNow[2];

        //Separating date
        String[] DateNow = dateNow.split("/");
        String yearNow = DateNow[0];
        String monthNow = DateNow[1];
        String dayNow = DateNow[2];

        //converting all strings into ints
        //converting the time from db
        int dbHour = Integer.parseInt(hour);
        int dbMinute = Integer.parseInt(minute);
        int dbSecond = Integer.parseInt(second);

        //converting the date from db
        int dbYear = Integer.parseInt(year);
        int dbMonth = Integer.parseInt(month);
        int dbDay = Integer.parseInt(day);

        //converting the current date
        int nowYear = Integer.parseInt(yearNow);
        int nowMonth = Integer.parseInt(monthNow);
        int nowDay = Integer.parseInt(dayNow);

        //converting the current time
        int nowHour = Integer.parseInt(hourNow);
        int nowMinute = Integer.parseInt(minuteNow);
        int nowSecond = Integer.parseInt(secondNow);


//        System.out.println("dbYear: "+ dbYear + " dbMonth: " + dbMonth + " dbDay: " + dbDay);
//        System.out.println("nowYear: "+ nowYear + " nowMonth: " + nowMonth + " nowDay: " + nowDay);
//        System.out.println("dbHour: "+ dbHour + " dbMinute: " + dbMinute + " dbSecond: " + dbSecond);
//        System.out.println("nowHour: "+ nowHour + " nowMinute: " + nowMinute + " nowSecond: " + nowSecond);

        //algorithm for finding difference
        if(dbYear != nowYear){
            removeSession(full);
            return("Removing Data");
        }else if(dbMonth != nowMonth){
            removeSession(full);
            return("Removing Data");
        }else if(dbDay != nowDay){
            removeSession(full);
            return("Removing Data");
        }else if(dbHour != nowHour){
            int difference = nowHour - dbHour;
            if(difference >= 12){
                removeSession(full);
                return("Removing Data");
            }else{
                return("time is good");
            }
        }else{
            return("Time is good");
        }
    }

    public void removeSession(String dateTime){
        Connection connection = null;
        Statement st = null;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://104.194.207.43:3306/camivide_anonyomail", "camivide_anonyomail", "(~mVw1o0?Lz_");
            st = connection.createStatement();

            int i = st.executeUpdate("UPDATE sessions SET session_id = NULL WHERE created ='" + dateTime + "'");

            if(i > 0){
                System.out.println("Record has been Updated....");
            }
            else {
                System.out.println("Something Went wrong");
            }
        }
        catch(Exception ex){
            System.out.println("Expection : " + ex.toString());
        }
    }

    public void app(){
        Algorithm algorithm = new Algorithm();
        Connection connection = null;
        Statement st = null;
        ResultSet rs = null;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://HOST", "USERNAME", "PASS");
            st = connection.createStatement();
            rs = st.executeQuery("SELECT * FROM `sessions` WHERE `session_id` IS NOT NULL");
            while(rs.next()){
                String algo = algorithm.algorize(rs.getString(5));
                System.out.println(rs.getString(5));
                System.out.println(algo);
                System.out.println("");

            }
        }
        catch(Exception ex){
            System.out.println("Exception : " + ex.toString());
        }
    }
}
