package work.ambi.qiyue.provider;

/**
 * @author Ambi
 * @version 1.0
 * @date 2020/2/29 15:51
 */

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.models.auth.In;
import lombok.Data;

import java.util.List;

/**
 *
 * @Title: LeeJSONResult.java
 * @Package com.lee.utils
 * @Description: 自定义响应数据结构
 *                 这个类是提供给门户，ios，安卓，微信商城用的
 *                 门户接受此类数据后需要使用本类的方法转换成对于的数据类型格式（类，或者list）
 *                 其他自行处理
 *                 200：表示成功
 *                 500：表示错误，错误信息在msg字段中
 *                 501：bean验证错误，不管多少个错误都以map形式返回
 *                 502：拦截器拦截到用户token出错
 *                 555：异常抛出信息
 * Copyright: Copyright (c) 2016
 * Company:Nathan.Lee.Salvatore
 *
 * @author leechenxiang
 * @date 2016年4月22日 下午8:33:36
 * @version V1.0
 */
@Data
public class JsonResult {

    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    // 响应业务状态
    private Integer status;

    // layui的code
    private Integer code;

    // 响应消息
    private String msg;

    // 响应中的数据
    private Object data;



    private String ok;    // 不使用

    public static JsonResult build(Integer status, String msg, Object data,Integer code) {
        return new JsonResult(status, msg, data,code);
    }

    public static JsonResult ok(Object data) {
        return new JsonResult(data);
    }

    public static JsonResult ok() {
        return new JsonResult(null);
    }

    public static JsonResult errorMsg(String msg) {
        return new JsonResult(500, msg, null,500);
    }

    public static JsonResult errorMap(Object data) {
        return new JsonResult(501, "error", data,500);
    }

    public static JsonResult errorTokenMsg(String msg) {
        return new JsonResult(502, msg, null,500);
    }

    public static JsonResult errorException(String msg) {
        return new JsonResult(555, msg, null,500);
    }

    public JsonResult() {

    }

//    public static LeeJSONResult build(Integer status, String msg) {
//        return new LeeJSONResult(status, msg, null);
//    }

    public JsonResult(Integer status, String msg, Object data, Integer code) {
        this.status = status;
        this.msg = msg;
        this.data = data;
        this.code = code;
    }

    public JsonResult(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
        this.code = 0;
    }

    public Boolean isOK() {
        return this.status == 200&&this.code==0;
    }


    /**
     *
     * @Description: 将json结果集转化为LeeJSONResult对象
     *                 需要转换的对象是一个类
     * @param jsonData
     * @param clazz
     * @return
     *
     * @author leechenxiang
     * @date 2016年4月22日 下午8:34:58
     */
    public static JsonResult formatToPojo(String jsonData, Class<?> clazz) {
        try {
            if (clazz == null) {
                return MAPPER.readValue(jsonData, JsonResult.class);
            }
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (clazz != null) {
                if (data.isObject()) {
                    obj = MAPPER.readValue(data.traverse(), clazz);
                } else if (data.isTextual()) {
                    obj = MAPPER.readValue(data.asText(), clazz);
                }
            }
            return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj,jsonNode.get("status").intValue());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     *
     * @Description: 没有object对象的转化
     * @param json
     * @return
     *
     * @author leechenxiang
     * @date 2016年4月22日 下午8:35:21
     */
    public static JsonResult format(String json) {
        try {
            return MAPPER.readValue(json, JsonResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @Description: Object是集合转化
     *                 需要转换的对象是一个list
     * @param jsonData
     * @param clazz
     * @return
     *
     * @author leechenxiang
     * @date 2016年4月22日 下午8:35:31
     */
    public static JsonResult formatToList(String jsonData, Class<?> clazz) {
        try {
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (data.isArray() && data.size() > 0) {
                obj = MAPPER.readValue(data.traverse(),
                        MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
            }
            return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj,jsonNode.get("status").intValue());
        } catch (Exception e) {
            return null;
        }
    }

    public String getOk() {
        return ok;
    }

    public void setOk(String ok) {
        this.ok = ok;
    }

}