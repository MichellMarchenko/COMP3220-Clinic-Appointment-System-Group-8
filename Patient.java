package org.example;

// AI-assisted code: ChatGPT helped implement this class
// based on the team's existing PD1 class structure.

public class Patient {

    private int id;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;

    public Patient(int id, String firstName, String lastName,
                   String phone, String address) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}