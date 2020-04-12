package com.robod.entity;

/**
 * 返回给客户端的数据
 * @author 李迪
 * @date 2020/3/7 15:37
 */
public class ResultInfo<T> {

    private boolean flag;   //正常返回true,异常返回false
    private T data;    //返回给客户端的数据
    private String errorMsg;    //异常的信息

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        return "ResultInfo{" +
                "flag=" + flag +
                ", data=" + data +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }
}
