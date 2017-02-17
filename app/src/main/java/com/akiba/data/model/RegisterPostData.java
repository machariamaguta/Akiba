        package com.akiba.data.model;

        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class RegisterPostData {

    @SerializedName("test")
    @Expose
    private String test;
    @SerializedName("message")
    @Expose
    private String message;

    public String getSuccess() {
        return test;
    }

    public void setSuccess(String test) {
        this.test = test;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "RegisterPostData{" +
                "success=" + test +
                ", message='" + message + '\'' +
                '}';
    }

    public RegisterPostData(String success, String message){
        this.message=message;
        this.test=success;
    }
}