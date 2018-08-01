package com.oocl.parking.services;

import com.oocl.parking.dto.ParkinglotDto;
import com.oocl.parking.entities.Parkinglot;
import com.oocl.parking.repositories.ParkinglotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("parkinglotService")
public class ParkinglotService {

    private ParkinglotRepository parkinglotRepository;

    @Autowired
    public ParkinglotService(ParkinglotRepository parkinglotRepository){
        this.parkinglotRepository = parkinglotRepository;
    }


    public List<ParkinglotDto> getAllParkinglots(Pageable page, String status) {
        if(status != null)
            return parkinglotRepository.findByStatus(page, status).stream()
                    .map(ParkinglotDto::new).collect(Collectors.toList());

        return parkinglotRepository.findAll(page)
                .stream().map(ParkinglotDto::new).collect(Collectors.toList());
    }


    public ParkinglotDto save(Parkinglot parkinglot) {
        if(parkinglot.getId() != null)
            return null;
        parkinglotRepository.save(parkinglot);
        return new ParkinglotDto(parkinglot);
    }


    public ParkinglotDto getById(long id) {
        Parkinglot parkinglot = parkinglotRepository.findById(id).orElse(null);
        if(parkinglot == null){
            return null;
        }
        return new ParkinglotDto(parkinglot);
    }


    public boolean changeStatusById(Long id) {
        Parkinglot parkinglot = parkinglotRepository.findById(id).orElse(null);
        if(parkinglot == null || !parkinglot.isEmpty() || parkinglot.getUser() != null){
            return false;
        }
        String status = "";
        if(parkinglot.getStatus().equals("open")) {
            status = "logout";
//            parkinglot.setStatus("logout");
        }else{
            status = "open";
//            parkinglot.setStatus("open");
        }
        parkinglotRepository.changeStatus(id, status);//save(parkinglot);
        return true;
    }

    public boolean park(Long id) {
        Parkinglot parkinglot = parkinglotRepository.findById(id).orElse(null);
        if(parkinglot == null || parkinglot.isFull()){
            return false;
        }
        parkinglot.park();
        parkinglotRepository.save(parkinglot);
        return true;
    }

    public boolean unpark(Long id) {
        Parkinglot parkinglot = parkinglotRepository.findById(id).orElse(null);
        if(parkinglot == null || parkinglot.isEmpty()){
            return false;
        }
        parkinglot.unpark();
        parkinglotRepository.save(parkinglot);
        return true;
    }

    public List<ParkinglotDto> getDashboard(Pageable page, String status) {

        return parkinglotRepository.findByStatusAndUserNotNull(page, status).stream()
                .map(ParkinglotDto::new).collect(Collectors.toList());
    }

    public ParkinglotDto changeNameById(Long id, String name, int size) {
        Parkinglot parkinglot =parkinglotRepository.findById(id).orElse(null);
        if(parkinglot == null || parkinglot.getSize() > size){
            return null;
        }
        parkinglotRepository.changeNameAndSizeById(id, size, name);
        parkinglot = parkinglotRepository.findById(id).orElse(null);
        return new ParkinglotDto(parkinglot);
    }

    public List<ParkinglotDto> getNoUserParkinglots(Pageable page) {
        return parkinglotRepository.findAllByUserNull(page)
                .stream().map(ParkinglotDto::new).collect(Collectors.toList());
    }

    public List<ParkinglotDto> getPakinglotsCombineSearch(String name, String tel, int bt, int st) {
        return parkinglotRepository.findAllBySizeGreaterThan(bt)
                .stream().filter(parkinglot ->
                    (matchName(parkinglot, name) && matchTel(parkinglot, tel)) && parkinglot.getSize()<st
                ).map(ParkinglotDto::new).collect(Collectors.toList());
    }

    private boolean matchName(Parkinglot parkinglot, String name){
        return name == null || parkinglot.getName().equals(name);
    }
    private boolean matchTel(Parkinglot parkinglot, String tel){
        return tel == null || parkinglot.getUser().getPhone().equals(tel);
    }
}
