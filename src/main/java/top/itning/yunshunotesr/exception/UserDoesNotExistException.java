package top.itning.yunshunotesr.exception;

/**
 * 用户不存在
 *
 * @author itning
 */
public class UserDoesNotExistException extends Exception {
    public UserDoesNotExistException(String message) {
        super(message);
    }
}
