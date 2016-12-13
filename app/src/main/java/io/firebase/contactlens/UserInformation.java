package io.firebase.contactlens;
//This is generated activity simply to handle the UserInformation function,
// it doesn't get displayed anywhere and doesn't take up a screen

public class UserInformation {

    public String name;
    public String email;
    public String number;
    public String facebook;
    public String twitter;
    public String linkedin;
    public String github;

    //Simply packages the given strings as an object so that they can all be handled by Firebase at once
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
