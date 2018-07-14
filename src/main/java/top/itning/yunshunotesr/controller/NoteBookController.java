package top.itning.yunshunotesr.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.itning.yunshunotesr.entity.ServerResponse;
import top.itning.yunshunotesr.exception.IncorrectParameterException;
import top.itning.yunshunotesr.exception.NoSuchIdException;
import top.itning.yunshunotesr.service.NoteBookService;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * 笔记本控制层
 *
 * @author itning
 */
@RestController
public class NoteBookController {
    private static final Logger logger = LoggerFactory.getLogger(NoteBookController.class);

    private final NoteBookService noteBookService;

    @Autowired
    public NoteBookController(NoteBookService noteBookService) {
        this.noteBookService = noteBookService;
    }

    /**
     * 获取所有笔记本信息集合
     *
     * @return 笔记本集合
     */
    @GetMapping("/note_books")
    public ServerResponse getAllNoteBooks() {
        return new ServerResponse(noteBookService.getAllNoteBook().orElse(new ArrayList<>()));
    }

    /**
     * 新建笔记本
     *
     * @param name     笔记本名
     * @param response {@link HttpServletResponse}
     */
    @PostMapping("/note_book")
    public void newNoteBook(String name, HttpServletResponse response) {
        try {
            noteBookService.addNoteBook(name);
            response.setStatus(201);
        } catch (IncorrectParameterException e) {
            logger.info("new note book exception " + e);
            response.setStatus(400);
        }
    }

    /**
     * 修改笔记本
     *
     * @param id       笔记本ID
     * @param name     新笔记本名
     * @param response {@link HttpServletResponse}
     */
    @PatchMapping("/note_book/{id}/{name}")
    public void upNoteBook(@PathVariable("id") String id, @PathVariable("name") String name, HttpServletResponse response) {
        try {
            noteBookService.modifyNoteBookName(id, name);
            response.setStatus(204);
        } catch (NoSuchIdException e) {
            logger.info("modify note book exception " + e);
            response.setStatus(404);
        } catch (IncorrectParameterException e) {
            logger.info("modify note book exception " + e);
            response.setStatus(400);
        }
    }

    /**
     * 删除笔记本
     *
     * @param id       笔记本ID
     * @param response {@link HttpServletResponse}
     */
    @DeleteMapping("/note_book/{id}")
    public void delNoteBook(@PathVariable("id") String id, HttpServletResponse response) {
        try {
            noteBookService.deleteNoteBookById(id);
            response.setStatus(204);
        } catch (NoSuchIdException e) {
            logger.info("delete note book exception " + e);
            response.setStatus(400);
        }
    }
}
