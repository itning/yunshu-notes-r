package top.itning.yunshunotesr.securtiy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import top.itning.yunshunotesr.dao.UserDao;
import top.itning.yunshunotesr.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

/**
 * 安全过滤器
 *
 * @author itning
 */
@Component
@Order(1)
@WebFilter(filterName = "SecurityFilter", urlPatterns = "/*")
public class SecurityFilter implements Filter {
    private static final String LOGIN_URL = "/login";
    private static final String LOGOUT_URL = "/logout";
    private static final String REGISTERED_URL = "/registered";
    private static final String USERNAME_PARAMETER = "username";
    private static final String PASSWORD_PARAMETER = "password";
    private static final String LOGIN_METHOD = "POST";
    private static final String USER_SESSION_KEY = "user";

    private final UserDao userDao;

    @Autowired
    public SecurityFilter(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;
        if (LOGIN_URL.equals(req.getServletPath())) {
            if (!LOGIN_METHOD.equals(req.getMethod())) {
                SecurityUtils.setResponseMsg("不支持该方法", HttpStatus.METHOD_NOT_ALLOWED.value(), 405, resp);
                return;
            }
            String username = req.getParameter(USERNAME_PARAMETER);
            String password = req.getParameter(PASSWORD_PARAMETER);
            Optional<User> user = this.loadUserByUserName(username);
            if (!user.isPresent()) {
                SecurityUtils.setResponseMsg("用户不存在", HttpStatus.OK.value(), 404, resp);
            } else {
                User dbUser = user.get();
                if (dbUser.getPassword().equals(password)) {
                    HttpSession session = req.getSession();
                    session.setAttribute(USER_SESSION_KEY, dbUser);
                    SecurityUtils.setUser(session.getId(), dbUser);
                    SecurityUtils.setResponseMsg("登陆成功", dbUser, HttpStatus.OK.value(), 200, resp);
                } else {
                    SecurityUtils.setResponseMsg("密码错误", HttpStatus.OK.value(), 404, resp);
                }
            }
        } else if (LOGOUT_URL.equals(req.getServletPath())) {
            HttpSession session = req.getSession();
            SecurityUtils.deleteUser(session.getId());
            session.invalidate();
            SecurityUtils.setResponseMsg("注销成功", HttpStatus.OK.value(), 200, resp);
        } else if (REGISTERED_URL.equals(req.getServletPath())) {
            chain.doFilter(request, response);
        } else {
            //检查Session是否存在
            if (req.getSession().getAttribute(USER_SESSION_KEY) != null) {
                chain.doFilter(request, response);
            } else {
                SecurityUtils.setResponseMsg("请先登陆", HttpStatus.UNAUTHORIZED.value(), 401, resp);
            }
        }
    }

    @Override
    public void destroy() {

    }

    private Optional<User> loadUserByUserName(String username) {
        return Optional.ofNullable(userDao.findByUsername(username));
    }
}
