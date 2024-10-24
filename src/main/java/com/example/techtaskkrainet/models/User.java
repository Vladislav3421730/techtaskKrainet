package com.example.techtaskkrainet.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Table
@Entity(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})// Игнорирование ошибок при ленивой загрузке
public class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")// Автоматическая генерация значения для первичного ключа
    @SequenceGenerator(name = "users_id_seq", sequenceName = "users_id_seq", allocationSize = 1)// Определяет генератор последовательности для создания уникальных значений
    private Long id;

    private String username;
    private String password;
    private String name;
    private String surname;

    @Column(name = "is_active")
    private boolean isActive;//Переменная, которая определяет есть ли у пользователя доступ к системе


    /**
     * Создание таблицы user_role, которая хранит в себе id пользователя
     * и роль в текстовом виде
     */
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Set<Role> roles=new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "user")
    private List<Record> recordList;//Связь с сущностью Record

    //Добавление новой записи в recordList
    public void addRecord(Record record){
        record.setUser(this);
        recordList.add(record);
    }

}
