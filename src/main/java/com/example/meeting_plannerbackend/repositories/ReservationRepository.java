package com.example.meeting_plannerbackend.repositories;

import com.example.meeting_plannerbackend.entities.MeetingRoom;
import com.example.meeting_plannerbackend.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {

    //La condition r IS NULL  permet de sélectionner les MeetingRoom qui n'ont pas de réservation du tout.La condition (r.startTime > :endTime OR r.endTime < :startTime) Cela permet de sélectionner les réservations qui se chevauchent avec la période donnée.

//    @Query("SELECT r FROM Reservation r LEFT JOIN MeetingRoom m  ON r.meetingRoom.meetingRoomId = m.meetingRoomId WHERE (r.startTime > :endTime OR r.endTime < :startTime) OR m IS NULL")
//    List<MeetingRoom> findAvailableMeetingRooms(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    @Query("SELECT m FROM MeetingRoom m LEFT JOIN Reservation r ON m.meetingRoomId = r.meetingRoom.meetingRoomId WHERE r IS NULL OR r.endTime < :currentTimePlusOneHour")
    List<MeetingRoom> findAvailableMeetingRooms(@Param("currentTimePlusOneHour") LocalDateTime currentTimePlusOneHour);

    @Query("SELECT m.equipment FROM Reservation r LEFT JOIN r.meetingRoom m WHERE r.type = :type AND m.meetingRoomId = :meetingRoomId")
    List<String> findEquipmentMeetingRooms(@Param("type") String type, @Param("meetingRoomId") Long meetingRoomId);


}
