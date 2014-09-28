package com.lyfelink.linkup;

import android.net.Uri;

/**
 * Package: com.lyfelink.linkup, Project: LinkUp.
 * Created by Jan on 09.10.2014.
 */
public class Contact {

    private int id;
    private String name, company, jobTitle, email, phone, website, address;
    private Uri imageUri;

    //Constructor
    public Contact(int id, String name, String company, String jobTitle, String email, String phone, String website, String address, Uri imageUri) {
        this.id = id;
        this.name = name;
        this.company = company;
        this.jobTitle = jobTitle;
        this.email = email;
        this.phone = phone;
        this.website = website;
        this.address = address;
        this.imageUri = imageUri;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCompany() {
        return company;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getWebsite() {
        return website;
    }

    public String getAddress() {
        return address;
    }

    public Uri getImageUri() {
        return imageUri;
    }
}
