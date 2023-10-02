package com.example.meeting_plannerbackend;

import com.example.meeting_plannerbackend.entities.MeetingRoom;
import com.example.meeting_plannerbackend.entities.Reservation;
import com.example.meeting_plannerbackend.enums.ReservationTypes;
import com.example.meeting_plannerbackend.repositories.MeetingRoomRepository;
import com.example.meeting_plannerbackend.services.MeetingRoomService;
import com.example.meeting_plannerbackend.services.ReservationService;
import com.example.meeting_plannerbackend.web.MeetingRoomRestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;
@WebMvcTest(MeetingRoomRestController.class)
@AutoConfigureMockMvc
public class MeetingRoomIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MeetingRoomRepository meetingRoomRepository;

    @Test
    public void testGetAllMeetingRooms() throws Exception {
        MeetingRoom meetingRoom1 = new MeetingRoom();
        meetingRoom1.setRoomName("E1001");
        meetingRoom1.setMeetingRoomId(1L);
        meetingRoom1.setCapacity(10);
        meetingRoom1.setEquipment(Arrays.asList("ecran", "tableau").toString());

        MeetingRoom meetingRoom2 = new MeetingRoom();
        meetingRoom2.setRoomName("E1002");
        meetingRoom2.setMeetingRoomId(2L);
        meetingRoom2.setCapacity(8);
        meetingRoom2.setEquipment(Arrays.asList("projecteur", "tableau").toString());

        List<MeetingRoom> meetingRooms = Arrays.asList(meetingRoom1, meetingRoom2);

        when(meetingRoomRepository.findAll()).thenReturn(meetingRooms);

        mockMvc.perform(MockMvcRequestBuilders.get("/meeting-rooms")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].roomName").value("E1001"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].meetingRoomId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].capacity").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].equipment").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].equipment[0]").value("ecran"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].equipment[1]").value("tableau"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].roomName").value("E1002"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].meetingRoomId").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].capacity").value(8))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].equipment").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].equipment[0]").value("projecteur"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].equipment[1]").value("tableau"));
    }


}
