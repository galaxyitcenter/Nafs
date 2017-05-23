package net.theaimtech.nafs.pojo;

import java.io.Serializable;

/**
 * Created by Aimanzaki Kagzi on 10/05/2017.
 */

public class User implements Serializable{
    String username;
    String address;
    String id;
    String noOfSurvay;

    public String getNoOfSurvay() {
        return noOfSurvay;
    }

    public void setNoOfSurvay(String noOfSurvay) {
        this.noOfSurvay = noOfSurvay;
    }

    @Override
    public String toString() {
        return "{" +
                "username='" + username + '\'' +
                ", address='" + address + '\'' +
                ", id='" + id + '\'' +
                ", id_token='" + id_token + '\'' +
                ", noOfSurvay='"+noOfSurvay+'\''+
                '}';
    }

    String id_token;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_token() {
        return id_token;
    }

    public void setId_token(String id_token) {
        this.id_token = id_token;
    }
}
