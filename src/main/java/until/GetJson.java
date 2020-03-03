package until;

import entity.MyFile;

import java.util.List;

/**
 * @author Ambi
 * @version 1.0
 * @date 2020/3/2 11:47
 */
public class GetJson {
    public static String toJson(List<MyFile> list, Integer code, String msg){
        StringBuffer  json = new StringBuffer("{\"code\":");
        json.append(code+",\"msg\":\"" + msg + "\",\"data\":");
        StringBuffer  data= new StringBuffer("[");
        for(Object obj:list){
            data.append("{"+obj+"},");
        }
        json.append(data.substring(0,data.length()-1)+"]}");
        return json.toString();
    }

    public static String ok(String msg,String src){
        StringBuffer  json = new StringBuffer("{\"code\":");
        json.append(0+",\"msg\":\"" + msg + "\",\"data\":{\"src\":");
        json.append('\"'+src+"\"}}");
        return json.toString();
    }
}
