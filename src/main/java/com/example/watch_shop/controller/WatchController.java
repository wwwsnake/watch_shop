package com.example.watch_shop.controller;

import com.example.watch_shop.entity.WatchEntity;
import com.example.watch_shop.repository.WatchRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/watch")
public class WatchController {
    @Value("${upload.path}") //связь с переменной из application properties, которую не указали тут
    private String uploadFiles;
    @Autowired //подключает репозиторий
    private WatchRepo watchRepo;

    @PostMapping("/addWatch")
    public String addWatch(@ModelAttribute WatchEntity watch, @RequestParam("file") MultipartFile file) {
        File uploadDir = new File(uploadFiles);
        String uuidFile = UUID.randomUUID().toString();
        String fileName = uuidFile + "_" + file.getOriginalFilename(); //у каждого файла будет уникальное название и они не будут заменяться в памяти при совпадении имен

        try {
            file.transferTo(new File(uploadDir+"/"+fileName)); //перенос файла в другую папку
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        watch.setImg(fileName);
        watchRepo.save(watch);//сохраняем в базу новый товар
        return "OK";//при закгрузке товара напишет ОК
    }
    @GetMapping("/getWatches")
    public ResponseEntity getWatches(){
        ArrayList<WatchEntity>  watchEntities = (ArrayList<WatchEntity>) watchRepo.findAll();//достаем все товары из базы в коллекцию
        return ResponseEntity.ok(watchEntities);
    }
    @PostMapping ("/getWatch")
    public ResponseEntity getWatch(@ModelAttribute WatchEntity watch){
        Optional<WatchEntity> watch1 = watchRepo.findById(watch.getId());
        return ResponseEntity.ok(watch1);
    }
}
