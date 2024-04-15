package com.fiap.challenge.targetcustomer.repository;

import com.fiap.challenge.targetcustomer.model.Login;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<Login, Long> {
}
