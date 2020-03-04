package work.ambi.qiyue.until;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Ambi
 * @version 1.0
 * @date 2020/3/3 12:08
 */
public class ReadConfigFile {

    public static Map<String,String> get(String cfgFile){
        Map<String,String> map = new HashMap<>();
        try {
            InputStream in = ReadConfigFile.class.getClassLoader().getResource(cfgFile).openStream();
            Properties prop = new Properties();
            prop.load(in);
            map.put("priKey",prop.getProperty("priKey"));
            return map;
        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
