package dao;

import entity.MyFile;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

/**
 * @author Ambi
 * @version 1.0
 * @date 2020/3/1 0:31
 */
public class fileDaoTest {

    @Test
    public void tt(){
        FileDao fileDao = new FileDao();
        fileDao.SSS();
    }

    @Test
    public void All(){
        FileDao fileDao = new FileDao();
        List<MyFile> all = fileDao.findAll();
        all.forEach(t-> System.out.println(t));
    }

    @Test
    public void save(){
        FileDao fileDao = new FileDao();
       MyFile file = new MyFile();
       file.setUid(UUID.randomUUID().toString());
       file.setFileSize(1);
        int savefile = fileDao.savefile(file);
        if (savefile==0){
            System.out.println("success");
        }
    }
}