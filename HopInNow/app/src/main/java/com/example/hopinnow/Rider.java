package com.example.hopinnow;

class Rider extends User {
    private Request curRequest;
    Rider() {}
    Rider(String name, String password, String phoneNumber, String email) {
        super(name, password, phoneNumber, email);
    }

    public Request getCurRequest() {
        return curRequest;
    }

    public void setCurRequest(Request curRequest) {
        this.curRequest = curRequest;
    }
}
