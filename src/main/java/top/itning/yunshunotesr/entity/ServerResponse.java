package top.itning.yunshunotesr.entity;

import java.io.Serializable;

/**
 * 服务器响应
 *
 * @author itning
 */
public class ServerResponse implements Serializable {
    private int status;
    private String msg;

    public ServerResponse() {
    }

    public ServerResponse(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
