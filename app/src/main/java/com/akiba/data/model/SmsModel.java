        package com.akiba.data.model;

        import java.util.List;
        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class SmsModel {

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("messages")
    @Expose
    private List<Message> messages = null;


    public SmsModel(String phone, List<Message> messages) {
        this.phone = phone;
        this.messages = messages;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

}