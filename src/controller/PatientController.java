/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author domtr
 */
import packagee.Patient;
import response.Response;
import util.Validator;
import java.util.ArrayList;
import java.util.List;

public class PatientController {

    private static final List<Patient> patients = new ArrayList<>();

    public Response registerPatient(
            String id,
            String name,
            String username,
            String password,
            String confirmPassword,
            String phone,
            String email,
            String birthDate
    ) {

        if (!Validator.isValidUserId(id)) {
            return Response.error(400, "El ID debe tener 12 dígitos");
        }

        if (!Validator.passwordsMatch(password, confirmPassword)) {
            return Response.error(400, "Las contraseñas no coinciden");
        }

        if (!Validator.isValidPhone(phone)) {
            return Response.error(400, "El teléfono debe tener 10 dígitos");
        }

        if (!Validator.isValidEmail(email)) {
            return Response.error(400, "Correo inválido");
        }

        if (!Validator.isValidDate(birthDate)) {
            return Response.error(400, "Fecha inválida");
        }

        for (Patient patient : patients) {

            if (patient.getId()== Long.parseLong(id)) {
                return Response.error(400, "Ya existe un paciente con ese ID");
            }

            if (patient.getUsername().equals(username)) {
                return Response.error(400, "Nombre de usuario ya existe");
            }
        }

        Patient patient = new Patient(
                Long.parseLong(id),
                username,
                name,
                "",
                password,
                email,
                java.time.LocalDate.parse(birthDate),
                true,
                Long.parseLong(phone),
                "Sin dirección"
        );

        patients.add(patient);

        return Response.ok("Paciente registrado correctamente", patient);
    }

    public List<Patient> getPatients() {
        return patients;
    }
}
