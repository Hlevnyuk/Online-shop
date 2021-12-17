package com.luxoft.dao;
import com.luxoft.entity.Client;
public interface ClientDao {
    void addUser(Client client);
    boolean isMailAlreadyExist(String email);
    Client findUserByEmail(Client client);
}
