package com.example.techtaskkrainet.dto;

import com.example.techtaskkrainet.models.Project;
import com.example.techtaskkrainet.models.Record;
import com.example.techtaskkrainet.models.User;
import lombok.Builder;
import lombok.Data;

//DTO для отправления информации о Record
@Data
@Builder
public class RecordDto {
    private User user;
    private Project project;
    private Record record;
}
