package com.luxoft.service;
import com.luxoft.dao.ClientDao;
import com.luxoft.entity.Client;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
public class UserService {
    private ClientDao clientDao;
    public UserService(ClientDao clientDao) {
        this.clientDao = clientDao;
    }
    public void addUser(Client client) {
        clientDao.addUser(client);
        System.out.println("Add user " + client.getName());
    }
    public boolean isMailAlreadyExist(String email) {
        boolean isExist = clientDao.isMailAlreadyExist(email);
        System.out.println("Email exist in db: " + isExist);
        return isExist;
    }
    public boolean Registered(HttpServletRequest req, List<String> userTokens) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user-token")) {
                    if (userTokens.contains(cookie.getValue())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public Client findClientByEmail(Client client){
        Client foundClient = clientDao.findUserByEmail(client);
        System.out.println("Found user with email: " + client.getEmail());
        return foundClient;
    }
}