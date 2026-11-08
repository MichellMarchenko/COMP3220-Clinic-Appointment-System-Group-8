import javax.annotation.processing.Generated;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;

// JavaFX application for creating patients and booking appointments.
public class Main extends Application {

    // Keeps track of patients, appointments, and current selections.
    private int nextPatientId = 1;

    private ArrayList<Patient> patients = new ArrayList<>();
    private ArrayList<Appointment> appointments = new ArrayList<>();

    private Appointment appointmentBeingEdited = null;
    private Patient selectedPatient = null;

    // Builds the main clinic booking window and its button actions.
    @Override
    @Generated(value = "ChatGPT", date = "2026-11-08", comments = "AI-assisted changes to displayed clinic hours and weekend validation within original UI method")
    public void start(Stage stage) {

        // Read-only fields display the selected patient.
        TextField firstName = new TextField();
        firstName.setPromptText("First Name");
        firstName.setEditable(false);

        TextField lastName = new TextField();
        lastName.setPromptText("Last Name");
        lastName.setEditable(false);

        TextField patientPhone = new TextField();
        patientPhone.setPromptText("Phone");
        patientPhone.setEditable(false);

        TextField patientAddress = new TextField();
        patientAddress.setPromptText("Address");
        patientAddress.setEditable(false);

        // Buttons for finding or adding a patient.
        Button patientProfileButton = new Button("View Patient Profiles");
        Button newPatientButton = new Button("Add New Patient");

        // Doctor dropdown with sample clinic doctors.
        ComboBox<Doctor> doctorChoice = new ComboBox<>();

        Doctor doctor1 = new Doctor(1, "Dr. Smith", "Family Medicine");
        Doctor doctor2 = new Doctor(2, "Dr. Brown", "Cardiology");
        Doctor doctor3 = new Doctor(3, "Dr. Lee", "Pediatrics");

        doctorChoice.getItems().addAll(
                doctor1,
                doctor2,
                doctor3
        );

        doctorChoice.setPromptText("Select Doctor");

        // Shows each doctor and their specialty in the dropdown.
        doctorChoice.setCellFactory(listView -> new ListCell<Doctor>() {
            @Override
            protected void updateItem(Doctor doctor, boolean empty) {
                super.updateItem(doctor, empty);

                if (empty || doctor == null) {
                    setText(null);
                } else {
                    setText(doctor.getName() + " - " + doctor.getSpecialty());
                }
            }
        });

        // Shows the selected doctor name after choosing.
        doctorChoice.setButtonCell(new ListCell<Doctor>() {
            @Override
            protected void updateItem(Doctor doctor, boolean empty) {
                super.updateItem(doctor, empty);

                if (empty || doctor == null) {
                    setText(null);
                } else {
                    setText(doctor.getName());
                }
            }
        });

        // Date and time controls for a booking.
        DatePicker datePicker = new DatePicker();

        Button timeButton = new Button("Select Time");
        Label selectedTimeLabel = new Label("No time selected");

        Label timeDescription = new Label(
                "Monday-Friday: 8 AM-4 PM | Saturday-Sunday: closed"
        );

        String[] selectedTime = {null};

        // Opens the list of existing patient profiles.
        patientProfileButton.setOnAction(e -> {

            if (patients.isEmpty()) {
                showError(
                        "There are no patient profiles yet. Please create a new patient."
                );
                return;
            }

            showPatientSelectionWindow(
                    stage,
                    firstName,
                    lastName,
                    patientPhone,
                    patientAddress
            );
        });

        // Opens the new-patient form.
        newPatientButton.setOnAction(e -> {

            showNewPatientWindow(
                    stage,
                    firstName,
                    lastName,
                    patientPhone,
                    patientAddress
            );
        });

        // Validates the date and doctor before showing time slots.
        timeButton.setOnAction(e -> {

            Doctor selectedDoctor = doctorChoice.getValue();
            LocalDate selectedDate = datePicker.getValue();

            if (selectedDoctor == null) {
                showError("Please select a doctor first.");
                return;
            }

            if (selectedDate == null) {
                showError("Please select a date first.");
                return;
            }

            if (selectedDate.isBefore(LocalDate.now())) {
                showError("Please select a current or future date.");
                return;
            }

            if (selectedDate.getDayOfWeek().getValue() >= 6) {
                showError("The clinic is closed on weekends.");
                return;
            }

            showTimeSelectionWindow(
                    stage,
                    selectedDoctor,
                    selectedDate,
                    selectedTime,
                    selectedTimeLabel
            );
        });

        // Clears the chosen time when the doctor changes.
        doctorChoice.setOnAction(e -> {
            selectedTime[0] = null;
            selectedTimeLabel.setText("No time selected");
        });

        // Resets time and shows clinic hours when the date changes.
        datePicker.valueProperty().addListener((obs, oldDate, newDate) -> {

            selectedTime[0] = null;
            selectedTimeLabel.setText("No time selected");

            if (newDate == null) {
                timeDescription.setText(
                        "Monday-Friday: 8 AM-4 PM | Saturday-Sunday: closed"
                );
                return;
            }

            if (newDate.getDayOfWeek().getValue() >= 6) {
                timeDescription.setText("Clinic is closed on weekends.");
            } else {
                timeDescription.setText("Monday-Friday: 8 AM-4 PM");
            }
        });

        // Lists booked appointments and provides booking/edit controls.
        ListView<Appointment> appointmentList = new ListView<>();

        Button bookButton = new Button("Book Appointment");

        Button editButton = new Button("Edit Appointment");

        Button saveButton = new Button("Save Changes");

        Button cancelButton = new Button("Cancel");

        saveButton.setVisible(false);
        saveButton.setManaged(false);

        cancelButton.setVisible(false);
        cancelButton.setManaged(false);

        // Validates the form and creates a booking if the slot is free.
        bookButton.setOnAction(e -> {

            if (selectedPatient == null) {
                showError(
                        "Please select an existing patient or create a new patient first."
                );
                return;
            }

            Doctor selectedDoctor = doctorChoice.getValue();
            LocalDate selectedDate = datePicker.getValue();
            String selectedTimeValue = selectedTime[0];

            if (selectedDoctor == null) {
                showError("Please select a doctor.");
                return;
            }

            if (selectedDate == null) {
                showError("Please select a date.");
                return;
            }

            if (selectedDate.isBefore(LocalDate.now())) {
                showError("Please select a current or future date.");
                return;
            }

            if (selectedDate.getDayOfWeek().getValue() >= 6) {
                showError("The clinic is closed on weekends.");
                return;
            }

            if (selectedTimeValue == null) {
                showError("Please select an appointment time.");
                return;
            }

            if (isTimeBooked(
                    selectedDoctor,
                    selectedDate,
                    selectedTimeValue)) {

                showError(
                        "This doctor is already booked at this date and time."
                );
                return;
            }

            Appointment appointment = new Appointment(
                    selectedPatient,
                    selectedDoctor,
                    selectedDate.toString(),
                    selectedTimeValue
            );

            appointments.add(appointment);
            appointmentList.getItems().add(appointment);

            showMessage(
                    "Appointment booked successfully for Patient ID "
                            + selectedPatient.getId() + "."
            );

            selectedPatient = null;

            firstName.clear();
            lastName.clear();
            patientPhone.clear();
            patientAddress.clear();

            doctorChoice.setValue(null);
            datePicker.setValue(null);

            selectedTime[0] = null;
            selectedTimeLabel.setText("No time selected");
        });

        // Loads a selected appointment for editing.
        editButton.setOnAction(e -> {

            Appointment selectedAppointment =
                    appointmentList.getSelectionModel().getSelectedItem();

            if (selectedAppointment == null) {
                showError("Please select an appointment to edit.");
                return;
            }

            appointmentBeingEdited = selectedAppointment;

            Patient patient = selectedAppointment.getPatient();

            selectedPatient = patient;

            firstName.setText(patient.getFirstName());
            lastName.setText(patient.getLastName());
            patientPhone.setText(patient.getPhone());
            patientAddress.setText(patient.getAddress());

            firstName.setEditable(false);
            lastName.setEditable(false);
            patientPhone.setEditable(false);
            patientAddress.setEditable(false);

            doctorChoice.setValue(selectedAppointment.getDoctor());

            datePicker.setValue(
                    LocalDate.parse(selectedAppointment.getDate())
            );

            selectedTime[0] = selectedAppointment.getTime();
            selectedTimeLabel.setText(selectedTime[0]);

            bookButton.setVisible(false);
            bookButton.setManaged(false);

            editButton.setVisible(false);
            editButton.setManaged(false);

            saveButton.setVisible(true);
            saveButton.setManaged(true);

            cancelButton.setVisible(true);
            cancelButton.setManaged(true);
        });

        // Validates and saves changes to the selected appointment.
        saveButton.setOnAction(e -> {

            if (appointmentBeingEdited == null) {
                showError(
                        "Please select an appointment to edit first."
                );
                return;
            }

            Doctor selectedDoctor = doctorChoice.getValue();
            LocalDate selectedDate = datePicker.getValue();
            String selectedTimeValue = selectedTime[0];

            if (selectedDoctor == null) {
                showError("Please select a doctor.");
                return;
            }

            if (selectedDate == null) {
                showError("Please select a date.");
                return;
            }

            if (selectedDate.isBefore(LocalDate.now())) {
                showError("Please select a current or future date.");
                return;
            }

            if (selectedDate.getDayOfWeek().getValue() >= 6) {
                showError("The clinic is closed on weekends.");
                return;
            }

            if (selectedTimeValue == null) {
                showError("Please select an appointment time.");
                return;
            }

            if (isTimeBookedByAnotherAppointment(
                    selectedDoctor,
                    selectedDate,
                    selectedTimeValue,
                    appointmentBeingEdited)) {

                showError(
                        "This doctor is already booked at this date and time."
                );
                return;
            }

            appointmentBeingEdited.setDoctor(selectedDoctor);
            appointmentBeingEdited.setDate(selectedDate.toString());
            appointmentBeingEdited.setTime(selectedTimeValue);

            appointmentList.refresh();

            showMessage("Appointment updated successfully.");

            appointmentBeingEdited = null;
            selectedPatient = null;

            firstName.clear();
            lastName.clear();
            patientPhone.clear();
            patientAddress.clear();

            doctorChoice.setValue(null);
            datePicker.setValue(null);

            selectedTime[0] = null;
            selectedTimeLabel.setText("No time selected");

            bookButton.setVisible(true);
            bookButton.setManaged(true);

            editButton.setVisible(true);
            editButton.setManaged(true);

            saveButton.setVisible(false);
            saveButton.setManaged(false);

            cancelButton.setVisible(false);
            cancelButton.setManaged(false);
        });

        // Exits appointment editing without saving.
        cancelButton.setOnAction(e -> {

            appointmentBeingEdited = null;
            selectedPatient = null;

            firstName.clear();
            lastName.clear();
            patientPhone.clear();
            patientAddress.clear();

            doctorChoice.setValue(null);
            datePicker.setValue(null);

            selectedTime[0] = null;
            selectedTimeLabel.setText("No time selected");

            bookButton.setVisible(true);
            bookButton.setManaged(true);

            editButton.setVisible(true);
            editButton.setManaged(true);

            saveButton.setVisible(false);
            saveButton.setManaged(false);

            cancelButton.setVisible(false);
            cancelButton.setManaged(false);
        });

        // Places the buttons and fields into the main window.
        GridPane patientButtons = new GridPane();
        patientButtons.setHgap(10);
        patientButtons.setAlignment(Pos.CENTER);
        patientButtons.add(patientProfileButton, 0, 0);
        patientButtons.add(newPatientButton, 1, 0);

        GridPane buttons = new GridPane();
        buttons.setHgap(10);
        buttons.setVgap(10);
        buttons.setAlignment(Pos.CENTER);

        buttons.add(bookButton, 0, 0);
        buttons.add(editButton, 1, 0);
        buttons.add(saveButton, 2, 0);
        buttons.add(cancelButton, 3, 0);

        VBox layout = new VBox(10);

        layout.setPadding(new Insets(15));
        layout.setAlignment(Pos.TOP_CENTER);

        layout.getChildren().addAll(
                new Label("Patient Profiles"),
                patientButtons,
                firstName,
                lastName,
                patientPhone,
                patientAddress,

                new Label("Doctor"),
                doctorChoice,

                new Label("Appointment Date"),
                datePicker,

                new Label("Appointment Time"),
                timeButton,
                selectedTimeLabel,
                timeDescription,

                buttons,

                new Label("Booked Appointments"),
                appointmentList
        );

        Scene scene = new Scene(layout, 500, 700);

        stage.setTitle("Clinic Appointment System");
        stage.setScene(scene);
        stage.show();
    }

