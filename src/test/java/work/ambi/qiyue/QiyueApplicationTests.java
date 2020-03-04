package work.ambi.qiyue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import work.ambi.qiyue.entity.MyFile;
import work.ambi.qiyue.repository.MyFileRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;


@SpringBootTest
class QiyueApplicationTests {

    @Autowired
    private MyFileRepository repository;

    @Test
    void contextLoads() {
        Optional<MyFile> byId = repository.findById("4ecf9257-2e35-44ec-888a-b1d23f2353b2");
        System.out.println(byId.get().getOldname());
    }

}
