package com.example.techtaskkrainet.services;

import com.example.techtaskkrainet.dto.RecordAddDto;
import com.example.techtaskkrainet.dto.RecordDto;
import com.example.techtaskkrainet.models.Record;

import java.security.Principal;
import java.util.List;

/**
 * Сервис сервиса для работы с сущностью Record.
 */
public interface RecordService extends BaseService<Record, RecordDto, Long> {

   /**
    * Преобразует DTO для добавления записи (RecordAddDto) в сущность Record,
    * используя информацию о текущем пользователе (Principal).
    *
    * @param recordAddDto объект DTO для добавления записи
    * @param principal информация о текущем пользователе
    * @return сущность Record, готовая к сохранению
    */
   Record ConvertToRecord(RecordAddDto recordAddDto, Principal principal);

   List<RecordDto> findAllByProjectId(Long id);
   List<RecordDto> findAllByUserId(Long id);

}

