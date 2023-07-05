package com.example.chatappserver.services;

import com.example.chatappserver.models.Friend;
import com.example.chatappserver.repositories.AccountRepository;
import com.example.chatappserver.repositories.FriendRepository;
import com.example.chatappserver.requests.FriendRequest;
import com.example.chatappserver.responses.FriendResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FriendService {
    @Autowired
    FriendRepository friendRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    AccountRepository accountRepository;

    public List<FriendResponse> getAllFriends() {
        List<Friend> friends = friendRepository.findAll();
        List<FriendResponse> result = new ArrayList<>();
        for (Friend friend : friends)
            result.add(modelMapper.map(friend, FriendResponse.class));
        return result;
    }

    public FriendResponse getFriendById(Long id) {
        try {
            return modelMapper.map(friendRepository.findById(id).get(), FriendResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public FriendResponse createFriend(FriendRequest friendRequest) {
        Friend friend = new Friend();
        friend.setAccount1(accountRepository.findById(friendRequest.getId1()).get());
        friend.setAccount2(accountRepository.findById(friendRequest.getId2()).get());
        friendRepository.save(friend);
        return modelMapper.map(friend, FriendResponse.class);
    }

    public boolean deleteFriend(Long id) {
        try {
            friendRepository.delete(friendRepository.findById(id).get());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
