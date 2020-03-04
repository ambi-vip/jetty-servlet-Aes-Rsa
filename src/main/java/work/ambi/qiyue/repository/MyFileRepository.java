package work.ambi.qiyue.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import work.ambi.qiyue.entity.MyFile;

/**
 * @author Ambi
 * @version 1.0
 * @date 2020/2/29 17:02
 */
@Repository
public interface MyFileRepository extends JpaRepository<MyFile,String> {
}
