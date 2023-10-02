package com.example.meeting_plannerbackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MeetingRoom")
@Data @NoArgsConstructor @AllArgsConstructor
public class MeetingRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_room_id")
    private Long meetingRoomId;

    @Column(name = "room_name", nullable = false)
    private String roomName;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    @Column(name = "equipment")
    private String equipment;




    // Getters and setters
}
