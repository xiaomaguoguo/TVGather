package temp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by KNothing on 2017/4/18.
 */

public class TempDemo {
    public static void main(String [] args){

        long system = System.currentTimeMillis();
        long user = 1493129276000l;
        long kkk = system - user;
        System.out.println("KKK = " + kkk);

        convertUpOrDown();

        timeTest();

        ymd();

    }

    private static void ymd() {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(2017,04,24);
        System.out.println("时间是："+mCalendar.get(Calendar.YEAR) + "-" + mCalendar.get(Calendar.MONTH) + "-" + mCalendar.get(Calendar.DAY_OF_MONTH) );
    }

    static void convertUpOrDown(){
        String yesteday = "0.26";
        String today = "0.29";
        float test = Float.valueOf(today) - Float.valueOf(yesteday);
        if(test > 0){
            System.out.println("test = "+ test);
        }
    }

    static void timeTest(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse("2017-04-24:17:40:19");
            System.out.println(date.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println(System.currentTimeMillis());

        String ymd = "2017-04-24";
        Calendar calendar = Calendar.getInstance();
        calendar.set(2017,04,24);
        System.out.println(calendar.getTimeInMillis());

        long user = calendar.getTimeInMillis();
        long system = System.currentTimeMillis();
        long diff = system - user;
        System.out.println("diff = "+ diff);


        calendar.setTimeInMillis(user + diff);
        long cccc = calendar.getTimeInMillis();
        String kk = sdf.format(cccc);
        System.out.println("过了 = " + kk);
        System.out.println("今天是 = " + getWeekOfDate(calendar.getTime()));

    }

    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
}
