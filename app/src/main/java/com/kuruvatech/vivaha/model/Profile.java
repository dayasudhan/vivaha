package com.kuruvatech.vivaha.model;

/**
 * Created by gagan on 3/3/2017.
 */
public class Profile {
    String id;
    String phone;
    String name;
    String email;
    String dob;
    int age;
    String gender;
    String community;
    String occupation;
    String education;
    String logo;
    String profileLogo;
    String origin;
    String summary;
    String interests;
    String mothertongue;
    String income;
    String gothra;
    String star;
    String rashi;
    float height;
    float weight;
    Address address;
    Parent father;
    Parent mother;

    public Profile()
    {
        id = new String("");
        phone = new String("");
        name = new String("");
        email = new String("");
        dob = new String("");
        int age =0;
        gender = new String("");
        community = new String("");
        occupation = new String("");
        education = new String("");
        logo = new String("");
        profileLogo = new String("");
        origin = new String("");
        summary = new String("");
        interests = new String("");
        mothertongue = new String("");
        income = new String("");
        gothra = new String("");
        star = new String("");
        rashi = new String("");
        height = 0;
        weight = 0 ;
        address = new Address();
        father = new Parent();
        mother = new Parent();
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getProfileLogo() {
        return profileLogo;
    }

    public void setProfileLogo(String profileLogo) {
        this.profileLogo = profileLogo;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public String getMothertongue() {
        return mothertongue;
    }

    public void setMothertongue(String mothertongue) {
        this.mothertongue = mothertongue;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getGothra() {
        return gothra;
    }

    public void setGothra(String gothra) {
        this.gothra = gothra;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getRashi() {
        return rashi;
    }

    public void setRashi(String rashi) {
        this.rashi = rashi;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Parent getFather() {
        return father;
    }

    public void setFather(Parent father) {
        this.father = father;
    }

    public Parent getMother() {
        return mother;
    }

    public void setMother(Parent mother) {
        this.mother = mother;
    }


}
