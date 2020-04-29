package com.example.loginregistration.fbclass;

import java.text.SimpleDateFormat;

public class Transaction {
    private String time;
    private String type;    // (bank)    1: deposit 2: withdrawal
                            // (project) 3: invest 4: spend 5: stipend 6: mock-up growth

    private String senderId;
    private int senderType;  // 0: admin 1: user 2: business 3: bank
    private String senderName;

    private String receiverId;
    private int receiverType;  // 0: admin 1: user 2: business 3: bank
    private String receiverName;

    private String memo;

    public Transaction() {}

    public Transaction(String ti, String tp, Account s, Account r) {
        time = ti;
        type = tp;
        senderId = s.getId();
        senderType = s.getType();
        senderName = s.getName();
        receiverId = r.getId();
        receiverName = r.getName();
        receiverType = r.getType();
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public int getSenderType() {
        return senderType;
    }

    public void setSenderType(int senderType) {
        this.senderType = senderType;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public int getReceiverType() {
        return receiverType;
    }

    public void setReceiverType(int receiverType) {
        this.receiverType = receiverType;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}