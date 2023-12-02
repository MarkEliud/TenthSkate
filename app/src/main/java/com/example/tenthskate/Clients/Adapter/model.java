package com.example.tenthskate.Clients.Adapter;

public class model {
    String name,email,sender,user,receiver;

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public model(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getSemail() {
        return semail;
    }

    public void setSemail(String semail) {
        this.semail = semail;
    }

    String semail;
    public model(String sender, String user, String email, String receiver, String name, String semail) {
        this.sender = sender;
        this.user = user;
        this.email = email;
        this.receiver = receiver;
        this.name = name;
        this.semail = semail;
    }
    public model(String name) {
        this.name = name;
        //this.email = email;
    }
}
