package serves.impl;

import dao.FileDao;
import entity.MyFile;
import serves.MyFileServe;

import java.util.List;

/**
 * @author Ambi
 * @version 1.0
 * @date 2020/3/2 9:11
 */
public class MyFileServeImpl implements MyFileServe {

    private FileDao fileDao = new FileDao();

    @Override
    public int saveFile(MyFile file) {
        return fileDao.savefile(file);
    }

    @Override
    public List<MyFile> findAllFile() {
        List<MyFile> all = fileDao.findAll();
        //自己写的json需要吧“\”转成“\\”
        all.forEach(t->t.setPath(t.getPath().replaceAll( "\\\\",   "\\\\\\\\")));
        return all;
    }

    @Override
    public MyFile findByUid(String uid) {
        return fileDao.findByUid(uid);
    }
}
