package com.example.chatappserver.controllers;

import com.example.chatappserver.requests.FriendRequest;
import com.example.chatappserver.responses.FriendResponse;
import com.example.chatappserver.services.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friends")
public class FriendController {
    @Autowired
    FriendService friendService;

    @GetMapping("")
    public ResponseEntity<List<FriendResponse>> getAllFriends() {
        return new ResponseEntity<>(friendService.getAllFriends(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FriendResponse> getFriendById(@PathVariable Long id) {
        FriendResponse friendResponse = friendService.getFriendById(id);
        return friendResponse != null ? new ResponseEntity<>(friendResponse, HttpStatus.OK) : new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("")
    public ResponseEntity<FriendResponse> createFriend(@RequestBody FriendRequest friendRequest) {
        return new ResponseEntity<>(friendService.createFriend(friendRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteFriend(@PathVariable Long id) {
        boolean result = friendService.deleteFriend(id);
        return result ? new ResponseEntity<>(true, HttpStatus.OK) : new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }
}
