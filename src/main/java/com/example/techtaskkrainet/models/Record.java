package com.example.techtaskkrainet.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;


@Entity
@Table(name = "record")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})// Игнорирование ошибок при ленивой загрузке
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "record_id_seq")// Автоматическая генерация значения для первичного ключа
    @SequenceGenerator(name = "record_id_seq", sequenceName = "record_id_seq", allocationSize = 1)// Определяет генератор последовательности для создания уникальных значений
    private Long id;

    @Column(name = "start_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")// Ввод данных в формате "yyyy-MM-dd HH:mm:ss"
    private LocalDateTime startTime;

    @Column(name = "end_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")// Ввод данных в формате "yyyy-MM-dd HH:mm:ss"
    private LocalDateTime endTime;


    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.LAZY)
    @JoinColumn(name="project_id")
    @JsonIgnore
    private Project project;//Связь с сущностью Project

    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    @JsonIgnore
    private User user;//Связь с сущностью User
}
