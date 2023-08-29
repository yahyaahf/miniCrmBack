package com.example.minicrm.service;


import com.example.minicrm.model.Client;
import com.example.minicrm.repository.ClientRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    public Optional<Client> getClientById(Long id){ return clientRepository.findById(id);}

    public Iterable<Client> getClients(){ return clientRepository.findAll();}

    public void deleteClient(Long id){ clientRepository.deleteById(id);}

    public Client saveClient(Client client){
        Client savedClient= clientRepository.save(client);
        return savedClient;
    }

    public Client updateClient(Long id,Client client) {
        Optional<Client> oldClient = clientRepository.findById(id);
        if(oldClient.isPresent()){
            if(oldClient.get().getNom() != client.getNom()) oldClient.get().setNom(client.getNom());
            if(oldClient.get().getPrenom() != client.getPrenom()) oldClient.get().setPrenom(client.getPrenom());
            if(oldClient.get().getEntreprise() != client.getEntreprise()) oldClient.get().setEntreprise(client.getEntreprise());
            if(oldClient.get().getTelephone()!=client.getTelephone()) oldClient.get().setTelephone(client.getTelephone());
            if(oldClient.get().getCategorie() != client.getCategorie()) oldClient.get().setCategorie(client.getCategorie());
            if(oldClient.get().getEmail() != client.getEmail()) oldClient.get().setEmail(client.getEmail());
            clientRepository.save(oldClient.get());
            return oldClient.get();

        }
        return null;
    }

}
