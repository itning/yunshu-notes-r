package top.itning.yunshunotesr.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.itning.yunshunotesr.dao.NoteBookDao;
import top.itning.yunshunotesr.entity.NoteBook;
import top.itning.yunshunotesr.exception.IncorrectParameterException;
import top.itning.yunshunotesr.exception.NoSuchIdException;
import top.itning.yunshunotesr.service.NoteBookService;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 笔记本服务层实现类
 *
 * @author itning
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class NoteBookServiceImpl implements NoteBookService {
    private static final Logger logger = LoggerFactory.getLogger(NoteBookServiceImpl.class);

    private final NoteBookDao noteBookDao;

    @Autowired
    public NoteBookServiceImpl(NoteBookDao noteBookDao) {
        this.noteBookDao = noteBookDao;
    }

    @Override
    public Optional<List<NoteBook>> getAllNoteBook() {
        return Optional.of(noteBookDao.findAll());
    }

    @Override
    public void deleteNoteBookById(String id) throws NoSuchIdException {
        if (!noteBookDao.existsById(id)) {
            logger.info("the note book id %s does not exist", id);
            throw new NoSuchIdException("笔记本Id:" + id + " 不存在");
        }
        noteBookDao.deleteById(id);
    }

    @Override
    public NoteBook modifyNoteBookName(String id, String name) throws NoSuchIdException, IncorrectParameterException {
        if (StringUtils.isBlank(name)) {
            throw new IncorrectParameterException("参数不正确:" + name);
        }
        NoteBook noteBook = noteBookDao.findById(id).orElseThrow(() -> new NoSuchIdException("笔记本Id:" + id + " 不存在"));
        noteBook.setName(name);
        return noteBookDao.save(noteBook);
    }

    @Override
    public NoteBook addNoteBook(String name) throws IncorrectParameterException {
        if (StringUtils.isBlank(name)) {
            throw new IncorrectParameterException("参数不正确:" + name);
        }
       // User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        NoteBook noteBook = new NoteBook();
        noteBook.setGmtCreate(new Date());
        noteBook.setGmtModified(new Date());
        noteBook.setName(name);
       // noteBook.setUser(user);
        return noteBookDao.save(noteBook);
    }
}
