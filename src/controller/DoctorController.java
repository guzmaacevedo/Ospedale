/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author domtr
 */

import packagee.Doctor;
import packagee.Specialty;
import response.Response;
import util.Validator;
import java.util.ArrayList;
import java.util.List;

public class DoctorController {

    private static final List<Doctor> doctors = new ArrayList<>();

    public Response registerDoctor(
            String id,
            String username,
            String firstname,
            String lastname,
            String password,
            String confirmPassword,
            Specialty specialty,
            String licenceNumber,
            String assignedOffice
    ) {

        if (!Validator.isValidUserId(id)) {
            return Response.error(400, "El ID debe tener 12 dígitos");
        }

        if (!Validator.passwordsMatch(password, confirmPassword)) {
            return Response.error(400, "Las contraseñas no coinciden");
        }

        if (!Validator.isValidLicense(licenceNumber)) {
            return Response.error(400, "Licencia inválida");
        }

        if (!Validator.isValidOffice(assignedOffice)) {
            return Response.error(400, "Oficina inválida");
        }

        for (Doctor doctor : doctors) {

            if (doctor.getId() == Long.parseLong(id)) {
                return Response.error(400, "Ya existe un doctor con ese ID");
            }

            if (doctor.getUsername().equals(username)) {
                return Response.error(400, "Nombre de usuario ya existe");
            }
        }

        Doctor doctor = new Doctor(
                Long.parseLong(id),
                username,
                firstname,
                lastname,
                password,
                specialty,
                licenceNumber,
                assignedOffice
        );

        doctors.add(doctor);

        return Response.ok("Doctor registrado correctamente", doctor);
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }
}
