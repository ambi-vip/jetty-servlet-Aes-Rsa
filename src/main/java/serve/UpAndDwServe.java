package serve;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Ambi
 * @version 1.0
 * @date 2020/3/3 9:35
 */
public interface UpAndDwServe {

    //得到文件流加密后存储
    public void file2Aes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;


}
