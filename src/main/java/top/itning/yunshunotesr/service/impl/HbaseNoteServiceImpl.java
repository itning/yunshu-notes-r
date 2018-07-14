package top.itning.yunshunotesr.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.itning.yunshunotesr.dao.HbaseRepository;
import top.itning.yunshunotesr.dao.NoteBookDao;
import top.itning.yunshunotesr.entity.Note;
import top.itning.yunshunotesr.entity.NoteBook;
import top.itning.yunshunotesr.exception.IncorrectParameterException;
import top.itning.yunshunotesr.exception.NoSuchIdException;
import top.itning.yunshunotesr.service.NoteService;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * 笔记服务层实现类(HBASE)
 *
 * @author itning
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class HbaseNoteServiceImpl implements NoteService {
    private static final Logger logger = LoggerFactory.getLogger(NoteBookServiceImpl.class);

    private final HbaseRepository hbaseRepository;

    private final NoteBookDao noteBookDao;

    @Autowired
    public HbaseNoteServiceImpl(HbaseRepository hbaseRepository, NoteBookDao noteBookDao) {
        this.hbaseRepository = hbaseRepository;
        this.noteBookDao = noteBookDao;
    }

    @Override
    public Optional<List<Note>> getAllNotes(String noteBookId) {
        try {
            return hbaseRepository.findAll(noteBookId);
        } catch (IOException e) {
            logger.info("get all notes error ", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Note> getNoteByNoteId(String noteId) {
        try {
            return hbaseRepository.findOne(noteId);
        } catch (IOException e) {
            logger.info("get note error ", e);
            return Optional.empty();
        }

    }

    @Override
    public Note addNote(String noteBookId, String title, String content) throws IncorrectParameterException, NoSuchIdException {
        if (StringUtils.isAnyBlank(noteBookId, title, content)) {
            logger.info("add note parameter exception");
            throw new IncorrectParameterException("参数不正确");
        }
        Note note = new Note();
        NoteBook noteBook = noteBookDao.findById(noteBookId).orElseThrow(() -> new NoSuchIdException("Id不存在"));
        //ID组成: 笔记本ID+笔记ID
        note.setId(noteBook.getId() + UUID.randomUUID().toString());
        note.setTitle(title);
        note.setContent(content);
        note.setGmtCreate(new Date());
        note.setGmtModified(new Date());
        note.setTrash(false);
        try {
            return hbaseRepository.save(note);
        } catch (IOException e) {
            logger.info("save note error ", e);
            return null;
        }
    }

    @Override
    public Note modifyNote(String id, String title, String content) throws IncorrectParameterException, NoSuchIdException {
        if (StringUtils.isAnyBlank(id, title, content)) {
            logger.info("add note parameter exception");
            throw new IncorrectParameterException("参数不正确");
        }
        try {
            Optional<Note> noteOptional = hbaseRepository.findOne(id);
            Note note = noteOptional.orElseThrow(() -> new NoSuchIdException("ID不存在"));
            note.setTitle(title);
            note.setContent(content);
            note.setGmtModified(new Date());
            return hbaseRepository.save(note);
        } catch (IOException e) {
            logger.info("modify note error ", e);
            throw new IncorrectParameterException("参数不正确");
        }
    }

    @Override
    public void deleteByNoteId(String id) {
        try {
            hbaseRepository.delete(id);
        } catch (IOException e) {
            logger.info("delete note error ", e);
        }
    }

    @Override
    public void deleteByNoteBookId(String noteBookId) {
        try {
            hbaseRepository.batchDelete(noteBookId);
        } catch (IOException e) {
            logger.info("batch delete notes error ", e);
        }
    }
}
