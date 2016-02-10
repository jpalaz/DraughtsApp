package by.draughts.model.person;

import java.util.Date;

public class PersonMetadata {
    private String country;
    private String place;
    private Date birthday;

    public PersonMetadata() {
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
