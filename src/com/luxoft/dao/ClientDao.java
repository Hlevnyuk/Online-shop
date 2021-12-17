package com.luxoft.dao;
import com.luxoft.entity.Client;
public interface ClientDao {
    void addUser(Client client);
    boolean isUserExists(Client Client);
    boolean isMailAlreadyExist(String email);
}
