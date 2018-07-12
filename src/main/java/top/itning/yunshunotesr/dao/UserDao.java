package top.itning.yunshunotesr.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import top.itning.yunshunotesr.entity.User;

/**
 * User Dao interface
 *
 * @author itning
 */
public interface UserDao extends JpaRepository<User, String> {
    /**
     * 根据用户名查找用户
     *
     * @param username 用户名
     * @return 用户实体
     */
    User findByUsername(String username);
}
