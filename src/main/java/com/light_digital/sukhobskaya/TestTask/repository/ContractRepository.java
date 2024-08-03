package com.light_digital.sukhobskaya.TestTask.repository;

import com.light_digital.sukhobskaya.TestTask.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ContractRepository extends JpaRepository<Contract, Integer> {
}
