package serves;

import entity.MyFile;

import java.util.List;

/**
 * @author Ambi
 * @version 1.0
 * @date 2020/3/2 9:11
 */
public interface MyFileServe {
    public int saveFile(MyFile file);

    public List<MyFile> findAllFile();

    public MyFile findByUid(String uid);
}
