package top.itning.yunshunotesr.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 服务器响应
 *
 * @author itning
 */
public class ServerResponse implements Serializable {
    /**
     * 状态码
     */
    private int status;
    /**
     * 消息
     */
    private String msg;
    /**
     * 数据集合
     */
    private List<?> dataList;
    /**
     * 数据
     */
    private Object data;

    public ServerResponse() {
        this.status = 200;
        this.msg = "OK";
        this.data = "";
        this.dataList = new ArrayList<>();
    }

    public ServerResponse(int status, String msg) {
        this.status = status;
        this.msg = msg;
        this.data = "";
        this.dataList = new ArrayList<>();
    }

    public ServerResponse(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.dataList = new ArrayList<>();
        this.data = data;
    }

    public ServerResponse(List<?> dataList) {
        this.status = 200;
        this.msg = "OK";
        this.data = "";
        this.dataList = dataList;
    }

    public ServerResponse(int status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
        this.dataList = new ArrayList<>();
    }

    public ServerResponse(int status, String msg, List<?> dataList) {
        this.status = status;
        this.msg = msg;
        this.data = "";
        this.dataList = dataList;
    }

    public ServerResponse(int status, String msg, List<?> dataList, Object data) {
        this.status = status;
        this.msg = msg;
        this.dataList = dataList;
        this.data = data;
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

    public List<?> getDataList() {
        return dataList;
    }

    public void setDataList(List<?> dataList) {
        this.dataList = dataList;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ServerResponse{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", dataList=" + dataList +
                ", data=" + data +
                '}';
    }
}
