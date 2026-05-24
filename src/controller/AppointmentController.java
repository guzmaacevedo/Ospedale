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
import packagee.Patient;
import packagee.Specialty;
import response.Response;
import util.Validator;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentController {

    private static final List<Appointment> appointments = new ArrayList<>();

    public Response requestAppointment(
            Patient patient,
            Doctor doctor,
            Specialty specialty,
            String date,
            String time,
            String reason,
            boolean type
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

        if (!Validator.isValidTime(time)) {
            return Response.error(400, "Hora inválida");
        }

        LocalDate localDate = LocalDate.parse(date);
        LocalTime localTime = LocalTime.parse(time);

        LocalDateTime dateTime = LocalDateTime.of(localDate, localTime);

        for (Appointment appointment : appointments) {

            if (appointment.getDoctor().getId() == doctor.getId()
                    && appointment.getDatetime().equals(dateTime)
                    && appointment.getStatus() != AppointmentStatus.CANCELED) {

                return Response.error(
                        400,
                        "El doctor no tiene disponibilidad"
                );
            }
        }

        String appointmentId = generateAppointmentId(patient);

        Appointment appointment = new Appointment(
                appointmentId,
                patient,
                doctor,
                specialty,
                dateTime,
                reason,
                type
        );

        appointments.add(appointment);

        patient.addAppointment(appointment);

        return Response.ok(
                "Cita solicitada correctamente",
                appointment
        );
    }

    private String generateAppointmentId(Patient patient) {

        int count = 0;

        for (Appointment appointment : appointments) {

            if (appointment.getPatient().getId()
                    == patient.getId()) {

                count++;
            }
        }

        return String.format(
                "A-%d-%04d",
                patient.getId(),
                count
        );
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public Response acceptAppointment(String appointmentId) {

        for (Appointment appointment : appointments) {

            if (appointment.getId().equals(appointmentId)) {

                appointment.setStatus(AppointmentStatus.PENDING);

                return Response.ok("Cita aceptada");
            }
        }

        return Response.error(404, "Cita no encontrada");
    }

    public Response completeAppointment(String appointmentId) {

        for (Appointment appointment : appointments) {

            if (appointment.getId().equals(appointmentId)) {

                appointment.setStatus(AppointmentStatus.COMPLETED);

                return Response.ok("Cita completada");
            }
        }

        return Response.error(404, "Cita no encontrada");
    }

    public Response cancelAppointment(String appointmentId) {

        for (Appointment appointment : appointments) {

            if (appointment.getId().equals(appointmentId)) {

                if (appointment.getStatus()
                        == AppointmentStatus.COMPLETED) {

                    return Response.error(
                            400,
                            "No se puede cancelar una cita completada"
                    );
                }

                appointment.setStatus(AppointmentStatus.CANCELED);

                return Response.ok("Cita cancelada");
            }
        }

        return Response.error(404, "Cita no encontrada");
    }

    public Response rescheduleAppointment(
            String appointmentId,
            String newTime,
            String reason
    ) {

        if (!Validator.isValidTime(newTime)) {

            return Response.error(400, "Hora inválida");
        }

        for (Appointment appointment : appointments) {

            if (appointment.getId().equals(appointmentId)) {

                LocalDateTime currentDateTime
                        = appointment.getDatetime();

                LocalDateTime newDateTime
                        = LocalDateTime.of(
                                currentDateTime.toLocalDate(),
                                java.time.LocalTime.parse(newTime)
                        );

                appointment.setDatetime(newDateTime);

                appointment.setReason(
                        appointment.getReason()
                        + " | Reagendada: "
                        + reason
                );

                return Response.ok("Cita reagendada");
            }
        }

        return Response.error(404, "Cita no encontrada");
    }
}
