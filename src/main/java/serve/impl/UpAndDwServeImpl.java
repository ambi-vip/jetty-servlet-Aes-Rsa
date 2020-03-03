package serve.impl;

import entity.MyFile;
import org.eclipse.jetty.server.Request;
import serve.MyFileServe;
import serve.UpAndDwServe;
import until.GetJson;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author Ambi
 * @version 1.0
 * @date 2020/3/3 9:36
 */
//说明该Servlet处理的是multipart/form-data类型的请求
@MultipartConfig
public class UpAndDwServeImpl implements UpAndDwServe {


    //使用MultipartConfig需要加上这个配置，不加报MultipartConfig不存在
    private static final MultipartConfigElement MULTI_PART_CONFIG = new MultipartConfigElement("E:\\javacode\\SpringBoot\\test\\fileServe");
    private static final long serialVersionUID = 1L;

    private MyFileServe myFileServe = new MyFileServeImpl();

    @Override
    public void file2Aes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String contentType = request.getContentType();
        //判断是不是文件请求。必须加
        if(contentType != null && contentType.startsWith("multipart/")){
            /*  允许跨域访问 */
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "*");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.addHeader("Access-Control-Allow-Headers", "*");
            response.setHeader("Access-Control-Allow-Credentials", "*");
            //保存文件信息。存在在数据库中。
            MyFile file = new MyFile();
            //设置文件配置。必须加。
            request.setAttribute(Request.MULTIPART_CONFIG_ELEMENT, MULTI_PART_CONFIG);
            //说明输入的请求信息采用UTF-8编码方式
            request.setCharacterEncoding("utf-8");
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            //Servlet3.0中新引入的方法，用来处理multipart/form-data类型编码的表单
            Part part = request.getPart("file");
            //获取HTTP头信息headerInfo=（form-data; name="file" filename="文件名"）
            String headerInfo = part.getHeader("content-disposition");
            System.out.println("文件大小："+part.getSize());
            file.setFileSize((int) part.getSize());
            //从HTTP头信息中获取文件名fileName=（文件名）
            String fileName = headerInfo.substring(headerInfo.lastIndexOf("=") + 2, headerInfo.length() - 1);
            file.setOldname(fileName);
            //获得存储上传文件的文件夹路径
            String fileSavingFolder = request.getServletContext().getRealPath("upload");
            //按理说这个获取upload的相对路径。我这不对。
            System.out.println(fileSavingFolder);
            if (fileSavingFolder==null){
                fileSavingFolder="upload";
            }
            //获取文件的后缀名
            String suffix = "."+ fileName.substring(fileName.lastIndexOf(".") + 1);
            file.setFiletype(suffix);
            //UUID
            String uuid = UUID.randomUUID().toString();
            file.setUid(uuid);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            //获取当前日期
            String currentDate = dateFormat.format( new Date() );
            //获得存储上传文件的完整路径（文件夹路径+文件名）
            //文件夹位置固定，文件夹采用与上传文件的原始名字相同

            String fileSavingPath = fileSavingFolder + File.separator + currentDate + File.separator +uuid +suffix;
            System.out.println(fileSavingPath);
            file.setPath(fileSavingPath);
            //如果存储上传文件的文件夹不存在，则创建文件夹
            File f = new File(fileSavingFolder+File.separator + currentDate);
            if(!f.exists()){
                System.out.println("新建文件夹");
                f.mkdirs();
            }
            String absolutePath = f.getAbsolutePath();
            System.out.println(absolutePath);
            //将上传的文件内容写入服务器文件中
            part.write(fileSavingPath);
            //输出上传成功信息
            System.out.println("文件上传成功~！");
            myFileServe.saveFile(file);
            out.println(GetJson.ok("kkkk",fileSavingPath.replaceAll( "\\\\",   "\\\\\\\\")));
        } else {

        }
    }
}
