package work.ambi.qiyue.entity;

import lombok.Data;
import lombok.Generated;

import javax.sql.DataSource;
import java.sql.Date;

/**
 * @author Ambi
 * @version 1.0
 * @date 2020/2/29 16:29
 */
@Data

public class MyFile {

    private String uid;
    private String filetype;
    private String oldname;
    private Date createAt;
    private String path;
    private String rbs;
    private Integer isDel;
    private String fileSize;

//    @Column 描述了数据库表中该字段的详细定义，这对于根据 JPA 注解生成数据库表结构的工具。name: 表示数据库表中该字段的名称，默认情形属性名称一致；nullable: 表示该字段是否允许为 null，默认为 true；unique: 表示该字段是否是唯一标识，默认为 false；length: 表示该字段的大小，仅对 String 类型的字段有效。

}
