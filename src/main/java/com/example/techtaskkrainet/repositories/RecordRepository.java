package com.example.techtaskkrainet.repositories;

import com.example.techtaskkrainet.models.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record,Long> {
    List<Record> findRecordByProjectId(Long id);

}
