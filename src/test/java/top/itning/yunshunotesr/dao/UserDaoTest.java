package top.itning.yunshunotesr.dao;

import com.google.common.collect.Lists;

import java.util.Date;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.itning.yunshunotesr.entity.User;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTest {
    private static final Logger logger = LoggerFactory.getLogger(UserDaoTest.class);

    @Autowired
    private UserDao userDao;

    @Before
    public void setUp() throws Exception {
        logger.info("setUp method invoke");
        if (userDao.count() == 0) {
            User user = new User();
            user.setGmtCreate(new Date());
            user.setGmtModified(new Date());
            user.setUsername("test1");
            user.setPassword("test");
            user.setName("test");
            user.setNoteBookList(Lists.newArrayList());
            userDao.saveAndFlush(user);
        }
    }

    @After
    public void tearDown() throws Exception {
        if (userDao.findAll().stream().anyMatch(user1 -> user1.getName().equals("test"))) {
            userDao.findAll().stream().filter(user2 -> user2.getName().equals("test")).forEach(userDao::delete);
        }
        if (userDao.findAll().stream().anyMatch(user1 -> user1.getName().equals("test1"))) {
            userDao.findAll().stream().filter(user2 -> user2.getName().equals("test1")).forEach(userDao::delete);
        }
    }

    @Test
    public void testSave() {
        User user = new User();
        user.setGmtCreate(new Date());
        user.setGmtModified(new Date());
        user.setUsername("test");
        user.setPassword("test");
        user.setName("test1");
        user.setNoteBookList(Lists.newArrayList());
        assertNotNull(userDao.save(user));
        assertTrue(userDao.findAll().stream().anyMatch(user1 -> user1.getName().equals("test1")));
    }

    @Test
    public void testModify() {
        Optional<User> userOptional = userDao.findAll().stream().filter(user -> user.getName().equals("test")).findFirst();
        assertTrue(userOptional.isPresent());
        User user = userOptional.get();
        user.setPassword("test1");
        assertNotNull(userDao.save(user));
    }

    @Test
    public void testDelete() {
        userDao.findAll().stream().filter(user -> user.getName().equals("test")).forEach(userDao::delete);
        assertSame(0L, userDao.findAll().stream().filter(user -> user.getName().equals("test")).count());
    }
}