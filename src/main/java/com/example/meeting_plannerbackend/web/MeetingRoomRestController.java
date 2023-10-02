package com.example.meeting_plannerbackend.web;

import com.example.meeting_plannerbackend.Exception.MeetingRoomNotFoundException;
import com.example.meeting_plannerbackend.entities.MeetingRoom;
import com.example.meeting_plannerbackend.entities.Reservation;
import com.example.meeting_plannerbackend.enums.ReservationTypes;
import com.example.meeting_plannerbackend.services.MeetingRoomService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
public class MeetingRoomRestController {

    private MeetingRoomService meetirnRoomService;

    @GetMapping("/meeting-rooms")
    public List<MeetingRoom> MeetingRooms(){
        return meetirnRoomService.listMeetingRoom();
    }

    @GetMapping("/meetingRoomCapacity")
    public int getCapacity() throws MeetingRoomNotFoundException {
        return meetirnRoomService.capacityMax(1L);
    }

    @PostMapping ("/availableRoom")
    public MeetingRoom findAvailableMeetingRooms(@RequestBody Reservation reservation) throws MeetingRoomNotFoundException {
        LocalDateTime startTime = LocalDateTime.of(2023, 10, 6, 14, 46);
        LocalDateTime endTime = LocalDateTime.of(2023, 10, 6, 15, 46);
        int requiredCapacity = 3;
        ReservationTypes reservationType = ReservationTypes.SPEC;

        return meetirnRoomService.findBestMeetingRoom(reservation.getPersonNumbre(),reservation.getStartTime(),reservation.getEndTime(), reservation.getType());
    }

}