    // Opens a form to register a new patient.
    private void showNewPatientWindow(
            Stage parentStage,
            TextField firstName,
            TextField lastName,
            TextField patientPhone,
            TextField patientAddress) {

        Stage patientStage = new Stage();

        patientStage.setTitle("New Patient");

        patientStage.initModality(Modality.WINDOW_MODAL);
        patientStage.initOwner(parentStage);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));

        TextField newFirstName = new TextField();
        newFirstName.setPromptText("First Name");

        TextField newLastName = new TextField();
        newLastName.setPromptText("Last Name");

        TextField newPhone = new TextField();
        newPhone.setPromptText("Phone");

        TextField newAddress = new TextField();
        newAddress.setPromptText("Address");

        Button savePatientButton = new Button("Save Patient");
        Button cancelButton = new Button("Cancel");

        // Checks the patient details and saves the new profile.
        savePatientButton.setOnAction(e -> {

            String firstNameText =
                    newFirstName.getText().trim();

            String lastNameText =
                    newLastName.getText().trim();

            String phoneText =
                    newPhone.getText().trim();

            String addressText =
                    newAddress.getText().trim();

            if (firstNameText.isEmpty()
                    || lastNameText.isEmpty()
                    || phoneText.isEmpty()
                    || addressText.isEmpty()) {

                showError(
                        "Please complete all patient information."
                );
                return;
            }

            if (!phoneText.matches("\\d{10}")) {

                showError(
                        "Phone number must contain exactly 10 digits."
                );
                return;
            }

            Patient patient = new Patient(
                    nextPatientId,
                    firstNameText,
                    lastNameText,
                    phoneText,
                    addressText
            );

            patients.add(patient);

            nextPatientId++;

            selectedPatient = patient;

            firstName.setText(patient.getFirstName());
            lastName.setText(patient.getLastName());
            patientPhone.setText(patient.getPhone());
            patientAddress.setText(patient.getAddress());

            showMessage(
                    "Patient created successfully. Patient ID: "
                            + patient.getId()
            );

            patientStage.close();
        });

        cancelButton.setOnAction(e -> {
            patientStage.close();
        });

        layout.getChildren().addAll(
                new Label("Create New Patient"),
                newFirstName,
                newLastName,
                newPhone,
                newAddress,
                savePatientButton,
                cancelButton
        );

        Scene scene = new Scene(layout, 350, 350);

        patientStage.setScene(scene);
        patientStage.showAndWait();
    }

    // Opens a window to select or edit an existing patient.
    private void showPatientSelectionWindow(
            Stage parentStage,
            TextField firstName,
            TextField lastName,
            TextField patientPhone,
            TextField patientAddress) {

        Stage patientStage = new Stage();

        patientStage.setTitle("Select Patient");

        patientStage.initModality(Modality.WINDOW_MODAL);
        patientStage.initOwner(parentStage);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));

        Label titleLabel =
                new Label("Select Existing Patient");

        ListView<Patient> patientList = new ListView<>();

        patientList.getItems().addAll(patients);

        Button selectButton =
                new Button("Select Patient");

        Button editPatientButton =
                new Button("Edit Patient Profile");

        Button cancelButton =
                new Button("Cancel");

        // Copies the chosen patient into the booking form.
        selectButton.setOnAction(e -> {

            Patient patient =
                    patientList.getSelectionModel().getSelectedItem();

            if (patient == null) {
                showError("Please select a patient.");
                return;
            }

            selectedPatient = patient;

            firstName.setText(patient.getFirstName());
            lastName.setText(patient.getLastName());
            patientPhone.setText(patient.getPhone());
            patientAddress.setText(patient.getAddress());

            patientStage.close();
        });

        // Opens the profile editor for the selected patient.
        editPatientButton.setOnAction(e -> {

            Patient patient =
                    patientList.getSelectionModel().getSelectedItem();

            if (patient == null) {
                showError("Please select a patient.");
                return;
            }

            showEditPatientWindow(
                    patientStage,
                    patient,
                    patientList
            );
        });

        cancelButton.setOnAction(e -> {
            patientStage.close();
        });

        GridPane patientButtons = new GridPane();

        patientButtons.setHgap(10);
        patientButtons.setAlignment(Pos.CENTER);

        patientButtons.add(selectButton, 0, 0);
        patientButtons.add(editPatientButton, 1, 0);
        patientButtons.add(cancelButton, 2, 0);

        layout.getChildren().addAll(
                titleLabel,
                patientList,
                patientButtons
        );

        Scene scene = new Scene(layout, 450, 400);

        patientStage.setScene(scene);
        patientStage.showAndWait();
    }

    // Opens a form to update a patient's profile.
    private void showEditPatientWindow(
            Stage parentStage,
            Patient patient,
            ListView<Patient> patientList) {

        Stage editPatientStage = new Stage();

        editPatientStage.setTitle("Edit Patient Profile");

        editPatientStage.initModality(Modality.WINDOW_MODAL);
        editPatientStage.initOwner(parentStage);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));

        TextField editFirstName =
                new TextField(patient.getFirstName());

        TextField editLastName =
                new TextField(patient.getLastName());

        TextField editPhone =
                new TextField(patient.getPhone());

        TextField editAddress =
                new TextField(patient.getAddress());

        Button saveButton =
                new Button("Save Changes");

        Button cancelButton =
                new Button("Cancel");

        saveButton.setOnAction(e -> {

            String firstNameText =
                    editFirstName.getText().trim();

            String lastNameText =
                    editLastName.getText().trim();

            String phoneText =
                    editPhone.getText().trim();

            String addressText =
                    editAddress.getText().trim();

            if (firstNameText.isEmpty()
                    || lastNameText.isEmpty()
                    || phoneText.isEmpty()
                    || addressText.isEmpty()) {

                showError(
                        "Please complete all patient information."
                );
                return;
            }

            if (!phoneText.matches("\\d{10}")) {

                showError(
                        "Phone number must contain exactly 10 digits."
                );

                return;
            }

            patient.setFirstName(firstNameText);
            patient.setLastName(lastNameText);
            patient.setPhone(phoneText);
            patient.setAddress(addressText);

            patientList.refresh();

            showMessage(
                    "Patient profile updated successfully."
            );

            editPatientStage.close();
        });

        cancelButton.setOnAction(e -> {
            editPatientStage.close();
        });

        layout.getChildren().addAll(
                new Label("Edit Patient Profile"),
                editFirstName,
                editLastName,
                editPhone,
                editAddress,
                saveButton,
                cancelButton
        );

        Scene scene =
                new Scene(layout, 350, 350);

        editPatientStage.setScene(scene);
        editPatientStage.showAndWait();
    }

    // Displays available half-hour slots and disables booked ones.
    private void showTimeSelectionWindow(
            Stage parentStage,
            Doctor doctor,
            LocalDate date,
            String[] selectedTime,
            Label selectedTimeLabel) {

        Stage timeStage = new Stage();

        timeStage.setTitle("Select Appointment Time");

        timeStage.initModality(Modality.WINDOW_MODAL);
        timeStage.initOwner(parentStage);

        GridPane grid = new GridPane();

        grid.setPadding(new Insets(15));
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);

        ArrayList<String> timeSlots =
                getTimeSlots(date);

        int column = 0;
        int row = 0;

        for (String time : timeSlots) {

            Button timeButton =
                    new Button(time);

            timeButton.setPrefWidth(100);

            if (isTimeBookedForSelection(
                    doctor,
                    date,
                    time)) {

                timeButton.setText(
                        time + " (Booked)"
                );

                timeButton.setDisable(true);

            } else {

                timeButton.setOnAction(e -> {

                    selectedTime[0] = time;

                    selectedTimeLabel.setText(time);

                    timeStage.close();
                });
            }

            grid.add(
                    timeButton,
                    column,
                    row
            );

            column++;

            if (column == 3) {
                column = 0;
                row++;
            }
        }

        Scene scene =
                new Scene(grid, 400, 300);

        timeStage.setScene(scene);
        timeStage.showAndWait();
    }


    // Builds half-hour time slots on weekdays; no weekend slots.
    @Generated(value = "ChatGPT", date = "2026-11-08", comments = "AI-assisted weekday 30-minute time slot generation")
    private ArrayList<String> getTimeSlots(LocalDate date) {
        ArrayList<String> timeSlots = new ArrayList<>();
        if (date.getDayOfWeek().getValue() >= 6) {
            return timeSlots;
        }
        for (int hour = 8; hour < 16; hour++) {
            timeSlots.add(formatTime(hour, 0));
            timeSlots.add(formatTime(hour, 30));
        }
        return timeSlots;
    }


    // Checks slots while selecting a time; ignores the booking being edited.
    @Generated(value = "ChatGPT", date = "2026-11-08", comments = "AI-assisted shared appointment conflict-checking wrapper")
    private boolean isTimeBookedForSelection(
            Doctor doctor, LocalDate date, String time) {
        return isTimeBookedByAnotherAppointment(
                doctor, date, time, appointmentBeingEdited);
    }

    // Checks whether a new booking would use an occupied slot.
    @Generated(value = "ChatGPT", date = "2026-11-08", comments = "AI-assisted shared appointment conflict-checking wrapper")
    private boolean isTimeBooked(
            Doctor doctor, LocalDate date, String time) {
        return isTimeBookedByAnotherAppointment(doctor, date, time, null);
    }

    // Finds conflicting bookings, optionally skipping one appointment.
    @Generated(value = "ChatGPT", date = "2026-11-08", comments = "AI-assisted shared conflict check excluding currently edited booking")
    private boolean isTimeBookedByAnotherAppointment(
            Doctor doctor, LocalDate date, String time,
            Appointment excludedAppointment) {
        for (Appointment appointment : appointments) {
            if (appointment != excludedAppointment
                    && appointment.matchesSlot(doctor, date, time)) {
                return true;
            }
        }
        return false;
    }

    // Converts 24-hour values into display times such as 9:30 AM.
    private String formatTime(
            int hour,
            int minute) {

        String period;

        if (hour >= 12) {
            period = "PM";
        } else {
            period = "AM";
        }

        int displayHour = hour;

        if (displayHour > 12) {
            displayHour -= 12;
        }

        return String.format(
                "%d:%02d %s",
                displayHour,
                minute,
                period
        );
    }

    // Shows an error popup.
    private void showError(String message) {

        Alert alert =
                new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }

    // Shows a success or information popup.
    private void showMessage(String message) {

        Alert alert =
                new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle(
                "Clinic Appointment System"
        );

        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }

    // Starts the JavaFX application.
    public static void main(String[] args) {
        launch(args);
    }
}
