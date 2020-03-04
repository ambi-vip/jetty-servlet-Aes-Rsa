package until;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

/**
 * @author Ambi
 * @version 1.0
 * @date 2020/3/4 0:34
 */
public class TokenRsa {

    private static final String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCqW2VX0YoBwhWvXKGCEui/r1FwsrTFRGdL/IKW+igpznK37AAB4PLQsXgHLP+tntFcLSUVxnB8q1mLEQRhdo+S01v7NxnjLrvmCHP6C+xTlIJw22zWmdAhPrmcKouVDM/TFvsPxuevv1MJzjUn36RzZmF8Jg6+0N0kA3M9k8LnWwIDAQAB";


    /**
     * 读取内容header
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static  byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[4096];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    /**
     * 判断SID是否符合要求。
     * @param
     * @return
     * @throws IOException
     * @throws ServletException
     */
    public static boolean Authentication(HttpServletRequest request, HttpServletResponse response) {

        String SID = request.getHeader("X-SID");
        String Signature = request.getHeader("X-Signature");
        System.out.println("SID     "+SID);
        //用公钥解锁。解锁后内容与SID比较
        String result = RsaUtil.deWithRSAPublicKey(Signature, publicKey);
        if (result==null){
            return false;
        }
        if (SID.equals(result)){
            System.out.println("验证成功");
            return true;
        }
        return false;
    }
}
