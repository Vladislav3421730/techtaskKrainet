package com.example.techtaskkrainet.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "project")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})// Игнорирование ошибок при ленивой загрузке
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_id_seq") // Автоматическая генерация значения для первичного ключа
    @SequenceGenerator(name = "project_id_seq", sequenceName = "project_id_seq", allocationSize = 1)// Определяет генератор последовательности для создания уникальных значений
    private Long id;

    private String name;
    private String description;
    private BigDecimal cost;

    @Column(name = "created_at")
    private LocalDate createdAt;

    //Связь с сущностью Project
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "project")
    private List<Record> recordList;

    //Добавление новой записи в recordList
    public void addRecord(Record record){
        record.setProject(this);
        recordList.add(record);
    }
}
