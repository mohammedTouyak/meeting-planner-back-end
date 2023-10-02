package com.example.meeting_plannerbackend.services;

import com.example.meeting_plannerbackend.Exception.MeetingRoomNotFoundException;
import com.example.meeting_plannerbackend.entities.MeetingRoom;
import com.example.meeting_plannerbackend.enums.ReservationTypes;

import java.time.LocalDateTime;
import java.util.List;

public interface MeetingRoomService {
    MeetingRoom saveMeetingRoom(MeetingRoom meetingRoom);
    List<MeetingRoom> listMeetingRoom();
    MeetingRoom getMeetingRoom(Long meetingRoomId) throws MeetingRoomNotFoundException;
    int capacityMax(Long meetingRoomId) throws MeetingRoomNotFoundException;


    boolean isRoomAvailable(LocalDateTime startTime, LocalDateTime endTime);

    List<MeetingRoom> findAvailableMeetingRooms(LocalDateTime startTime, LocalDateTime endTime);

    MeetingRoom findBestMeetingRoom(int requiredCapacity, LocalDateTime startTime, LocalDateTime endTime, ReservationTypes reservationType) throws MeetingRoomNotFoundException;
}
