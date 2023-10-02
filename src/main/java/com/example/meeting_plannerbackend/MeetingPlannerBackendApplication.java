package com.example.meeting_plannerbackend;

import com.example.meeting_plannerbackend.entities.MeetingRoom;
import com.example.meeting_plannerbackend.entities.Reservation;
import com.example.meeting_plannerbackend.enums.ReservationTypes;
import com.example.meeting_plannerbackend.repositories.MeetingRoomRepository;
import com.example.meeting_plannerbackend.repositories.ReservationRepository;
import com.example.meeting_plannerbackend.services.MeetingRoomService;
import com.example.meeting_plannerbackend.services.ReservationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Stream;

@SpringBootApplication
public class MeetingPlannerBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MeetingPlannerBackendApplication.class, args);
    }

        @Bean
    CommandLineRunner commandLineRunner(MeetingRoomService meetingRoomService, ReservationService reservationService){
        return arg -> {
            LocalDateTime startTime = LocalDateTime.of(2023, 9, 30, 10, 0);
            LocalDateTime endTime = LocalDateTime.of(2023, 9, 30, 11, 0);
            int requiredCapacity = 3;
            ReservationTypes reservationType = ReservationTypes.VC;

            Stream.of("E1001","E1002","E1003","E1004").forEach(name-> {
                MeetingRoom meetingRoom = new MeetingRoom();
                meetingRoom.setRoomName(name);

                if (name.equals("E1001")) {
                    meetingRoom.setCapacity(10);
                    meetingRoom.setEquipment(Arrays.asList("tableau", "projecteur").toString());
                } else {
                    if (name.equals("E1002")) {
                        meetingRoom.setCapacity(8);
                        meetingRoom.setEquipment(Arrays.asList("tableau", "ecran").toString());
                    } else {
                        if (name.equals("E1003")) {
                            meetingRoom.setCapacity(8);
                            meetingRoom.setEquipment("pieuvre");

                        } else {
                            meetingRoom.setCapacity(23);
                            meetingRoom.setEquipment(Arrays.asList("ecran", "webcame", "pieuvre","tableau").toString());

                        }
                    }
                }
                meetingRoomService.saveMeetingRoom(meetingRoom);

            });

            Stream.of(ReservationTypes.VC,ReservationTypes.RS,ReservationTypes.VC,ReservationTypes.SPEC).forEach(type->{
                MeetingRoom room = new MeetingRoom();
                room.setCapacity(12);
                room.setMeetingRoomId(3L);
                room.setRoomName("E2004");
                room.setEquipment(Arrays.asList("tableau", "ecran").toString());
                Reservation reservation = new Reservation();
                reservation.setType(type);
                if (type.name().equals("VC")) {
                    room.setMeetingRoomId(1L);
                    LocalDateTime datetime = LocalDateTime.now().withNano(123456).plusHours(2);
                    reservation.setPersonNumbre(8);
                    reservation.setStartTime(datetime);
                    reservation.setMeetingRoom(room);
                    reservation.setEndTime(datetime.plusHours(1));
                } else {
                    if (type.name().equals("RS")) {
                        room.setMeetingRoomId(3L);
                        LocalDateTime datetime = LocalDateTime.now().withNano(123456).plusHours(1);
                        reservation.setPersonNumbre(4);
                        reservation.setStartTime(datetime);
                        reservation.setEndTime(datetime.plusHours(1));
                        reservation.setMeetingRoom(room);

                    } else {
                        room.setMeetingRoomId(2L);
                        LocalDateTime datetime = LocalDateTime.now().withNano(123456).plusHours(3);
                        reservation.setPersonNumbre(6);
                        reservation.setStartTime(datetime);
                        reservation.setEndTime(datetime.plusHours(1));
                        reservation.setMeetingRoom(room);

                    }
                }
                reservationService.saveReservation(reservation);
            });

//            MeetingRoom room = meetingRoomService.findBestMeetingRoom(requiredCapacity,startTime,endTime,reservationType);
//            System.out.println("---------------------- swah : "+room.getRoomName());

        };
        }

//    @Bean
    CommandLineRunner start(MeetingRoomRepository meetingRoomRepo , ReservationRepository reservationRepo){
       return args -> {
           Stream.of("E1001","E1002","E1003","E1004").forEach(name->{
               MeetingRoom meetingRoom = new MeetingRoom();
               meetingRoom.setRoomName(name);

               if (name.equals("E1001")) {
                   meetingRoom.setCapacity(10);
                   meetingRoom.setEquipment(Arrays.asList("tableau", "projecteur").toString());
                       } else {
                           if (name.equals("E1002")) {
                               meetingRoom.setCapacity(8);
                               meetingRoom.setEquipment(Arrays.asList("tableau", "ecran").toString());
                           } else {
                               if (name.equals("E1003")) {
                                   meetingRoom.setCapacity(8);
                               } else {
                                   meetingRoom.setCapacity(4);
                               }
                           }
                       }

               if (name.equals("E1001")) {
                   meetingRoom.setCapacity(10);
                   meetingRoom.setEquipment(Arrays.asList("tableau", "projecteur").toString());
               } else {
                   if (name.equals("E1002")) {
                       meetingRoom.setEquipment("ecran");
                   } else {
                       if (name.equals("E1003")) {
                           meetingRoom.setEquipment("pieuvre");
                       } else {
                           meetingRoom.setEquipment("tableau");
                       }
                   }
               }

               meetingRoomRepo.save(meetingRoom);

           });

           Stream.of(ReservationTypes.VC,ReservationTypes.RS,ReservationTypes.VC,ReservationTypes.SPEC).forEach(type->{
               Reservation reservation = new Reservation();
               reservation.setType(type);
               if (type.name().equals("VC")) {
                   LocalDateTime datetime = LocalDateTime.now().withNano(123456);
                   reservation.setPersonNumbre(8);
                   reservation.setStartTime(datetime);
                   reservation.setEndTime(datetime.plusHours(1));
               } else {
                   if (type.name().equals("RS")) {
                       LocalDateTime datetime = LocalDateTime.now().withNano(123456).plusHours(1);
                       reservation.setPersonNumbre(4);
                       reservation.setStartTime(datetime);
                       reservation.setEndTime(datetime.plusHours(1));
                   } else {
                       LocalDateTime datetime = LocalDateTime.now().withNano(123456).plusHours(3);
                       reservation.setPersonNumbre(6);
                       reservation.setStartTime(datetime);
                       reservation.setEndTime(datetime.plusHours(1));
                   }
               }
               reservationRepo.save(reservation);
           });





       };




    }






}
