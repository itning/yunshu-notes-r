package top.itning.yunshunotesr.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import top.itning.yunshunotesr.entity.NoteBook;

/**
 * NoteBook Dao interface
 *
 * @author itning
 */
public interface NoteBookDao extends JpaRepository<NoteBook, String> {
}
