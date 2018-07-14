package top.itning.yunshunotesr.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.itning.yunshunotesr.dao.HbaseRepository;
import top.itning.yunshunotesr.entity.Note;
import top.itning.yunshunotesr.service.NoteService;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

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

    @Autowired
    public HbaseNoteServiceImpl(HbaseRepository hbaseRepository) {
        this.hbaseRepository = hbaseRepository;
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
    public Note addNote(Note note) {
        try {
            return hbaseRepository.save(note);
        } catch (IOException e) {
            logger.info("save note error ", e);
            return null;
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
