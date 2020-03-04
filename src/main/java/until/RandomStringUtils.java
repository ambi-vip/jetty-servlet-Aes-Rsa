package until;

import java.util.Random;

/**
 * 指定长度随机字符串生成
 * @author Ambi
 * @version 1.0
 * @date 2020/3/3 12:02
 */
public class RandomStringUtils {

    //length用户要求产生字符串的长度
    public static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return String.valueOf(sb);//String.valueOf效率更高
    }
}
