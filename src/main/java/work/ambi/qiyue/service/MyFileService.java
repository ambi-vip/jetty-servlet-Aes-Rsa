package work.ambi.qiyue.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import work.ambi.qiyue.entity.MyFile;
import work.ambi.qiyue.repository.MyFileRepository;

import java.util.List;

/**
 * @author Ambi
 * @version 1.0
 * @date 2020/2/29 16:38
 */
@Service
public class MyFileService {

    @Autowired
    private MyFileRepository repository;

    public void save(MyFile file){
        repository.save(file);
    }

    public List<MyFile> AllFiles(){
        List<MyFile> all = repository.findAll();
        return all;
    }
}
