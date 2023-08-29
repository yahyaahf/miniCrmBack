package com.example.minicrm.controller;


import com.example.minicrm.model.Client;
import com.example.minicrm.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @PostMapping("/clients/add")
    public Client createClient(@RequestBody Client client) {
        return clientService.saveClient(client);
    }

    @GetMapping("/clients/{id}")
    public Client getClient(@PathVariable("id")Long id) {
        Optional<Client> client =clientService.getClientById(id);
        if(client.isPresent()) {
            return client.get();
        } else {
            return null;
        }
    }

    @GetMapping("/clients")
    public Iterable<Client> getClients() {
        return clientService.getClients();
    }

    @DeleteMapping("/clients/delete/{id}")
    public void deleteClient(@PathVariable("id")Long id) {clientService.deleteClient(id);}

    @PutMapping("/clients/update/{id}")
    public Client updateClient(@PathVariable("id") Long id, @RequestBody Client client) {
        Client updated = clientService.updateClient(id,client);
        return updated;
    }



}
