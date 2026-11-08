// AI-assisted code 
// Added matchesSlot() to check appointment conflicts.
import javax.annotation.processing.Generated;
import java.time.LocalDate;

// Connects a patient, doctor, date, and time in one booking.
public class Appointment {

    // Details of this appointment.
    private Patient patient;
    private Doctor doctor;
    private String date;
    private String time;

    // Creates an appointment.
    public Appointment(Patient patient, Doctor doctor,
                       String date, String time) {

        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
        this.time = time;
    }

    // Returns the booked patient.
    public Patient getPatient() {
        return patient;
    }

    // Returns the booked doctor.
    public Doctor getDoctor() {
        return doctor;
    }

    // Returns the appointment date.
    public String getDate() {
        return date;
    }

    // Returns the appointment time.
    public String getTime() {
        return time;
    }

    // Changes the booked doctor.
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    // Changes the appointment date.
    public void setDate(String date) {
        this.date = date;
    }

    // Changes the appointment time.
    public void setTime(String time) {
        this.time = time;
    }


    // Checks whether a requested doctor, date, and time match this booking.
    @Generated(value = "ChatGPT", date = "2026-11-08", comments = "AI-assisted appointment slot matching method")
    public boolean matchesSlot(Doctor requestedDoctor, LocalDate requestedDate,
                               String requestedTime) {
        return doctor.getId() == requestedDoctor.getId()
                && date.equals(requestedDate.toString())
                && time.equals(requestedTime);
    }

    // Defines how an appointment appears in the list.
    @Override
    public String toString() {
        return "Appointment for Patient ID: " + patient.getId()
                + " with " + doctor.getName()
                + " on " + date
                + " at " + time;
    }
}
