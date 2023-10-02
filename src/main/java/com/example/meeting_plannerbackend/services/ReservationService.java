package com.example.meeting_plannerbackend.services;

import com.example.meeting_plannerbackend.entities.Reservation;

import java.util.List;

public interface ReservationService {
    Reservation saveReservation(Reservation reservation);
    Reservation getReservationById(Long reservationId);
    List<Reservation> ListReservation();
}
