package top.itning.yunshunotesr.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 笔记本实体
 *
 * @author itning
 */
public class NoteBook implements Serializable {
    /**
     * 笔记本标识ID
     */
    private String id;
    /**
     * 创建时间
     */
    private Date gmtCreate;
    /**
     * 修改时间
     */
    private Date gmtModified;
    /**
     * 笔记本名称
     */
    private String name;
    /**
     * 该笔记本所属用户
     */
    private User user;
    /**
     * 该笔记本下所有笔记集合
     */
    private List<Note> noteList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Note> getNoteList() {
        return noteList;
    }

    public void setNoteList(List<Note> noteList) {
        this.noteList = noteList;
    }

    @Override
    public String toString() {
        return "NoteBook{" +
                "id='" + id + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", name='" + name + '\'' +
                ", user=" + user +
                ", noteList=" + noteList +
                '}';
    }
}
