package dao;

import entity.MyFile;
import org.apache.log4j.Logger;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * @author Ambi
 * @version 1.0
 * @date 2020/2/29 23:48
 */
public class FileDao {
    private static String url = "jdbc:derby:qiyue;create=true";

    public MyFile findByUid(String Uid){
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");//加载驱动
            Connection conn = DriverManager.getConnection("jdbc:derby:derbyDB;create=true","user","pwd");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from FILES where UID = '" + Uid+"'");
            List<MyFile> list = new ArrayList<>();
            while (rs.next()){
                MyFile file = new MyFile();
                file.setUid(rs.getString(1).trim());
                file.setFileSize(rs.getInt(2));
                file.setFiletype(rs.getString(3).trim());
                file.setOldname(rs.getString(4).trim());
                file.setPath(rs.getString(5).trim());
                file.setCreateAt(rs.getDate(6));
                file.setRbs(rs.getString(7).trim());
                file.setIsDel(rs.getInt(8));
                list.add(file);
            }
            System.out.println("查询一条数据！");
            return list.get(0);
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            return null;
        }finally{
//            try {
//                DriverManager.getConnection("jdbc:derby:;shutdown=true");
//            } catch (SQLException ex) {
//                System.out.println("关闭数据库！");
//                //     Logger.getLogger(TestDerbyServer.class.getName()).log(Level.SEVERE, null, ex);
//            }

        }

    }


    /**
     * 保存文件信息
     * @param file
     * @return  返回执行成功的条数
     */
    public int savefile(MyFile file){
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");//加载驱动
            Connection conn = DriverManager.getConnection("jdbc:derby:derbyDB;create=true","user","pwd");
            Statement st = conn.createStatement();
            int execute = st.executeUpdate(" INSERT INTO FILES (UID, FILESIZE, FILETYPE, OLDNAME, PATH,RBS) VALUES ('" +
                    file.getUid() + "'," +
                    file.getFileSize()+ ",'" +
                    file.getFiletype() + "','" +
                    file.getOldname() + "','" +
                    file.getPath() + "','" +
                    file.getRbs() + "')");
            System.out.println("新增数据成功！");
            return execute;
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            return 0;
        }finally{
//            try {
//                DriverManager.getConnection("jdbc:derby:;shutdown=true");
//            } catch (SQLException ex) {
//                System.out.println("关闭数据库！");
//                //     Logger.getLogger(TestDerbyServer.class.getName()).log(Level.SEVERE, null, ex);
//            }

        }

    }

    public List<MyFile> findAll(){
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");//加载驱动
            Connection conn = DriverManager.getConnection("jdbc:derby:derbyDB;create=true","user","pwd");
            Statement st = conn.createStatement();
            //只显示最近10条数据
            /**
             * SELECT * FROM YOUR_TABLE
             * OFFSET ? ROWS
             * FETCH NEXT ? ROWS ONLY;
             * 稍微解释一下：
             * OFFSET ? ROWS - 是指跳过 ? 条记录
             * FETCH NEXT ? ROWS ONLY - 是指抓取下一个 ? 条记录
             */
            ResultSet rs = st.executeQuery(" select * from FILES order by CREATE_AT desc FETCH NEXT 10 ROWS ONLY   ");
            List<MyFile> list = new ArrayList<>();
            while(rs.next())
            {
                MyFile file = new MyFile();
                file.setUid(rs.getString(1).trim());
                file.setFileSize(rs.getInt(2));
                file.setFiletype(rs.getString(3).trim());
                file.setOldname(rs.getString(4).trim());
                file.setPath(rs.getString(5).trim());
                file.setCreateAt(rs.getDate(6));
                file.setRbs(rs.getString(7).trim());
                file.setIsDel(rs.getInt(8));
                list.add(file);
            }
            rs.close();
            return list;
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            return null;
        }finally{
            System.out.println("查询全部数据！");
//            try {
//                DriverManager.getConnection("jdbc:derby:;shutdown=true");
//            } catch (SQLException ex) {
//                System.out.println("关闭数据库！");
//                //     Logger.getLogger(TestDerbyServer.class.getName()).log(Level.SEVERE, null, ex);
//            }
        }

    }

    public void SSS() {
//        Connection conn = null;
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");//加载驱动
            Connection conn = DriverManager.getConnection("jdbc:derby:derbyDB;create=true","user","pwd");
            Statement st = conn.createStatement();
//            st.execute("drop table FILES");
            ResultSet rs=st.executeQuery(" select  count(*)  from SYS.SYSTABLES where tablename='FILES' ");
            //注意derby数据库中的表明不存在小写，所以如果此处查询结果为小写那就查不到
            int k=0;
            while(rs.next())
            {
                if("0".equals(rs.getObject(1).toString()))
                    k=-1;
            }
            if(k==-1)
            {
                st.execute("create table FILES(UID VARCHAR(50) not null constraint FILES_PK primary key,FILESIZE INTEGER,FILETYPE VARCHAR(10),OLDNAME VARCHAR(20),PATH VARCHAR(255),CREATE_AT TIMESTAMP default CURRENT_TIMESTAMP not null,RBS VARCHAR(255),IS_DEL INTEGER)" );
                System.out.println("创建新表");
                k=0;
            }
//
////            rs=st.executeQuery(" select max(UID)  from FILES ");
////            while(rs.next())
////            {
////                if(null!=rs.getObject(1))
////                    k=Integer.parseInt(String.valueOf(rs.getObject(1)));
////            }
//////            st.execute("insert into FILES (UID,FILESIZE) values('"+(k+1)+"',1)");
//////            st.execute("insert into FILES (UID,FILESIZE) values('"+(k+2)+"',2)");
//            rs=st.executeQuery(" select * from FILES ");
//            while(rs.next())
//            {
//                System.out.println(rs.getObject(1)+"---->"+rs.getObject(2));
//            }
////             st.execute("drop table FILES");
//            rs.close();
        } catch (ClassNotFoundException | SQLException ex) {
           ex.printStackTrace();
        }finally{
//            try {
//                DriverManager.getConnection("jdbc:derby:;shutdown=true");
//            } catch (SQLException ex) {
//                System.out.println("关闭数据库！");
////                     Logger.getLogger(FileDao.class.getName()).log( null, ex);
//            }
        }

    }

}
