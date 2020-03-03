package servlet;



import serves.UpAndDwServe;
import serves.impl.UpAndDwServeImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    //公钥
    private String pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC8HMr2CBpoZPm3t9tCVlrKtTmI4jNJc7/HhxjIEiDjC8czP4PV+44LjXvLYcSV0fwi6nE4LH2c5PBPEnPfqp0g8TZeX+bYGvd70cXee9d8wHgBqi4k0J0X33c0ZnW7JruftPyvJo9OelYSofBXQTcwI+3uIl/YvrgQRv6A5mW01QIDAQAB";


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException,IOException{
        upAndDwServe.file2Aes(request,response);
    }
}