package top.itning.yunshunotesr.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 笔记实体
 *
 * @author itning
 */
public class Note implements Serializable {
    /**
     * 笔记唯一标识ID
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
     * 是否为废纸篓中的笔记
     */
    private boolean trash;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 该笔记所属笔记本
     */
    private NoteBook noteBook;

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

    public boolean isTrash() {
        return trash;
    }

    public void setTrash(boolean trash) {
        this.trash = trash;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public NoteBook getNoteBook() {
        return noteBook;
    }

    public void setNoteBook(NoteBook noteBook) {
        this.noteBook = noteBook;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id='" + id + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", trash=" + trash +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", noteBook=" + noteBook +
                '}';
    }
}
