package top.itning.yunshunotesr.securtiy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;


/**
 * Session事件监听
 *
 * @author itning
 */
@WebListener
public class SessionListener implements HttpSessionListener {
    private static final Logger logger = LoggerFactory.getLogger(SessionListener.class);

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        logger.info("sessionCreated....");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        logger.info("sessionDestroyed....");
        SecurityUtils.deleteUser(se.getSession().getId());
    }
}
