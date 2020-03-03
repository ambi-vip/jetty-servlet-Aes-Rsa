package servlet;



import entity.MyFile;
import org.eclipse.jetty.server.Request;
import serve.MyFileServe;
import serve.UpAndDwServe;
import serve.impl.MyFileServeImpl;
import serve.impl.UpAndDwServeImpl;
import until.GetJson;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

/**
 * @author Ambi
 * @version 1.0
 * @date 2020/3/1 20:59
 */

//设置访问调用这个Servlet的路径.我设置了感觉没啥用。
@WebServlet("/Upload")
//说明该Servlet处理的是multipart/form-data类型的请求
@MultipartConfig
public class Upload extends HttpServlet {

    private UpAndDwServe upAndDwServe = new UpAndDwServeImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException,IOException{
        upAndDwServe.file2Aes(request,response);
    }
}