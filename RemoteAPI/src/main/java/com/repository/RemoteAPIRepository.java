package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entity.Customer;
@Repository
public interface RemoteAPIRepository extends JpaRepository<Customer, Long> {

}
