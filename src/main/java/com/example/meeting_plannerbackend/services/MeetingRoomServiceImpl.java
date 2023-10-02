package com.example.meeting_plannerbackend.services;

import com.example.meeting_plannerbackend.Exception.MeetingRoomNotFoundException;
import com.example.meeting_plannerbackend.entities.MeetingRoom;
import com.example.meeting_plannerbackend.enums.ReservationTypes;
import com.example.meeting_plannerbackend.repositories.MeetingRoomRepository;
import com.example.meeting_plannerbackend.repositories.ReservationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class MeetingRoomServiceImpl implements MeetingRoomService{
    private MeetingRoomRepository meetingRoomRepository;
    private ReservationRepository reservationRepository;



    @Override
    public MeetingRoom saveMeetingRoom(MeetingRoom meetingRoom) {
        log.info("Enregistrer salle ");
        MeetingRoom saveMeetingRom = meetingRoomRepository.save(meetingRoom);
        return saveMeetingRom;
    }

    @Override
    public List<MeetingRoom> listMeetingRoom() {
        return meetingRoomRepository.findAll();
    }

    @Override
    public MeetingRoom getMeetingRoom(Long meetingRoomId) throws MeetingRoomNotFoundException {
        MeetingRoom meetingRoom =  meetingRoomRepository.findById(meetingRoomId).
                orElseThrow(() -> new MeetingRoomNotFoundException("MeetingRoom not found ") );

        return meetingRoom;
    }

    @Override
    public int capacityMax(Long meetingRoomId) throws MeetingRoomNotFoundException {

        MeetingRoom meetingRoom =  meetingRoomRepository.findById(meetingRoomId).
                                                  orElseThrow(() -> new MeetingRoomNotFoundException("MeetingRoom not found from capacity ") );

        int MaxCapacity =(int) Math.floor(meetingRoom.getCapacity()*0.7) ;
        return MaxCapacity;
    }



    public boolean isRoomReserved(LocalDateTime startTime, LocalDateTime endTime) {
        // Vérifier si le jour de la réservation est un week-end
        if (startTime.getDayOfWeek() == DayOfWeek.SATURDAY || startTime.getDayOfWeek() == DayOfWeek.SUNDAY) {
            return false;
        }

        // Vérifier si l'heure de début est entre 8h et 20h
        if (startTime.getHour() < 8 || startTime.getHour() >= 20) {
            return false;
        }

        // Vérifier si l'heure de fin est entre 8h et 20h
        if (endTime.getHour() < 8 || endTime.getHour() >= 20) {
            return false;
        }

        // Vérifier si la durée de réservation est d'une heure
        if (Duration.between(startTime, endTime).toHours() != 1) {
            return false;
        }

        // Si toutes les conditions sont satisfaites, la salle est réservée
        return true;
    }

    @Override
    public boolean isRoomAvailable(LocalDateTime startTime, LocalDateTime endTime) {
        // Vérifier si la salle est déjà réservée pour le créneau horaire spécifié
        if (!isRoomReserved(startTime, endTime)) {
            return false;
        }
        // Vérifier si la salle est libre 1 heure avant sa prochaine réservation
        LocalDateTime nextReservationStartTime = startTime.plusHours(1);
        LocalDateTime previousReservationEndTime = endTime.minusHours(1);

        if (!isRoomReserved(nextReservationStartTime, nextReservationStartTime.plusHours(1)) ||
                !isRoomReserved(previousReservationEndTime.minusHours(1), previousReservationEndTime)) {
            return false;
        }

        // Si toutes les conditions sont satisfaites, la salle est disponible
        return true;
    }
    @Override
    public List<MeetingRoom> findAvailableMeetingRooms(LocalDateTime startTime, LocalDateTime endTime) {
        if (isRoomAvailable(startTime, endTime)) {
            LocalDateTime currentTimePlusOneHour = LocalDateTime.now().plusHours(1);
            List<MeetingRoom> availableMeetingRooms = reservationRepository.findAvailableMeetingRooms(currentTimePlusOneHour);

            if (availableMeetingRooms.isEmpty()) {

                return meetingRoomRepository.findAll();
            } else {
                return availableMeetingRooms;
            }
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public MeetingRoom findBestMeetingRoom(int requiredCapacity, LocalDateTime startTime, LocalDateTime endTime, ReservationTypes reservationType) throws MeetingRoomNotFoundException {

        List<MeetingRoom> availableMeetingRooms = findAvailableMeetingRooms(startTime, endTime);
        List<MeetingRoom> meetingRoomsFilteredByCapacity = availableMeetingRooms.stream()
                .filter(room -> {
                    try {
                        return capacityMax(room.getMeetingRoomId()) >= requiredCapacity;
                    } catch (MeetingRoomNotFoundException e) {
                        System.err.println("MeetingRoomNotFoundException: " + e.getMessage());
                        return false; // Ignorer la salle de réunion en cas d'exception
                    }
                })
                .collect(Collectors.toList());

        meetingRoomsFilteredByCapacity.sort(Comparator.comparingInt(MeetingRoom::getCapacity));

        for (MeetingRoom meetingRoom : meetingRoomsFilteredByCapacity) {
            if (hasRequiredEquipment(meetingRoom.getMeetingRoomId(),reservationType)) {
                return meetingRoom;
            }
        }

        return null;
    }


    public boolean  hasRequiredEquipment(Long meetingRoomId,ReservationTypes reservationType) throws MeetingRoomNotFoundException {

        MeetingRoom room = meetingRoomRepository.findById(meetingRoomId).orElseThrow(
                () ->  new MeetingRoomNotFoundException("Not found room")
        );

        if(reservationType.name().equals("SPEC") && room.getEquipment().contains("tableau"))
            return true;


      List<String> requiredEquipmentsVC = Arrays.asList("ecran", "webcame", "pieuvre");
      List<String> requiredEquipmentsRC = Arrays.asList("ecran", "tableau", "pieuvre");
      String equipment = room.getEquipment();

      boolean containsAllEquipments = true;
      if(reservationType.name().equals("VC")){
          //VC equipments
          for (String requiredEquipment : requiredEquipmentsVC) {
              if (!equipment.contains(requiredEquipment)) {
                  containsAllEquipments = false;
                  break;
              }
          }
          if (containsAllEquipments && reservationType.name().equals("VC"))
              return true;
      }

      if(reservationType.name().equals("RC")){
          //RC equipments
          for (String requiredEquipment : requiredEquipmentsRC) {
              if (!equipment.contains(requiredEquipment)) {
                  containsAllEquipments = false;
                  break;
              }
          }
          if (containsAllEquipments && reservationType.name().equals("RC")) {
              return true;
          }
      }


        return false;
  }



}
