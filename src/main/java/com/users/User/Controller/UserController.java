package com.users.User.Controller;

import com.users.User.Models.User;
import com.users.User.Models.WithdrawalRequest;
import com.users.User.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
        return "User updated";


    }
    @DeleteMapping(value= "delete/{id}")
    public  String deleteUser(@PathVariable long id){
        User deleteUser=userRepo.findById(id).get();
        userRepo.delete(deleteUser);
        return "Delete user with id:" +id;
    }
    @PostMapping(value = "/withdraw")
    public ResponseEntity<String> withdrawFunds(@RequestBody WithdrawalRequest request) {
        User user = userRepo.findById(request.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));

        if (!"active".equalsIgnoreCase(user.getActive())) {
            return ResponseEntity.badRequest().body("User is not active");
        }

        if (request.getAmount() % 100 != 0) {
            return ResponseEntity.badRequest().body("Withdrawal amount must be a multiple of 100");
        }

        if (user.getBalance() < request.getAmount()) {
            return ResponseEntity.badRequest().body("Insufficient balance");
        }

        user.setBalance(user.getBalance() - request.getAmount());
        userRepo.save(user);

        return ResponseEntity.ok("Withdrawal successful");
    }
}
