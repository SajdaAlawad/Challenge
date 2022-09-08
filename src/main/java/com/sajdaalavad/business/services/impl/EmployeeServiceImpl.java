package com.sajdaalavad.business.services.impl;

import ch.qos.logback.core.spi.PreSerializationTransformer;
import com.sajdaalavad.business.dto.EmployeeDto;
import com.sajdaalavad.business.services.IEmployeeServices;
import com.sajdaalavad.data.entity.EmployeeEntity;
import com.sajdaalavad.data.repository.EmployeeRepository;
import com.sajdaalavad.exception.ResourceNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.PresentationDirection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Log4j2
public class EmployeeServiceImpl implements IEmployeeServices {

    @Autowired
    EmployeeRepository repository;
    @Autowired
    ModelMapper modelMapper;

    //modelMapper
    @Override
    public EmployeeDto EntityTODto(EmployeeEntity employeeEntity) {
        EmployeeDto employeeDto=modelMapper.map(employeeEntity,EmployeeDto.class);
        return employeeDto;
    }
    @Override
    public EmployeeEntity DtotoEntity(EmployeeDto employeeDto) {
        EmployeeEntity entity=modelMapper.map(employeeDto,EmployeeEntity.class);
        return entity;
    }

    //Save
    // http://localhost:8080/save/employees
    @Override
    @PostMapping("/save/employees")
    public EmployeeDto createEmployee(@RequestBody EmployeeDto employeeDto) {
        //Dtodaki verileri entity ye cevirdik
        EmployeeEntity entity=DtotoEntity(employeeDto);
        repository.save(entity);
        log.info("Ekleme Basarili");
        return employeeDto;
    }
    //List
    // http://localhost:8080/list/employees
    @Override
    @GetMapping("/list/employees")
    public List<EmployeeDto> getAllEmployee() {
        //Bütün dosyalar listemyle listesin
        List<EmployeeDto> list=new ArrayList<>();
        Iterable<EmployeeEntity> listem=repository.findAll();
        for(EmployeeEntity entity : listem){
            EmployeeDto employeeDto= EntityTODto(entity);
            list.add(employeeDto);
        }
        return list;
    }

    //find
    // http://localhost:8080/list/employees
    @Override
    @GetMapping("/find/employees/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable(name="id") Long id) {
        EmployeeEntity employeeEntity= repository.findById(id)
                .orElseThrow( ()->new ResourceNotFoundException("Employee "+id+"id bulunmadii"));
        EmployeeDto employeeDto=EntityTODto(employeeEntity);
        return ResponseEntity.ok(employeeDto);
    }
    //update
    @Override
    @PutMapping("/update/employees/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable(name="id") Long id,@RequestBody EmployeeDto employeeDto) {
        //DtoEntity
        EmployeeEntity employeeEntity=DtotoEntity(employeeDto);

        EmployeeEntity employeeFind=repository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Employee"+"id bulunmadii"));

        //setEntity
        employeeFind.setEmployeeName(employeeEntity.getEmployeeName());
        employeeFind.setEmployeeEmail(employeeEntity.getEmployeeEmail());

        EmployeeEntity employeeEntity2= repository.save(employeeFind);

        //EntityToDto
        EmployeeDto employeeDto2= EntityTODto(employeeEntity2);
        return ResponseEntity.ok(employeeDto2);
    }

    //Delete
    @Override
    @DeleteMapping("/delete/employees/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable(name="id") Long id) {
        EmployeeEntity employeeEntity=repository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Employee"+id+"id bulunmadii"));

        repository.delete(employeeEntity);
        Map<String, Boolean> response=new HashMap<>();
        response.put("silindi",Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
