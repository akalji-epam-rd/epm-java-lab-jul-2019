package com.epam.lab.library.mock;

import com.epam.lab.library.domain.User;

import java.util.ArrayList;
import java.util.List;

public class UserMock {

    private static List<User> userMock = new ArrayList<>();

    public static List<User> getUserMockList() {

        List<String> emailList = new ArrayList<>();
        emailList.add("ya@ya.ru");
        emailList.add("go@google.com");
        emailList.add("vn@yandex.net");
        emailList.add("o@yahoo.net");

        List<String> passwordList = new ArrayList<>();
        passwordList.add("12345");
        passwordList.add("54789");
        passwordList.add("qwerty");
        passwordList.add("qwerty");

        List<String> namesList = new ArrayList<>();
        namesList.add("Vasya");
        namesList.add("Ivan");
        namesList.add("Petya");
        namesList.add("Pitr");

        List<String> lastNameList = new ArrayList<>();
        lastNameList.add("Vasilev");
        lastNameList.add("Ivanov");
        lastNameList.add("Petrovich");
        lastNameList.add("Pitrovich");

        for (int i = 0; i < 4; i++) {
            userMock.add(getUser(i+1, emailList.get(i), passwordList.get(i), namesList.get(i), lastNameList.get(i)));
        }

        return userMock;

    }

    private static User getUser(Integer id, String email, String password, String name, String lastName) {
        User user = new User(id);
        user.setEmail(email);
        user.setPassword(password);
        user.setName(name);
        user.setLastName(lastName);

        return user;
    }

}
