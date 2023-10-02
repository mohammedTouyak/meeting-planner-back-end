package com.example.meeting_plannerbackend.entities;

import com.example.meeting_plannerbackend.enums.ReservationStatus;
import com.example.meeting_plannerbackend.enums.ReservationTypes;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Reservation")
@Data @NoArgsConstructor @AllArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long reservationId;

    @Column(name = "person_number")
    private int personNumbre;

    @ManyToOne
    @JoinColumn(name = "meeting_room_id", nullable = true)
    private MeetingRoom meetingRoom;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ReservationStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "type ")
    private ReservationTypes type;

    // Getters and setters
}
