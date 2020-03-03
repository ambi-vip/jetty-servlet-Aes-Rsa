package until;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Ambi
 * @version 1.0
 * @date 2020/3/2 11:51
 */
public class GetJsonTest {
    @Test
    public void ok(){
        String ok = GetJson.ok("ok", "sss");
        System.out.println(ok);
    }
}