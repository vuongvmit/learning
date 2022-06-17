package com.example.producer.controller;

import com.example.producer.service.PushMessageQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/call-push-message")
public class CallQueueController {

    @Autowired
    private PushMessageQueue pushMessageQueue;

    @GetMapping()
    public HttpStatus callPushMessage(@RequestParam String msg) {
        pushMessageQueue.pushMessageQueue(msg);
        return HttpStatus.OK;
    }
}
