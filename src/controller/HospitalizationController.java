/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author domtr
 */

import packagee.Appointment;
import packagee.AppointmentStatus;
import packagee.Doctor;
import packagee.Hospitalization;
import packagee.HospitalizationStatus;
import packagee.Patient;
import packagee.RoomType;
import response.Response;
import util.Validator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HospitalizationController {

    private static final List<Hospitalization> hospitalizations = new ArrayList<>();

    public Response requestHospitalization(
            Patient patient,
            Doctor doctor,
            String date,
            String reason,
            RoomType roomType,
            String observations
    ) {

        if (patient == null) {
            return Response.error(400, "Paciente inválido");
        }

        if (doctor == null) {
            return Response.error(400, "Doctor inválido");
        }

        if (!Validator.isValidDate(date)) {
            return Response.error(400, "Fecha inválida");
        }

        String hospitalizationId = generateHospitalizationId(patient);

        Hospitalization hospitalization = new Hospitalization(
                hospitalizationId,
                patient,
                doctor,
                LocalDate.parse(date),
                reason,
                roomType,
                observations
        );

        hospitalizations.add(hospitalization);

        return Response.ok("Hospitalización solicitada correctamente", hospitalization);
    }

    public Response approveHospitalization(String hospitalizationId) {

        for (Hospitalization hospitalization : hospitalizations) {

            if (hospitalization.getId().equals(hospitalizationId)) {
                hospitalization.setStatus(HospitalizationStatus.ONGOING);
                return Response.ok("Hospitalización aprobada");
            }
        }

        return Response.error(404, "Hospitalización no encontrada");
    }

    public Response cancelHospitalization(String hospitalizationId) {

        for (Hospitalization hospitalization : hospitalizations) {

            if (hospitalization.getId().equals(hospitalizationId)) {
                hospitalization.setStatus(HospitalizationStatus.CANCELED);
                return Response.ok("Hospitalización cancelada");
            }
        }

        return Response.error(404, "Hospitalización no encontrada");
    }

    public Response sendToHospitalizationFromAppointment(
            Appointment appointment,
            Doctor doctor,
            String date,
            String reason,
            RoomType roomType,
            String observations
    ) {

        if (appointment == null) {
            return Response.error(400, "Cita inválida");
        }

        if (doctor == null) {
            return Response.error(400, "Doctor inválido");
        }

        if (!Validator.isValidDate(date)) {
            return Response.error(400, "Fecha inválida");
        }

        Patient patient = appointment.getPatient();

        String hospitalizationId = generateHospitalizationId(patient);

        Hospitalization hospitalization = new Hospitalization(
                hospitalizationId,
                patient,
                doctor,
                LocalDate.parse(date),
                reason,
                roomType,
                observations,
                HospitalizationStatus.ONGOING
        );

        appointment.setStatus(AppointmentStatus.COMPLETED);

        hospitalizations.add(hospitalization);

        return Response.ok("Paciente enviado a hospitalización", hospitalization);
    }

    private String generateHospitalizationId(Patient patient) {

        int count = 0;

        for (Hospitalization hospitalization : hospitalizations) {
            if (hospitalization.getId().contains(String.valueOf(patient.getId()))) {
                count++;
            }
        }

        return String.format("H-%d-%04d", patient.getId(), count);
    }

    public List<Hospitalization> getHospitalizations() {
        return hospitalizations;
    }
}