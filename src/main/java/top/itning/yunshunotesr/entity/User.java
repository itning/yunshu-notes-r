package top.itning.yunshunotesr.entity;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户实体
 *
 * @author itning
 */
@Entity(name = "user")
public class User implements Serializable{
    /**
     * 用户唯一标识ID
     */
    @Id
    @Column(name = "u_id", length = 36)
    @GeneratedValue(generator = "userIdGenerator")
    @GenericGenerator(name = "userIdGenerator", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    /**
     * 创建时间
     */
    @Column(name = "gmt_create", nullable = false)
    private Date gmtCreate;
    /**
     * 修改时间
     */
    @Column(name = "gmt_modified", nullable = false)
    private Date gmtModified;
    /**
     * 用户名
     */
    @Column(name = "username", nullable = false)
    private String username;
    /**
     * 密码
     */
    @Column(name = "password", nullable = false)
    private String password;
    /**
     * 昵称
     */
    @Column(name = "u_name", nullable = false)
    private String name;
    /**
     * 该用户下的所有笔记本
     */
    @Transient
    private List<NoteBook> noteBookList;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<NoteBook> getNoteBookList() {
        return noteBookList;
    }

    public void setNoteBookList(List<NoteBook> noteBookList) {
        this.noteBookList = noteBookList;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", noteBookList=" + noteBookList +
                '}';
    }
}
