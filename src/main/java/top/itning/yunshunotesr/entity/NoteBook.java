package top.itning.yunshunotesr.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 笔记本实体
 *
 * @author itning
 */
@Entity(name = "note_book")
public class NoteBook implements Serializable {
    /**
     * 笔记本标识ID
     */
    @Id
    @Column(name = "b_id", length = 36)
    @GeneratedValue(generator = "noteBookIdGenerator")
    @GenericGenerator(name = "noteBookIdGenerator", strategy = "org.hibernate.id.UUIDGenerator")
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
     * 笔记本名称
     */
    @Column(name = "b_name", nullable = false)
    private String name;
    /**
     * 该笔记本所属用户
     */
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "u_id", referencedColumnName = "u_id", nullable = false)
    private User user;
    /**
     * 该笔记本下所有笔记集合
     */
    @Transient
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
