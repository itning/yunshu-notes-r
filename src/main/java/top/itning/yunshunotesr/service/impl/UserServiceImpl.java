package top.itning.yunshunotesr.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.itning.yunshunotesr.dao.UserDao;
import top.itning.yunshunotesr.entity.User;
import top.itning.yunshunotesr.exception.IncorrectParameterException;
import top.itning.yunshunotesr.exception.UserAlreadyExistsException;
import top.itning.yunshunotesr.exception.UserDoesNotExistException;
import top.itning.yunshunotesr.service.UserService;
import top.itning.yunshunotesr.util.EmailUtils;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.util.*;

/**
 * 用户服务层实现类
 *
 * @author itning
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private static final String REG_SESSION_ATTRIBUTE = "code";

    private static final String FORGET_SESSION_ATTRIBUTE = "forgetCode";

    private static final String FORGET_SESSION_ATTRIBUTE_MAP_CODE = "code";

    private static final String FORGET_SESSION_ATTRIBUTE_MAP_VCODE = "vCode";

    private static final String FORGET_SESSION_ATTRIBUTE_MAP_USERNAME = "username";

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User registeredUser(String name, String username, String password, String code) {
        if ((int) ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest().getSession().getAttribute(REG_SESSION_ATTRIBUTE) == Integer.parseInt(code)) {
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
    public void getCode(String email) throws MessagingException, UserAlreadyExistsException {
        if (userDao.findByUsername(email) != null) {
            logger.info("user " + email + " already exists");
            throw new UserAlreadyExistsException("用户已经存在,重复注册");
        } else {
            int code = (int) ((Math.random() * 9 + 1) * 1000);
            ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest().getSession().setAttribute(REG_SESSION_ATTRIBUTE, code);
            EmailUtils.sendEmail(email, "云舒云笔记验证码邮件", "您的验证码是:" + code);
        }
    }

    @Override
    public Integer forgetPasswordGetCode(String email) throws MessagingException, UserDoesNotExistException {
        if (userDao.findByUsername(email) == null) {
            throw new UserDoesNotExistException("用户不存在");
        } else {
            int code = (int) ((Math.random() * 9 + 1) * 1000);
            int vCode = (int) ((Math.random() * 9 + 1) * 1000);
            Map<String, Object> map = new HashMap<>(3);
            map.put(FORGET_SESSION_ATTRIBUTE_MAP_CODE, code);
            map.put(FORGET_SESSION_ATTRIBUTE_MAP_VCODE, vCode);
            map.put(FORGET_SESSION_ATTRIBUTE_MAP_USERNAME, email);
            ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest().getSession().setAttribute(FORGET_SESSION_ATTRIBUTE, map);
            EmailUtils.sendEmail(email, "云舒云笔记重置密码验证码邮件", "您的验证码是:" + code);
            return vCode;
        }
    }

    @Override
    public User forgetPassword(String code, String vCode, String password) throws IncorrectParameterException, UserDoesNotExistException {
        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest().getSession().getAttribute(FORGET_SESSION_ATTRIBUTE);
        if (!map.get(FORGET_SESSION_ATTRIBUTE_MAP_CODE).toString().equals(code) || !map.get(FORGET_SESSION_ATTRIBUTE_MAP_VCODE).toString().equals(vCode)) {
            logger.info("Verification code or key error");
            throw new IncorrectParameterException("验证码或密钥错误");
        } else {
            String username = (String) map.get(FORGET_SESSION_ATTRIBUTE_MAP_USERNAME);
            User user = userDao.findByUsername(username);
            if (user == null) {
                throw new UserDoesNotExistException("用户不存在");
            }
            user.setPassword(password);
            return userDao.save(user);
        }
    }
}
