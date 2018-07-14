package top.itning.yunshunotesr.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.itning.yunshunotesr.entity.ServerResponse;
import top.itning.yunshunotesr.securtiy.SecurityUtils;

/**
 * 用户控制层
 *
 * @author itning
 */
@RestController
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/getLoginUser")
    public ServerResponse getLoginUser() {
        return new ServerResponse(SecurityUtils.getUser());
    }
}
