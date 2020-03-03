package entity;

import java.sql.Date;

/**
 * @author Ambi
 * @version 1.0
 * @date 2020/3/1 0:16
 */
public class MyFile {
    private String uid;
    private String filetype;
    private String oldname;
    private Date createAt;
    private String path;
    private String rbs;
    private Integer isDel;
    private Integer fileSize;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    public String getOldname() {
        return oldname;
    }

    public void setOldname(String oldname) {
        this.oldname = oldname;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getRbs() {
        return rbs;
    }

    public void setRbs(String rbs) {
        this.rbs = rbs;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Integer getFileSize() {
        return fileSize;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

    @Override
    public String toString() {
        return  "\"uid\":\"" + uid + '\"' +
                ",\"filetype\":\"" + filetype + '\"' +
                ",\"oldname\":\"" + oldname + '\"' +
                ",\"createAt\":\"" + createAt + '\"' +
                ",\"path\":\"" + path + '\"'  +
                ",\"rbs\":\"" + rbs + '\"'  +
                ",\"isDel\":\"" + isDel + '\"' +
                ",\"fileSize\":\"" + fileSize + '\"';
    }
}
