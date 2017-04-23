/**
 * Created by KNothing on 2017/4/18.
 */

public class Test {
    public static void main(String [] args){

        convertUpOrDown();

    }

    static void convertUpOrDown(){
        String yesteday = "0.26";
        String today = "0.29";
        float test = Float.valueOf(today) - Float.valueOf(yesteday);
        if(test > 0){
            System.out.println("test = "+ test);
        }
    }
}
