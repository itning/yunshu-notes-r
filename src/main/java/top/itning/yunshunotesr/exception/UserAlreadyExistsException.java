package top.itning.yunshunotesr.exception;

/**
 * 用户已经存在
 *
 * @author itning
 */
public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
