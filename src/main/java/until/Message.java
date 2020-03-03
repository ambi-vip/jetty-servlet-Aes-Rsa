package until;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ambi
 * @version 1.0
 * @date 2020/3/2 11:41
 */
public class Message {

    private static final long serialVersionUID = -4505655308965878999L;
    /**
     * 消息头meta 存放状态信息 code message
     */
    private Integer code;
    private String msg;
    private Boolean success;
    private Timestamp timestamp;
    /**
     * 消息内容  存储实体交互数据
     */
    private Map<String,Object> data = new HashMap<String,Object>();


    public Map<String, Object> getData() {
        return data;
    }

    public Message setData(Map<String, Object> data) {
        this.data = data;
        return this;
    }


    public Message addData(String key,Object object) {
        this.data.put(key,object);
        return this;
    }
    public Message ok(String statusMsg) {
        this.code = 0;
        this.msg = statusMsg;
        this.success=Boolean.TRUE;
        this.timestamp = new Timestamp(System.currentTimeMillis());
        return this;
    }
    public Message error(int statusCode,String statusMsg) {
        this.code = statusCode;
        this.msg = statusMsg;
        this.success=Boolean.FALSE;
        this.timestamp = new Timestamp(System.currentTimeMillis());
        return this;
    }
}
