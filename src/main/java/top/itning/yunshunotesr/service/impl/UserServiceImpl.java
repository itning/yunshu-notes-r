package top.itning.yunshunotesr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.itning.yunshunotesr.dao.UserDao;
import top.itning.yunshunotesr.entity.User;
import top.itning.yunshunotesr.service.UserService;
import top.itning.yunshunotesr.util.EmailUtils;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * 用户服务层实现类
 *
 * @author itning
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User registeredUser(String name, String username, String password, String code) {
        if ((int) ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest().getSession().getAttribute("code") == Integer.parseInt(code)) {
            User user = new User();
            user.setId(UUID.randomUUID().toString());
            user.setGmtCreate(new Date());
            user.setGmtModified(new Date());
            user.setUsername(username);
            user.setPassword(password);
            user.setName(name);
            return userDao.save(user);
        } else {
            return null;
        }
    }

    @Override
    public void getCode(String email) throws MessagingException {
        int code = (int) ((Math.random() * 9 + 1) * 1000);
        ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest().getSession().setAttribute("code", code);
        EmailUtils.sendEmail(email, "云舒云笔记验证码邮件", "您的验证码是:" + code);
    }
}
