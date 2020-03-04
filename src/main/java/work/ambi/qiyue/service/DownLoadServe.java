package work.ambi.qiyue.service;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import work.ambi.qiyue.until.SymmetricEncoder;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.Socket;
import java.util.*;

/**
 * @author Ambi
 * @version 1.0
 * @date 2020/3/2 14:41
 */
@Service
public class DownLoadServe {

    /**
     * 最大线程池
     */
    public static final int THREAD_POOL_SIZE = 5;

    private String fileSavingPath = "C:\\Users\\Administrator\\Documents\\java\\b\\";

    //私钥
    private String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALwcyvYIGmhk+be320JWWsq1OYjiM0lzv8eHGMgSIOMLxzM/g9X7jguNe8thxJXR/CLqcTgsfZzk8E8Sc9+qnSDxNl5f5tga93vRxd5713zAeAGqLiTQnRffdzRmdbsmu5+0/K8mj056VhKh8FdBNzAj7e4iX9i+uBBG/oDmZbTVAgMBAAECgYEAmgNU5NTDkj9B+Pnt6UU8doSjw3+3j+bV2K2yS3QUOvAUus/Ax7x6ktjWxzCXvDY9IfUil2RNv9vtKEAqYLCWjc+lf8PV/yH1b7NEgyeAPBXtAJRoOnmYL2bdPW92kP9KgxJruF6Dz/C5AmMOncsvq8ABD+9Darn4p8dwj2ZC4O0CQQDf/AHmZsQokEItfCy4mHS9UbxbfIhEUv1ApPh/+Sr7NkJkHWYCtBQo+8jKO6zurAZQgWBPD1XX2UE4R+VIiZazAkEA1wAqtMvGhccyRZr+6kpkpDIa8+9jOE+nGUzqTDvgCID6as8AzOONFVVK6m/UUqkhcJ8Qu1pF36BGojy5BX2KVwJBAJSFpbji0hXXupowqfLp3RcgmNbNWAp+QUJZYhJx5cdYbmO2fssyH+AhPT6knYJR/YnqkDM8hv6vKCkqu2YDHjMCQAOA8TE5EOclM+CGghj3VWSHnIDVKdzFD4gOBNNxNlltIKeU8AJmwunSFgJ0CBXAw9a+ANvMwM7AIeaK7sj0HskCQAvxfDCq7gaNx+pfu0FHG8Gix08A/A6foggBl1fVu+L9sr9ZuOQ3HbXnl28F9ewuB9xdjnLUDjp7W7U0pB+vKoQ=";


    public interface HttpClientDownLoadProgress {
        public void onProgress(int progress);
    }

    public String logDownload(String uid, String filetype) throws Exception {
        // 创建Httpclient对象,相当于打开了浏览器
        CloseableHttpClient httpclient = HttpClients.createDefault();

        // 创建httpPost请求，相当于在浏览器输入地址
        HttpPost httpPost = new HttpPost("http://localhost:8080/DownLoad");
        // 把自己伪装成浏览器。否则开源中国会拦截访问
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");

        // 根据开源中国的请求需要，设置post请求参数
        List<NameValuePair> parameters = new ArrayList<NameValuePair>(0);
        parameters.add(new BasicNameValuePair("uid", uid));
        // 构造一个form表单式的实体
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters);
        // 将请求实体设置到httpPost对象中
        httpPost.setEntity(formEntity);

        CloseableHttpResponse response = null;
        try {
            // 执行请求，相当于敲完地址后按下回车。获取响应
            response = httpclient.execute(httpPost);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                // 解析响应，获取数据
//                String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                HttpEntity httpEntity = response.getEntity();
                long contentLength = httpEntity.getContentLength();
                InputStream is = httpEntity.getContent();
                // 根据InputStream 下载文件

                byte[] getData = readInputStream(is);
//                is.read(getData);
                String str = new String(getData);
                String out2File = SymmetricEncoder.AESDncode("123", str);
//                System.out.println(out2File);

                //解密后的文件。输出到文件。
                String UUid = UUID.randomUUID().toString();
                fileSavingPath = fileSavingPath + UUid +filetype;
                FileWriter fwriter = null;
                try {
                    fwriter = new FileWriter(fileSavingPath);
                    fwriter.write(out2File);
                    //输出上传成功信息
                    System.out.println("文件生成成功~！");
                } catch (IOException ex) {
                    ex.printStackTrace();
                } finally {
                    try {
                        fwriter.flush();
                        fwriter.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

//                ByteArrayOutputStream output = new ByteArrayOutputStream();
//                byte[] bytes = out2File.getBytes("utf-8");
//                System.out.println(new String(bytes));
//                output.write(bytes,0,out2File.length());
//
//                FileOutputStream fos = new FileOutputStream(fileSavingPath);
//                output.writeTo(fos);
//                output.flush();
//                output.close();
//                fos.close();
                EntityUtils.consume(httpEntity);
                System.out.println("下载成功");
                return "下载成功";
            }
        }catch (Exception e){
            return "下载失败";
        }finally {
            if (response != null) {
                // 关闭资源
                response.close();
            }
            // 关闭浏览器
            httpclient.close();
            return "下载成功";
        }
    }

    public static  byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

}
