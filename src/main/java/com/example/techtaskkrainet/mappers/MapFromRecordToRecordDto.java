package com.example.techtaskkrainet.mappers;

import com.example.techtaskkrainet.dto.RecordDto;
import com.example.techtaskkrainet.models.Record;

public class MapFromRecordToRecordDto {

    public static RecordDto map(Record record){
        return RecordDto.builder()
                .project(record.getProject())
                .user(record.getUser())
                .record(record)
                .build();
    }
}
