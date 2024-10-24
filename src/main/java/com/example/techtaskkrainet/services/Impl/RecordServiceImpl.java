package com.example.techtaskkrainet.services.Impl;

import com.example.techtaskkrainet.dto.RecordAddDto;
import com.example.techtaskkrainet.dto.RecordDto;
import com.example.techtaskkrainet.exceptions.ProjectNotFoundException;
import com.example.techtaskkrainet.exceptions.RecordNotFoundException;
import com.example.techtaskkrainet.exceptions.WrongDateException;
import com.example.techtaskkrainet.mappers.MapFromRecordToRecordDto;
import com.example.techtaskkrainet.models.Project;
import com.example.techtaskkrainet.models.Record;
import com.example.techtaskkrainet.models.User;
import com.example.techtaskkrainet.repositories.RecordRepository;
import com.example.techtaskkrainet.services.ProjectService;
import com.example.techtaskkrainet.services.RecordService;
import com.example.techtaskkrainet.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервис для управления записями (Record).
 */
@Service
@RequiredArgsConstructor
public class RecordServiceImpl implements RecordService {


    private final RecordRepository recordRepository;
    private final UserService userService;
    private final ProjectService projectService;

    @Override
    public Record ConvertToRecord(RecordAddDto recordAddDto, Principal principal) {
        // Получаем текущего пользователя по username и проект по ID
        User user = userService.findByUsername(principal.getName());
        Project project = projectService.findById(recordAddDto.getProjectId());

        // Проверка на корректность времени
        if (recordAddDto.getStartTime().compareTo(recordAddDto.getEndTime()) >= 0) {
            throw new WrongDateException("Конечное время должно быть больше чем начальное");
        }

        // Создаем новую запись (Record) и добавляем её пользователю и проекту
        Record record = Record.builder()
                .startTime(recordAddDto.getStartTime())
                .endTime(recordAddDto.getEndTime())
                .build();

        // Связываем запись с пользователем и проектом
        user.addRecord(record);
        project.addRecord(record);
        return record;
    }

    @Override
    public List<RecordDto> findAllByProjectId(Long id) {
        // Преобразуем записи в DTO объекты с помощью маппера
        return recordRepository.findRecordByProjectId(id).stream()
                .map(MapFromRecordToRecordDto::map)
                .collect(Collectors.toList());
    }

    @Override
    public List<RecordDto> findAll() {
        return recordRepository.findAll().stream()
                .map(MapFromRecordToRecordDto::map)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public RecordDto save(Record record) {
        // Сохраняем запись и преобразуем её в DTO для возврата
        return MapFromRecordToRecordDto.map(recordRepository.save(record));
    }

    @Override
    public RecordDto findById(Long id) {
        return MapFromRecordToRecordDto.map(recordRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Запись с id %d не найдена.", id))));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // Проверяем, существует ли запись перед удалением
        if (!recordRepository.existsById(id)) {
            throw new ProjectNotFoundException(String.format("Запись с id %d не найдена. Удаление невозможно", id));
        }
        recordRepository.deleteById(id);
    }

    @Override
    @Transactional
    public RecordDto update(Record record) {
        if (!recordRepository.existsById(record.getId())) {
            throw new RecordNotFoundException(String.format("Запись с id %d не найдена. Обновление невозможно", record.getId()));
        }
        return save(record);
    }
}
