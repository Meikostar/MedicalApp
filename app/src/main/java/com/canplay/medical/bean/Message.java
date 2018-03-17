package com.canplay.medical.bean;

import java.io.Serializable;


public class Message implements Serializable{
    /* "pushId": "2",long
           "menuName": "加水",
           "tableNo": "5"
   * */
    private Long pushId;
    private Long businessId;
    private String tableNo;
    private String menuName;

    /*
     "pushId": 61,
                "content": "加水",
                "time": 1503546624198,
                "state": 1,
                "tableNo": "3"
    * */
    private String content;
    private long time;

    public Message() {
    }

    public Message(Long pushId, Long businessId, String tableNo, String menuName, String content, long time, int state) {
        this.pushId = pushId;
        this.businessId = businessId;
        this.tableNo = tableNo;
        this.menuName = menuName;
        this.content = content;
        this.time = time;
    }

    public Long getPushId() {
        return pushId;
    }

    public void setPushId(Long pushId) {
        this.pushId = pushId;
    }

    public String getTableNo() {
        return tableNo;
    }

    public void setTableNo(String tableNo) {
        this.tableNo = tableNo;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Message{" +
                "pushId=" + pushId +
                ", businessId=" + businessId +
                ", tableNo='" + tableNo + '\'' +
                ", menuName='" + menuName + '\'' +
                ", content='" + content + '\'' +
                ", time=" + time +
                '}';
    }
}
