package com.example.masterarbeit_weihele.Recycler.CommunicationRecycler;

public class Contact_Item {
    private String contact_name;
    private String contact_id;
    private String token;

    public Contact_Item(String contactName, String token) {

        this.contact_name = contactName;
        this.token = token;
    }

    public String getContact_name() {
        return contact_name;
    }
    public String getToken() {
        return token;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }
    public void setToken(String token) {
        this.token = token;
    }

    public String getContact_id() {
        return contact_id;
    }

    public void setContact_id(String contact_id) {
        this.contact_id = contact_id;
    }
}
