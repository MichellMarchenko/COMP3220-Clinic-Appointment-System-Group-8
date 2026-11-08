// Stores a doctor's ID, name, and specialty.
public class Doctor {

    // Doctor details.
    private int id;
    private String name;
    private String specialty;

    // Creates a doctor.
    public Doctor(int id, String name, String specialty) {
        this.id = id;
        this.name = name;
        this.specialty = specialty;
    }

    // Returns the doctor ID.
    public int getId() {
        return id;
    }

    // Returns the doctor name.
    public String getName() {
        return name;
    }

    // Returns the doctor specialty.
    public String getSpecialty() {
        return specialty;
    }
}
