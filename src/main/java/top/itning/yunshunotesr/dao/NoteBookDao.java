package top.itning.yunshunotesr.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import top.itning.yunshunotesr.entity.Note;
import top.itning.yunshunotesr.entity.NoteBook;
import top.itning.yunshunotesr.entity.User;

import java.util.List;

/**
 * NoteBook Dao interface
 *
 * @author itning
 */
public interface NoteBookDao extends JpaRepository<NoteBook, String> {
    /**
     * 查找用户的所有笔记本
     *
     * @param user 用户
     * @return 笔记本集合
     */
    List<NoteBook> findAllByUser(User user);
}
