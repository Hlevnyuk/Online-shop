package com.luxoft.service;
import com.luxoft.entity.Client;
import org.apache.commons.codec.digest.DigestUtils;
import java.util.*;
public class SecurityService {
    private UserService userService;
    private Map<String, Client> userTokens = Collections.synchronizedMap(new HashMap<>());
    public SecurityService(UserService userService) {
        this.userService = userService;
    }
    public boolean isAuth(String token) {
        return userTokens.containsKey(token);
    }
    public String login(Client client) {
        if (isUserExist(client)) {
            return createToken(client);
        }
        throw new RuntimeException();
    }
    public void register(Client client) {
        String email = client.getEmail();
        if (!userService.isMailAlreadyExist(email)) {
            userService.addUser(client);
        } else {
            throw new RuntimeException();
        }
    }
    public boolean isUserExist(Client client) {
        Client foundClient = userService.findClientByEmail(client);
        if (!Objects.equals(foundClient, null)) {
            String hashedPassword = DigestUtils.md5Hex(foundClient.getSole() + client.getPassword());
            return Objects.equals(hashedPassword, foundClient.getPassword());
        }
        return false;
    }
    private String createToken(Client client) {
        String userToken = UUID.randomUUID().toString();
        Client foundClient = userService.findClientByEmail(client);
        userTokens.put(userToken, foundClient);
        return userToken;
    }
    public boolean removeToken(String token) {
        if (userTokens.containsKey(token)) {
            userTokens.remove(token);
            return true;
        }
        return false;
    }
    public Client getUserByToken(String token){
        return userTokens.get(token);
    }
}