package temp;

import java.util.Date;

/**
 * Created by MaZhihua on 2017/5/22.
 */
public class FanXinDemo<K extends Object,V extends Object> implements FanXin<String>{

    public static void main(String [] args){
        FanXinDemo<Date,String> fanXinDemo = new FanXinDemo<>();
        fanXinDemo.wtf(null);

        FanXin<String> mFanxin = new FanXinDemo<>();
        mFanxin.getType("String HHHKKKK");

    }

    @Override
    public void getType(String s) {

    }

    public String  wtf(K k){
        if(k != null){
            return k.toString();
        }
        return null;
    }
}


interface FanXin<T>{
    void getType(T t);
}

