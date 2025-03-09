package com.capybara.services;

import com.capybara.dto.UnitDto;
import com.capybara.entities.Unit;
import com.capybara.exceptions.EntityNotFoundException;
import com.capybara.repositories.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitService {

    private final UnitRepository unitRepository;

    @Autowired
    public UnitService(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    public List<Unit> findAll() {
        return unitRepository.findAll();
    }

    public Unit findById(Integer id) {
        return unitRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No Unit found with id: " + id));
    }

    public void save(Unit unit) {
        unitRepository.save(unit);
    }

    public void delete(Integer id) {
        unitRepository.deleteById(id);
    }

    public UnitDto convertToDto(Unit unit) {
        UnitDto.UnitDtoBuilder builderDto = UnitDto.builder();
        return builderDto
                .id(unit.getId())
                .name(unit.getName())
                .build();
    }

    public Unit convertFromDto(UnitDto unitDto) {
        return Unit.builder()
                .id(unitDto.getId())
                .name(unitDto.getName())
                .build();
    }
}
