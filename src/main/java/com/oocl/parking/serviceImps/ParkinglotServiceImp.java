package com.oocl.parking.serviceImps;

import com.oocl.parking.dto.ParkinglotDto;
import com.oocl.parking.entities.Parkinglot;
import com.oocl.parking.repositories.ParkinglotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("parkinglotService")
public class ParkinglotServiceImp implements ParkinglotService {

    private ParkinglotRepository parkinglotRepository;

    @Autowired
    public ParkinglotServiceImp(ParkinglotRepository parkinglotRepository){
        this.parkinglotRepository = parkinglotRepository;
    }

    @Override
    public List<ParkinglotDto> getAllParkinglots() {
        return parkinglotRepository.findAll()
            .stream().map(ParkinglotDto::new).collect(Collectors.toList());
    }

    @Override
    public Boolean save(Parkinglot parkinglot) {
        if(parkinglotRepository.findById(parkinglot.getId()).orElse(null)!=null)
            return false;
        parkinglotRepository.save(parkinglot);
        return true;
    }

    @Override
    public ParkinglotDto getById(long id) {
        Parkinglot parkinglot = parkinglotRepository.findById(id).orElse(null);
        if(parkinglot == null){
            return null;
        }
        return new ParkinglotDto(parkinglot);
    }
}
