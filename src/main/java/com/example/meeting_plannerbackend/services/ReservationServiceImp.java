package com.example.meeting_plannerbackend.services;

import com.example.meeting_plannerbackend.entities.MeetingRoom;
import com.example.meeting_plannerbackend.entities.Reservation;
import com.example.meeting_plannerbackend.repositories.ReservationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class ReservationServiceImp implements ReservationService{
    public ReservationRepository reservationRepository;
    @Override
    public Reservation saveReservation(Reservation reservation) {
        Reservation saveRese = reservationRepository.save(reservation);
        return saveRese;
    }

    @Override
    public Reservation getReservationById(Long reservationId) {
        return null;
    }

    @Override
    public List<Reservation> ListReservation() {
        return null;
    }
}
