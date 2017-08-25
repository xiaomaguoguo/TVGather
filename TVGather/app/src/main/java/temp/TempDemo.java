package temp;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by KNothing on 2017/4/18.
 */

public class TempDemo {
    public static void main(String [] args){

        sortCollection();

        String dbName = "test_%s.db";
        System.out.println("dbName = " + String.format(dbName,""));

        String name = "http://download.haozip.com/haozip_v2.8_tiny.exe";

        long system = System.currentTimeMillis();
        long user = 1493129276000l;
        long kkk = system - user;
        System.out.println("KKK = " + kkk);

        convertUpOrDown();

        timeTest();

        ymd();

        formatMath();

        futureTime();

        System.out.println(name.substring(name.lastIndexOf("/") + 1));

    }

    private static void futureTime() {
        long pre =1493551146990l;
        long system = System.currentTimeMillis();
        long temp = 1493554913000l;
        System.out.println("System = "+ String.valueOf(system - pre));

        long kkk = system + 171244;
        System.out.println("System2 ==== "+ String.valueOf(kkk));




    }

    private static void formatMath() {
        double sss = -8.16223423423;
        String result = String.format("%.2f",sss);
        System.out.println("保留小数后两位 result =  "+ result);
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

    public static void sortCollection(){
        ArrayList<Integer> a = new ArrayList<>(9);
        a.add(9);
        a.add(1);
        a.add(3);
        a.add(2);
        a.add(6);
        a.add(5);
        a.add(8);
        a.add(7);
        a.add(4);
        Collections.sort(a, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 > o2 ? -1 : 0;
            }
        });

        for(int b : a){
            System.out.println("b = " + b);
        }
    }

}
