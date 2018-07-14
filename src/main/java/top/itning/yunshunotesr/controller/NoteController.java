package top.itning.yunshunotesr.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.itning.yunshunotesr.entity.Note;
import top.itning.yunshunotesr.entity.ServerResponse;
import top.itning.yunshunotesr.exception.IncorrectParameterException;
import top.itning.yunshunotesr.exception.NoSuchIdException;
import top.itning.yunshunotesr.service.NoteService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

/**
 * 笔记控制层
 *
 * @author itning
 */
@RestController
public class NoteController {
    private static final Logger logger = LoggerFactory.getLogger(NoteController.class);

    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    /**
     * 获取某笔记本下的所有笔记
     *
     * @param id 笔记本ID
     * @return 笔记集合
     */
    @GetMapping("/notes/{id}")
    public ServerResponse getAllNotes(@PathVariable("id") String id) {
        logger.debug("get all notes by note book id is " + id);
        ServerResponse serverResponse = new ServerResponse();
        Optional<List<Note>> optionalNoteList = noteService.getAllNotes(id);
        if (optionalNoteList.isPresent()) {
            serverResponse.setDataList(optionalNoteList.get());
        } else {
            serverResponse.setStatus(404);
            serverResponse.setMsg("未找到");
        }
        return serverResponse;
    }

    /**
     * 获取笔记
     *
     * @param id 笔记Id
     * @return 笔记信息
     */
    @GetMapping("/note/{id}")
    public ServerResponse getNote(@PathVariable("id") String id) {
        logger.debug("get note id is " + id);
        ServerResponse serverResponse = new ServerResponse();
        Optional<Note> noteOptional = noteService.getNoteByNoteId(id);
        if (noteOptional.isPresent()) {
            serverResponse.setData(noteOptional.get());
        } else {
            serverResponse.setStatus(404);
            serverResponse.setMsg("未找到");
        }
        return serverResponse;
    }

    /**
     * 删除笔记
     *
     * @param id 笔记ID
     */
    @DeleteMapping("/note/{id}")
    public void deleteNote(@PathVariable("id") String id) {
        logger.debug("delete note id is " + id);
        noteService.deleteByNoteBookId(id);
    }

    /**
     * 添加笔记
     *
     * @param noteBookId 笔记本ID
     * @param title      标题
     * @param content    内容
     * @param response   {@link HttpServletResponse}
     */
    @PostMapping("/note")
    public void addNote(String noteBookId, String title, String content, HttpServletResponse response) {
        try {
            if (noteService.addNote(noteBookId, title, content) == null) {
                logger.info("add note id error add result null");
                response.setStatus(500);
            }
        } catch (IncorrectParameterException e) {
            logger.info("add note id error ", e);
            response.setStatus(400);
        } catch (NoSuchIdException e) {
            logger.info("add note id error ", e);
            response.setStatus(404);
        }
    }

    /**
     * 修改笔记
     *
     * @param id       笔记ID
     * @param title    笔记标题
     * @param content  笔记内容
     * @param response {@link HttpServletResponse}
     */
    @PatchMapping("/note/{id}/{title}/{content}")
    public void upNote(@PathVariable("id") String id, @PathVariable("title") String title, @PathVariable("content") String content, HttpServletResponse response) {
        try {
            if (noteService.modifyNote(id, title, content) == null) {
                logger.info("up note error result is null");
                response.setStatus(500);
            }
        } catch (IncorrectParameterException e) {
            logger.info("up note error ", e);
            response.setStatus(400);
        } catch (NoSuchIdException e) {
            logger.info("up note error ", e);
            response.setStatus(404);
        }
    }
}
