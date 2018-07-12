package top.itning.yunshunotesr.service;

import top.itning.yunshunotesr.entity.NoteBook;
import top.itning.yunshunotesr.exception.IncorrectParameterException;
import top.itning.yunshunotesr.exception.NoSuchIdException;

import java.util.List;
import java.util.Optional;

/**
 * 笔记本服务层接口
 *
 * @author itning
 */
public interface NoteBookService {
    /**
     * 获取登陆用户的所有笔记本集合
     *
     * @return 笔记本集合
     */
    Optional<List<NoteBook>> getAllNoteBook();

    /**
     * 根据笔记本ID删除指定笔记本
     *
     * @param id 笔记本ID
     * @throws NoSuchIdException 当指定的ID不存在时
     */
    void deleteNoteBookById(String id) throws NoSuchIdException;

    /**
     * 根据笔记本ID修改笔记本名称
     *
     * @param id   笔记本ID
     * @param name 新的笔记本名称
     * @return 被修改的实体
     * @throws NoSuchIdException           当指定的ID不存在时
     * @throws IncorrectParameterException 笔记本名称为null或空字符串时
     */
    NoteBook modifyNoteBookName(String id, String name) throws NoSuchIdException, IncorrectParameterException;

    /**
     * 新建笔记本
     *
     * @param name 笔记本名称
     * @return 被保存的实体
     * @throws IncorrectParameterException 笔记本名称为null或空字符串时
     */
    NoteBook addNoteBook(String name) throws IncorrectParameterException;
}
