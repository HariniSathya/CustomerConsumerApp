package com.example.CustomerConsumerApp.entity;

public class ApiResponse<T>{

    private boolean success;
    private String message;
    private T data;

    // Getters and setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public  ApiResponse(boolean success,String message,T data){
        this.data =data;
        this.success=success;
        this.message=message;
    }

}
