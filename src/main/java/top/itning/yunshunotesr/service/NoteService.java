package top.itning.yunshunotesr.service;

import top.itning.yunshunotesr.entity.Note;

import java.util.List;
import java.util.Optional;

/**
 * 笔记服务层接口
 *
 * @author itning
 */
public interface NoteService {
    /**
     * 获取某笔记本下的所有笔记
     *
     * @param noteBookId 笔记本Id
     * @return 笔记集合
     */
    Optional<List<Note>> getAllNotes(String noteBookId);

    /**
     * 根基笔记Id获取笔记
     *
     * @param noteId 笔记Id
     * @return 笔记实体
     */
    Optional<Note> getNoteByNoteId(String noteId);

    /**
     * 添加笔记
     *
     * @param note 笔记实体
     * @return 被添加的笔记实体
     */
    Note addNote(Note note);

    /**
     * 根据笔记Id删除笔记
     *
     * @param id 笔记Id
     */
    void deleteByNoteId(String id);

    /**
     * 删除笔记本下的所有笔记
     *
     * @param noteBookId 笔记本Id
     */
    void deleteByNoteBookId(String noteBookId);
}
