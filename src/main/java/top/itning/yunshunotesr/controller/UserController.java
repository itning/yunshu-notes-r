package top.itning.yunshunotesr.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.itning.yunshunotesr.entity.User;
import top.itning.yunshunotesr.securtiy.SecurityUtils;

/**
 * 用户控制层
 *
 * @author itning
 */
@RestController
public class UserController {
    @GetMapping("/getLoginUser")
    public User getLoginUser() {
        return SecurityUtils.getUser();
    }
}
