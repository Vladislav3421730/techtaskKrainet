package com.example.techtaskkrainet.controllers;

import com.example.techtaskkrainet.dto.ResponseDto;
import com.example.techtaskkrainet.models.User;
import com.example.techtaskkrainet.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/add")
    public ResponseEntity<ResponseDto> addNewUser(@RequestBody User user){
        User savingUser=userService.save(user);
        return ResponseEntity.ok(new ResponseDto(String.format("Пользователь %s был сохранён",savingUser.getUsername())));
    }

    @GetMapping("/get")
    public List<User> findAllUsers(){
        return userService.findAll();
    }

    @GetMapping("/get/{id}")
    public User findUser(@PathVariable Long id){
        return userService.findById(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDto> DeleteUser(@PathVariable Long id){
        userService.delete(id);
        return ResponseEntity.ok(new ResponseDto(String.format("Пользователь с id %d был удалён",id)));
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> UpdateUser(@RequestBody User user){
        userService.update(user);
        return ResponseEntity.ok(new ResponseDto(String.format("Пользователь с id %d был обновлён",user.getId())));
    }




}
