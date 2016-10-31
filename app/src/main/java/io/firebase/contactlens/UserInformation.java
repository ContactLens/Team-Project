package io.firebase.contactlens;

/**
 * Created by jamie_000 on 16/10/2016.
 */

public class UserInformation {

    public String name;
    public String email;
    public String number;
    public String facebook;
    public String twitter;
    public String linkedin;
    public String github;

    public UserInformation(String name, String email, String number, String facebook, String twitter, String linkedin, String github) {
        this.email = email;
        this.facebook = facebook;
        this.github = github;
        this.linkedin = linkedin;
        this.name = name;
        this.number = number;
        this.twitter = twitter;
    }

}
