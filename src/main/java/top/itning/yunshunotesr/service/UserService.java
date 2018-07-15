package top.itning.yunshunotesr.service;

import top.itning.yunshunotesr.entity.User;
import top.itning.yunshunotesr.exception.IncorrectParameterException;
import top.itning.yunshunotesr.exception.NoSuchIdException;
import top.itning.yunshunotesr.exception.UserAlreadyExistsException;
import top.itning.yunshunotesr.exception.UserDoesNotExistException;

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

    /**
     * 获取重置密码验证码
     *
     * @param email 邮箱
     * @return 重置密钥
     * @throws MessagingException        MessagingException
     * @throws UserDoesNotExistException 用户不存在
     */
    Integer forgetPasswordGetCode(String email) throws MessagingException, UserDoesNotExistException;

    /**
     * 忘记密码
     *
     * @param code     验证码
     * @param vCode    密钥
     * @param password 新密码
     * @return 被重置密码的用户
     * @throws IncorrectParameterException 参数不正确
     * @throws UserDoesNotExistException   用户不存在
     */
    User forgetPassword(String code, String vCode, String password) throws IncorrectParameterException, UserDoesNotExistException;

    /**
     * 更改用户信息
     *
     * @param id       用户ID
     * @param name     用户名
     * @param password 密码
     * @return 被更改的用户实体
     * @throws NoSuchIdException ID不存在
     */
    User changeUserProfile(String id, String name, String password) throws NoSuchIdException;
}
