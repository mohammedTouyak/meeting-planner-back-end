package com.example.meeting_plannerbackend;

import com.example.meeting_plannerbackend.Exception.MeetingRoomNotFoundException;
import com.example.meeting_plannerbackend.entities.MeetingRoom;
import com.example.meeting_plannerbackend.entities.Reservation;
import com.example.meeting_plannerbackend.enums.ReservationTypes;
import com.example.meeting_plannerbackend.services.MeetingRoomService;
import com.example.meeting_plannerbackend.services.ReservationService;
import com.example.meeting_plannerbackend.web.MeetingRoomRestController;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MeetingRoomRestControllerTest {

    @MockBean
    private MeetingRoomService meetingRoomService;

    @Test
    void findAvailableMeetingRooms() throws MeetingRoomNotFoundException {
        LocalDateTime startTime = LocalDateTime.of(2023, 10, 6, 14, 46);
        LocalDateTime endTime = LocalDateTime.of(2023, 10, 6, 15, 46);
        int requiredCapacity = 3;
        ReservationTypes reservationType = ReservationTypes.SPEC;

        Reservation reservation = new Reservation();
        reservation.setPersonNumbre(requiredCapacity);
        reservation.setStartTime(startTime);
        reservation.setEndTime(endTime);
        reservation.setType(reservationType);

        MeetingRoom meetingRoom = new MeetingRoom();
        meetingRoom.setRoomName("E1004");
        meetingRoom.setMeetingRoomId(4L);
        meetingRoom.setCapacity(23);
        meetingRoom.setEquipment(Arrays.asList("ecran", "webcame", "pieuvre", "tableau").toString());

        Mockito.when(meetingRoomService.findBestMeetingRoom(requiredCapacity, startTime, endTime, reservationType))
                .thenReturn(meetingRoom);

        MeetingRoomRestController controller = new MeetingRoomRestController(meetingRoomService);
        MeetingRoom result = controller.findAvailableMeetingRooms(reservation);

        assertThat(result).isNotNull();
        assertThat(result.getRoomName()).isEqualTo("E1004");
        assertThat(result.getMeetingRoomId()).isEqualTo(4L);
        assertThat(result.getCapacity()).isEqualTo(23);
        assertThat(result.getEquipment()).contains("ecran", "webcame", "pieuvre", "tableau");
    }


}
