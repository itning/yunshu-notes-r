package top.itning.yunshunotesr.securtiy;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.context.request.RequestContextHolder;
import top.itning.yunshunotesr.entity.ServerResponse;
import top.itning.yunshunotesr.entity.User;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 安全工具
 *
 * @author itning
 */
public class SecurityUtils {
    /**
     * ObjectMapper
     */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    /**
     * 用于存储登陆用户
     */
    private static final Map<String, User> USER_MAP = new HashMap<>(16);

    private SecurityUtils() {
    }

    /**
     * 设置响应消息
     *
     * @param msg  消息
     * @param code 状态码
     * @param resp {@link HttpServletResponse}
     * @throws IOException IOException
     */
    static void setResponseMsg(String msg, int code, int status, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:8081");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS,DELETE,PUT");
        resp.setHeader("Access-Control-Allow-Headers", "*");
        resp.setStatus(code);
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");
        ServerResponse serverResponse = new ServerResponse(status, msg);
        String s = OBJECT_MAPPER.writeValueAsString(serverResponse);
        resp.getWriter().write(s);
    }

    static void setResponseMsg(String msg, Object data, int code, int status, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:8081");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS,DELETE,PUT");
        resp.setHeader("Access-Control-Allow-Headers", "*");
        resp.setStatus(code);
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");
        ServerResponse serverResponse = new ServerResponse(status, msg, data);
        String s = OBJECT_MAPPER.writeValueAsString(serverResponse);
        resp.getWriter().write(s);
    }

    /**
     * 将登陆用户实体存入MAP
     *
     * @param sessionId session id
     * @param user      用户实体
     */
    static void setUser(String sessionId, User user) {
        USER_MAP.put(sessionId, user);
    }

    /**
     * 在MAP中删除用户
     *
     * @param sessionId session id
     */
    static void deleteUser(String sessionId) {
        USER_MAP.remove(sessionId);
    }

    /**
     * 获取当前登录用户
     *
     * @return 用户实体
     */
    public static User getUser() {
        return USER_MAP.get(Objects.requireNonNull(RequestContextHolder.getRequestAttributes()).getSessionId());
    }
}
