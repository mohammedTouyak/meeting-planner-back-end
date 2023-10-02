package com.example.meeting_plannerbackend;

import com.example.meeting_plannerbackend.Exception.MeetingRoomNotFoundException;
import com.example.meeting_plannerbackend.entities.MeetingRoom;
import com.example.meeting_plannerbackend.enums.ReservationTypes;
import com.example.meeting_plannerbackend.repositories.MeetingRoomRepository;
import com.example.meeting_plannerbackend.repositories.ReservationRepository;
import com.example.meeting_plannerbackend.services.MeetingRoomService;
import com.example.meeting_plannerbackend.services.MeetingRoomServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class MeetingPlannerBackendApplicationTests {


    @Mock
    private MeetingRoomRepository meetingRoomRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private MeetingRoomServiceImpl meetingRoomService;



    @Test
    public void testFindBestMeetingRoom() throws MeetingRoomNotFoundException {
        // Création des données de test
        LocalDateTime startTime = LocalDateTime.of(2023, 10, 3, 10, 0);
        LocalDateTime endTime = LocalDateTime.of(2023, 10, 3, 11, 0);
        int requiredCapacity = 10;
        ReservationTypes reservationType = ReservationTypes.SPEC;

        MeetingRoom meetingRoom1 = new MeetingRoom();
        meetingRoom1.setRoomName("E1004");
        meetingRoom1.setMeetingRoomId(1L);
        meetingRoom1.setCapacity(15);
        meetingRoom1.setEquipment(Arrays.asList("ecran", "webcame", "pieuvre","tableau").toString());


        MeetingRoom meetingRoom2 = new MeetingRoom();
        meetingRoom2.setRoomName("E2004");
        meetingRoom2.setMeetingRoomId(2L);
        meetingRoom2.setCapacity(15);
        meetingRoom2.setEquipment(Arrays.asList("tableau", "projecteur").toString());
        List<MeetingRoom> availableMeetingRooms = Arrays.asList(meetingRoom1, meetingRoom2);


        Mockito.when(meetingRoomRepository.findAll()).thenReturn(Arrays.asList(meetingRoom1, meetingRoom2));
        Mockito.when(meetingRoomRepository.findById(1L)).thenReturn(Optional.of(meetingRoom1));
        Mockito.when(meetingRoomRepository.findById(2L)).thenReturn(Optional.of(meetingRoom2));
        Mockito.when(reservationRepository.findAvailableMeetingRooms(LocalDateTime.now().plusHours(1))).thenReturn(availableMeetingRooms);

//        Mockito.when(reservationRepository.findByMeetingRoomAndTimeRange(meetingRoom1, startTime, endTime)).thenReturn(Collections.emptyList());
//        Mockito.when(reservationRepository.findByMeetingRoomAndTimeRange(meetingRoom2, startTime, endTime)).thenReturn(Collections.emptyList());

        // Appel de la méthode à tester
        MeetingRoom bestMeetingRoom = meetingRoomService.findBestMeetingRoom(requiredCapacity, startTime, endTime, reservationType);

        // Vérification du résultat
        Assert.assertEquals(meetingRoom1, bestMeetingRoom);
    }


//    @Test
//    void contextLoads() throws MeetingRoomNotFoundException {
//        // Création des données de test
//        LocalDateTime startTime = LocalDateTime.of(2023, 10, 6, 14, 46);
//        LocalDateTime endTime = LocalDateTime.of(2023, 10, 6, 15, 46);
//        int requiredCapacity = 3;
//        ReservationTypes reservationType = ReservationTypes.SPEC;
//
//        MeetingRoom meetingRoom1 = new MeetingRoom();
//        meetingRoom1.setMeetingRoomId(1L);
//        meetingRoom1.setRoomName("E1001");
//        meetingRoom1.setCapacity(10);
//        meetingRoom1.setEquipment(Arrays.asList("tableau", "projecteur").toString());
////        meetingRoomRepository.save(meetingRoom1);
//
//        MeetingRoom meetingRoom2 = new MeetingRoom();
//        meetingRoom2.setMeetingRoomId(2L);
//        meetingRoom2.setRoomName("E1002");
//        meetingRoom2.setCapacity(8);
//        meetingRoom2.setEquipment(Arrays.asList("tableau", "ecran").toString());
//
//        List<MeetingRoom> availableMeetingRooms = Arrays.asList(meetingRoom1, meetingRoom2);
//
//        Mockito.when(meetingRoomRepository.findById(1L)).thenReturn(Optional.of(meetingRoom1));
//        Mockito.when(meetingRoomRepository.findById(2L)).thenReturn(Optional.of(meetingRoom2));
//
//
//
//        // Mock de la méthode findAvailableMeetingRooms du repository
//        Mockito.when(reservationRepository.findAvailableMeetingRooms(LocalDateTime.now().plusHours(1))).thenReturn(availableMeetingRooms);
//
//
//        // Appel de la méthode à tester
//        MeetingRoom bestMeetingRoom = meetingRoomService.findBestMeetingRoom(requiredCapacity, startTime, endTime, reservationType);
//
//        // Vérification du résultat
//        Assert.assertEquals(meetingRoom1, bestMeetingRoom);
//    }







}
