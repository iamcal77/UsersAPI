package com.users.User.Repo;

import com.users.User.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo  extends JpaRepository <User, Long> {
}
