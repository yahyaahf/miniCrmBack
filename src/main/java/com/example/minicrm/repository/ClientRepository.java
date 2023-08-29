package com.example.minicrm.repository;

import com.example.minicrm.model.Client;

import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client,Long> {
}
