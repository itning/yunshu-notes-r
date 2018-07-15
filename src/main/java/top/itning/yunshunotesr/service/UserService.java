package top.itning.yunshunotesr.service;

import top.itning.yunshunotesr.entity.User;
import top.itning.yunshunotesr.exception.UserAlreadyExistsException;

import javax.mail.MessagingException;

/**
 * 用户服务接口
 *
 * @author itning
 */
public interface UserService {
    /**
     * 注册用户
     *
     * @param name     昵称
     * @param username 用户名
     * @param password 密码
     * @param code     验证码
     * @return 注册用户信息
     */
    User registeredUser(String name, String username, String password, String code);

    /**
     * 获取验证码
     *
     * @param email 邮箱
     * @throws MessagingException         MessagingException
     * @throws UserAlreadyExistsException 用户已经存在
     */
    void getCode(String email) throws MessagingException, UserAlreadyExistsException;
}
