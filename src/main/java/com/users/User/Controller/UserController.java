package com.users.User.Controller;

import com.users.User.Models.User;
import com.users.User.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserRepo userRepo;
    @GetMapping("/")
    public String getPage(){
        return "hello world";
    }
    @GetMapping (value="/users")
    public List<User> getUsers(){
        return userRepo.findAll();
    }
    @PostMapping (value ="/save")
    public String saveUsers(@RequestBody User user){
        userRepo.save(user);
        return "user saved...";

    }
    @PutMapping (value ="update/{id}")
    public  String updateUser (@PathVariable long id, @RequestBody User user){
        User updatedUser = userRepo.findById(id).get();
        updatedUser.setFirstName(user.getFirstName());
        updatedUser.setLastName(user.getLastName());
        updatedUser.setActive(user.getActive());
        updatedUser.setBalance(user.getBalance());
        userRepo.save(updatedUser);
        return "user updated";


    }
    @DeleteMapping(value= "delete/{id}")
    public  String deleteUser(@PathVariable long id){
        User deleteUser=userRepo.findById(id).get();
        userRepo.delete(deleteUser);
        return "Delete user with id:" +id;


    }
}
