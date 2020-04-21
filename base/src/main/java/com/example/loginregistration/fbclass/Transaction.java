package com.example.loginregistration.fbclass;

import java.text.SimpleDateFormat;

public class Transaction {
    private String time;
    private int type;    // 1: deposit 2: withdrawal    (bank)
                                    // 3: invest 4 spend           (project)
    private String senderId;
    private int senderType;
    private String senderName;

    private String receiverId;
    private int receiverType;
    private String receiverName;

    private String memo;

    public Transaction(String t, int ty, Account s, Account r, String m) {
        time = t;
        type = ty;
        senderId = s.getId();
        senderType = s.getType();
        senderName = s.getName();
        receiverId = r.getId();
        receiverName = r.getName();
        receiverType = r.getType();
        memo = m;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int transactionType) {
        type = transactionType;
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