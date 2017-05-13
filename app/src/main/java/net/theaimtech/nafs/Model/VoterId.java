package net.theaimtech.nafs.Model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Aimtechpc5 on 5/13/2017.
 */

public class VoterId implements Parcelable
{
    String id,no,name,partNo,psNo,serialNo,edditorsAddress,surname,firstName,middelNAme,age,gender,birtDate,photoIdCardNo;

    public static VoterId parseJSON(String data)
    {
        VoterId voterId = new VoterId();
        try {
            JSONObject object = new JSONObject(data);
            //   driver.setDriverId(object.getString("driver_id"));
            voterId.setId(object.getString("ID"));
            voterId.setNo(object.getString("No"));
            voterId.setName(object.getString("Name"));
            voterId.setPartNo(object.getString("PartNo"));
            voterId.setSerialNo(object.getString("SerialNo"));
            voterId.setEdditorsAddress(object.getString("Elector's Address"));
            voterId.setSurname(object.getString("Surname"));
            voterId.setFirstName(object.getString("First Name"));
            voterId.setMiddelNAme(object.getString("Middle Name"));
            voterId.setAge(object.getString("Age/Gender"));
            voterId.setGender(object.getString("Age/Gender"));
            voterId.setBirtDate(object.getString("Birthdate"));
            voterId.setPhotoIdCardNo(object.getString("Photo Idcard No"));


            return voterId;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public VoterId()
    {
        super();
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPartNo() {
        return partNo;
    }

    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }

    public String getPsNo() {
        return psNo;
    }

    public void setPsNo(String psNo) {
        this.psNo = psNo;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getEdditorsAddress() {
        return edditorsAddress;
    }

    public void setEdditorsAddress(String edditorsAddress) {
        this.edditorsAddress = edditorsAddress;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddelNAme() {
        return middelNAme;
    }

    public void setMiddelNAme(String middelNAme) {
        this.middelNAme = middelNAme;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirtDate() {
        return birtDate;
    }

    public void setBirtDate(String birtDate) {
        this.birtDate = birtDate;
    }

    public String getPhotoIdCardNo() {
        return photoIdCardNo;
    }

    public void setPhotoIdCardNo(String photoIdCardNo) {
        this.photoIdCardNo = photoIdCardNo;
    }

    protected VoterId(Parcel in) {
        id = in.readString();
        no = in.readString();
        name = in.readString();
        partNo = in.readString();
        psNo = in.readString();
        serialNo = in.readString();
        edditorsAddress = in.readString();
        surname = in.readString();
        firstName = in.readString();
        middelNAme = in.readString();
        age = in.readString();
        gender = in.readString();
        birtDate = in.readString();
        photoIdCardNo = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(no);
        dest.writeString(name);
        dest.writeString(partNo);
        dest.writeString(psNo);
        dest.writeString(serialNo);
        dest.writeString(edditorsAddress);
        dest.writeString(surname);
        dest.writeString(firstName);
        dest.writeString(middelNAme);
        dest.writeString(age);
        dest.writeString(gender);
        dest.writeString(birtDate);
        dest.writeString(photoIdCardNo);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<VoterId> CREATOR = new Creator<VoterId>() {
        @Override
        public VoterId createFromParcel(Parcel in) {
            return new VoterId(in);
        }

        @Override
        public VoterId[] newArray(int size) {
            return new VoterId[size];
        }
    };
}
