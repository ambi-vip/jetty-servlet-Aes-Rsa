package servlet;

import entity.MyFile;
import serve.MyFileServe;
import serve.impl.MyFileServeImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * @author Ambi
 * @version 1.0
 * @date 2020/3/2 9:25
 */
public class DownLoad extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private MyFileServe myFileServe = new MyFileServeImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*  允许跨域访问 */
        try{
            String uid = req.getParameter("uid");
            System.out.println(uid);
            ServletContext servletContext = this.getServletContext();
            MyFile myFile = myFileServe.findByUid(uid);
            System.out.println(myFile.getOldname());
            //服务器相对路径
            String filepath = myFile.getPath();
            System.out.println(filepath);

            /*打开文件，创建File类型的文件对象*/
            File file = new File(filepath);
            /*如果文件存在*/
            if(file.exists()){
                System.out.println("文件存在");

                /*获得文件名，并采用UTF-8编码方式进行编码，以解决中文问题*/
                String filename = URLEncoder.encode(file.getName(), "UTF-8");
                System.out.println(filename);
                /*重置response对象*/
                resp.reset();
                //设置文件的类型，xml文件采用text/xml类型，详见MIME类型的说明
                String mimeType = servletContext.getMimeType(filename);
                resp.setHeader("Access-Control-Allow-Origin", "*");
                resp.setHeader("Access-Control-Allow-Methods", "*");
                resp.setHeader("Access-Control-Max-Age", "3600");
                resp.addHeader("Access-Control-Allow-Headers", "*");
                resp.setHeader("Access-Control-Allow-Credentials", "*");
                resp.setCharacterEncoding("UTF-8");
                resp.setHeader("content-type","application/octet-stream;charset=UTF-8");
//                //3.2设置响应头打开方式：content-disposition
                resp.setHeader("content-disposition","attachment;filename="+filename);
                //设置HTTP头信息中内容
//                resp.addHeader("Content-Disposition","attachment:filename=\"" + filename + "\"" );
                //设置文件的长度
                int fileLength = (int)file.length();
                System.out.println(fileLength);
                resp.setContentLength(fileLength);
                /*如果文件长度大于0*/
                if(fileLength!=0){
                    //创建输入流
                    InputStream inStream = new FileInputStream(file);
                    byte[] buf = new byte[4096];
                    //创建输出流
                    ServletOutputStream servletOS = resp.getOutputStream();
                    int readLength;
                    //读取文件内容并写到response的输出流当中
                    while(((readLength = inStream.read(buf))!=-1)){
                        servletOS.write(buf, 0, readLength);
                    }
                    //关闭输入流
                    inStream.close();
                    //刷新输出缓冲
                    servletOS.flush();
                    //关闭输出流
                    servletOS.close();
                }
            }else {
                System.out.println("文件不存在~！");
                PrintWriter out = resp.getWriter();
                out.println("文件 \"" + myFile.getOldname() + "\" 不存在");
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
