// Stores information about one patient.
public class Patient {

    // Patient details stored privately.
    private int id;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;

    // Creates a patient with their basic information.
    public Patient(int id, String firstName, String lastName,
                   String phone, String address) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.address = address;
    }

    // Returns the patient ID.
    public int getId() {
        return id;
    }

    // Returns the first name.
    public String getFirstName() {
        return firstName;
    }

    // Returns the last name.
    public String getLastName() {
        return lastName;
    }

    // Returns the phone number.
    public String getPhone() {
        return phone;
    }

    // Returns the address.
    public String getAddress() {
        return address;
    }

    // Updates the first name.
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // Updates the last name.
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // Updates the phone number.
    public void setPhone(String phone) {
        this.phone = phone;
    }

    // Updates the address.
    public void setAddress(String address) {
        this.address = address;
    }

    // Defines how a patient appears in lists.
    @Override
    public String toString() {
        return "Patient ID: " + id;
    }
}
