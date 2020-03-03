package servlet;

import entity.MyFile;
import serve.MyFileServe;
import serve.impl.MyFileServeImpl;
import until.GetJson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author Ambi
 * @version 1.0
 * @date 2020/3/2 9:30
 */
public class FileLiset extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private MyFileServe myFileServe = new MyFileServeImpl();



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            /* 设置格式为text/json */
            resp.setContentType("text/html; charset=UTF-8");
            /* 设置字符集为'UTF-8' */
            resp.setCharacterEncoding("UTF-8");
            /*  允许跨域访问 */
            resp.setHeader("Access-Control-Allow-Origin", "*");
            resp.setHeader("Access-Control-Allow-Methods", "*");
            resp.setHeader("Access-Control-Max-Age", "3600");
            resp.addHeader("Access-Control-Allow-Headers", "*");
            resp.setHeader("Access-Control-Allow-Credentials", "*");
            List<MyFile> allFile = myFileServe.findAllFile();

            PrintWriter out = resp.getWriter();
            String ok = GetJson.toJson(allFile,0,"s");
            System.out.println(ok);
            out.write(ok);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }

    public StringBuffer getJson(List<MyFile> list, Integer code, String msg){
        StringBuffer  json = new StringBuffer("{\"count\":1,\"code\":");
        json.append(code+",\"msg\":\"" + msg + "\",\"data\":");
        StringBuffer  data= new StringBuffer("[");
        for(Object obj:list){
            data.append("{"+obj+"},");
        }
        json.append(data.substring(0,data.length()-1)+"]}");
        return json;
    }
}
