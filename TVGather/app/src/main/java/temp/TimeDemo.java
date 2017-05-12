package temp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by MaZhihua on 2017/4/25.
 */

public class TimeDemo {

    public static void main(String [] args){
        long temp = 1506787200000l;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("date = "+ dateFormat.format(new Date(temp)));

        String kkk = "-0.25";
        System.out.println("kkk = " + (Float.valueOf(kkk) > 0 ? true : false));


        long system = System.currentTimeMillis();
        long user = 1493139744000l;
        long hour24 = 24 * 60 * 60 * 1000;
        int a = 199/4;

        System.out.println("24小时转化为毫秒为："  + hour24);
        System.out.println("转化后为："  + secToTime(1800));
        System.out.println("转化后为2："  + methodTime2(24 * 60 * 60));
        System.out.println("页码测试："  + a);
    }

    // a integer to xx:xx:xx
    public static String secToTime(long time) {
        String timeStr = null;
        long hour = 0;
        long minute = 0;
        long second = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    public static String unitFormat(long i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Long.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }

    public static String methodTime2(long time){
        long hour = time / 3600;
        long minute = time / 60 % 60;
        long second = time % 60;
        StringBuilder sb = new StringBuilder();
        sb.append(unitFormat(hour));
        sb.append(":");
        sb.append(unitFormat(minute));
        sb.append(":");
        sb.append(unitFormat(second));
        return sb.toString();
    }

}
