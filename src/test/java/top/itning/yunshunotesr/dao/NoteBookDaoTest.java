package top.itning.yunshunotesr.dao;

import com.google.common.collect.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.itning.yunshunotesr.entity.NoteBook;
import top.itning.yunshunotesr.entity.User;

import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NoteBookDaoTest {
    private static final Logger logger = LoggerFactory.getLogger(NoteBookDaoTest.class);

    @Autowired
    private NoteBookDao noteBookDao;

    @Autowired
    private UserDao userDao;

    @Before
    public void setUp() throws Exception {
        logger.info("setUp method invoke");
        User user = new User();
        user.setGmtCreate(new Date());
        user.setGmtModified(new Date());
        user.setUsername("test");
        user.setPassword("test");
        user.setName("test");
        user.setNoteBookList(Lists.newArrayList());
        userDao.saveAndFlush(user);
        if (noteBookDao.findAll().stream().noneMatch(noteBook -> noteBook.getName().equals("测试笔记本1"))) {
            NoteBook noteBook = new NoteBook();
            noteBook.setGmtCreate(new Date());
            noteBook.setGmtModified(new Date());
            noteBook.setName("测试笔记本1");
            userDao.findAll().forEach(noteBook::setUser);
            noteBook.setNoteList(Lists.newArrayList());
            noteBookDao.save(noteBook);
        }
    }

    @After
    public void tearDown() throws Exception {
        logger.info("tearDown method invoke");
        if (noteBookDao.findAll().stream().anyMatch(noteBook -> noteBook.getName().equals("测试笔记本1"))) {
            noteBookDao.findAll().stream().filter(noteBook -> noteBook.getName().equals("测试笔记本1")).forEach(noteBookDao::delete);
        }
        if (userDao.findAll().stream().anyMatch(user -> user.getName().equals("test"))) {
            userDao.findAll().stream().filter(user -> user.getName().equals("test")).forEach(userDao::delete);
        }
    }

    @Test
    public void testSave() {
        logger.info("testSave method invoke");
        NoteBook noteBook = new NoteBook();
        noteBook.setGmtCreate(new Date());
        noteBook.setGmtModified(new Date());
        noteBook.setName("测试笔记本1");
        userDao.findAll().forEach(noteBook::setUser);
        noteBook.setNoteList(Lists.newArrayList());
        assertNotNull(noteBookDao.save(noteBook));
    }

    @Test
    public void testModify() {
        logger.info("testModify method invoke");
        Optional<NoteBook> bookOptional = noteBookDao.findAll().stream().filter(noteBook -> noteBook.getName().equals("测试笔记本1")).findFirst();
        assertTrue(bookOptional.isPresent());
        NoteBook noteBook = bookOptional.get();
        Date date = new Date();
        noteBook.setGmtModified(date);
        assertNotNull(noteBookDao.save(noteBook));
    }

    @Test
    public void testDelete() {
        logger.info("testDelete method invoke");
        noteBookDao.findAll().stream().filter(noteBook -> noteBook.getName().equals("测试笔记本1")).forEach(noteBookDao::delete);
        assertSame(0L, noteBookDao.findAll().stream().filter(noteBook -> noteBook.getName().equals("测试笔记本1")).count());
    }
}