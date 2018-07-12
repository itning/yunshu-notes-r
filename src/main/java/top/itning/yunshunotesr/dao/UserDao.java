package top.itning.yunshunotesr.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import top.itning.yunshunotesr.entity.User;

/**
 * User Dao interface
 *
 * @author itning
 */
public interface UserDao extends JpaRepository<User, String> {
}
