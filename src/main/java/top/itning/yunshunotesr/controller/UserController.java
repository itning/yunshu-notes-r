package top.itning.yunshunotesr.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import top.itning.yunshunotesr.entity.ServerResponse;
import top.itning.yunshunotesr.exception.IncorrectParameterException;
import top.itning.yunshunotesr.exception.UserAlreadyExistsException;
import top.itning.yunshunotesr.exception.UserDoesNotExistException;
import top.itning.yunshunotesr.securtiy.SecurityUtils;
import top.itning.yunshunotesr.service.UserService;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户控制层
 *
 * @author itning
 */
@RestController
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 获取登录用户信息
     *
     * @return 用户信息
     */
    @GetMapping("/getLoginUser")
    public ServerResponse getLoginUser() {
        return new ServerResponse(SecurityUtils.getUser());
    }

    /**
     * 注册
     *
     * @return 注册消息
     */
    @PostMapping("/registered")
    public ServerResponse registered(String name, String username, String password, String code) {
        ServerResponse serverResponse = new ServerResponse();
        if (userService.registeredUser(name, username, password, code) == null) {
            serverResponse.setMsg("验证码不正确");
            serverResponse.setStatus(400);
        }
        return serverResponse;
    }

    /**
     * 获取验证码
     *
     * @param response {@link HttpServletResponse}
     * @param email    邮箱
     */
    @GetMapping("/get_code")
    public void getCode(HttpServletResponse response, String email) {
        try {
            userService.getCode(email);
            response.setStatus(202);
        } catch (MessagingException e) {
            logger.info("get code error ", e);
            response.setStatus(500);
        } catch (UserAlreadyExistsException e) {
            logger.info("get code error ", e);
            response.setStatus(406);
        }
    }

    /**
     * 获取忘记密码邮箱验证码
     *
     * @param email    邮箱
     * @param response {@link HttpServletResponse}
     * @return 密钥
     */
    @GetMapping("/forget_get_code")
    public Integer forgetPasswordGetCode(String email, HttpServletResponse response) {
        try {
            return userService.forgetPasswordGetCode(email);
        } catch (MessagingException e) {
            logger.info("get code error ", e);
            response.setStatus(500);
        } catch (UserDoesNotExistException e) {
            logger.info("get code error ", e);
            response.setStatus(404);
        }
        return -1;
    }

    /**
     * 忘记密码
     *
     * @param code     验证码
     * @param vCode    密钥
     * @param password 密码
     * @return 被重置的用户
     */
    @PostMapping("/forget_password")
    public ServerResponse forgetPassword(String code, String vCode, String password) {
        ServerResponse serverResponse = new ServerResponse();
        try {
            serverResponse.setData(userService.forgetPassword(code, vCode, password));
        } catch (IncorrectParameterException | UserDoesNotExistException e) {
            serverResponse.setStatus(404);
            serverResponse.setMsg(e.getMessage());
        }
        return serverResponse;
    }
}
