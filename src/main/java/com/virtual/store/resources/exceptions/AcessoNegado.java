package com.virtual.store.resources.exceptions;

public class AcessoNegado extends StandardError {
    private String path;

    public AcessoNegado(Integer status, String msg, Long timeStamp, String path) {
        super(status, msg, timeStamp);
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
