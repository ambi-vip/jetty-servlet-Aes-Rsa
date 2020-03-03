package servlet;

import entity.MyFile;
import serves.MyFileServe;
import serves.UpAndDwServe;
import serves.impl.MyFileServeImpl;
import serves.impl.UpAndDwServeImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
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
@WebServlet("DownLoad")
public class DownLoad extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private MyFileServe myFileServe = new MyFileServeImpl();
    private UpAndDwServe upAndDwServe = new UpAndDwServeImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        upAndDwServe.dpwnloadFile(req,resp);
    }
}
