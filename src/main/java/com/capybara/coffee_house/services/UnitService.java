package com.capybara.coffee_house.services;

import com.capybara.coffee_house.dto.CategoryDto;
import com.capybara.coffee_house.dto.UnitDto;
import com.capybara.coffee_house.entities.Category;
import com.capybara.coffee_house.entities.Unit;
import com.capybara.coffee_house.exceptions.EntityNotFoundException;
import com.capybara.coffee_house.repositories.UnitRepository;
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
        Unit.UnitBuilder builder = Unit.builder();
        return builder
                .id(unitDto.getId())
                .name(unitDto.getName())
                .build();
    }
}
