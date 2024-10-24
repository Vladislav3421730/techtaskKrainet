package com.example.techtaskkrainet.controllers;


import com.example.techtaskkrainet.dto.RecordAddDto;
import com.example.techtaskkrainet.dto.RecordDto;
import com.example.techtaskkrainet.dto.ResponseDto;
import com.example.techtaskkrainet.models.Record;
import com.example.techtaskkrainet.services.Impl.RecordServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/record")
public class RecordController {

    private final RecordServiceImpl recordService;

    @PostMapping("/add")
    public ResponseEntity<ResponseDto> addNewRecord(@RequestBody RecordAddDto recordAddDto, Principal principal){
        Record record=recordService.ConvertToRecord(recordAddDto,principal);
        recordService.save(record);
        return ResponseEntity.ok(new ResponseDto(String.format("Запись %d была сохранена",record.getId())));
    }

    @GetMapping("/get")
    public List<RecordDto> findAllRecords(){
        return recordService.findAll();
    }

    @GetMapping("/get/{id}")
    public RecordDto findRecordById(@PathVariable Long id){
        return recordService.findById(id);
    }

    @GetMapping("/get/ByProjectId/{projectId}")
    public List<RecordDto> findRecordsByProjectId(@PathVariable Long projectId){
        return recordService.findAllByProjectId(projectId);
    }

    @GetMapping("/get/ByUserId/{projectId}")
    public List<RecordDto> findRecordsByUserId(@PathVariable Long projectId){
        return recordService.findAllByUserId(projectId);
    }



    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDto> DeleteRecord(@PathVariable Long id){
        recordService.delete(id);
        return ResponseEntity.ok(new ResponseDto(String.format("Запись с id %d была удалена",id)));
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> UpdateRecord(@RequestBody Record record){
        recordService.update(record);
        return ResponseEntity.ok(new ResponseDto(String.format("Запись с id %d была обновлена",record.getId())));
    }
}
