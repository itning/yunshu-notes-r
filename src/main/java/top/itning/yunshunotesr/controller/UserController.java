package top.itning.yunshunotesr.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import top.itning.yunshunotesr.entity.ServerResponse;
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
        }
    }
}
