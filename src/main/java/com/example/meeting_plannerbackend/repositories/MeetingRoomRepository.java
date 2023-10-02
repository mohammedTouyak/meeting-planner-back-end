package com.example.meeting_plannerbackend.repositories;

import com.example.meeting_plannerbackend.entities.MeetingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface MeetingRoomRepository extends JpaRepository<MeetingRoom,Long> {
//
}
