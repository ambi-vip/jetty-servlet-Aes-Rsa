package servlet;

import entity.MyFile;
import org.junit.Test;
import serves.MyFileServe;
import serves.impl.MyFileServeImpl;

import java.util.List;

/**
 * @author Ambi
 * @version 1.0
 * @date 2020/3/2 9:41
 */
public class FileLisetTestList {

    @Test
    public void doPost() {
        MyFileServe myFileServe= new MyFileServeImpl();
        List<MyFile> allFile = myFileServe.findAllFile();
        FileLiset fileLiset = new FileLiset();
        String json = fileLiset.getJson(allFile,0,"ok").toString();
        System.out.println(json);
    }
}