/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author domtr
 */

import packagee.Administrator;
import packagee.Doctor;
import packagee.Patient;
import response.Response;

public class LoginController {

    private Administrator administrator;

    public LoginController() {

        administrator = new Administrator(
                1,
                "admin",
                "Admin",
                "Principal",
                "admin123"
        );
    }

    public Response login(
            String username,
            String password,
            PatientController patientController,
            DoctorController doctorController
    ) {

        if (
                administrator.getUsername().equals(username)
                && administrator.getPassword().equals(password)
        ) {

            return Response.ok("Login administrador", administrator);
        }

        for (Patient patient : patientController.getPatients()) {

            if (
                    patient.getUsername().equals(username)
                    && patient.getPassword().equals(password)
            ) {

                return Response.ok("Login paciente", patient);
            }
        }

        for (Doctor doctor : doctorController.getDoctors()) {

            if (
                    doctor.getUsername().equals(username)
                    && doctor.getPassword().equals(password)
            ) {

                return Response.ok("Login doctor", doctor);
            }
        }

        return Response.error(401, "Credenciales inválidas");
    }
}