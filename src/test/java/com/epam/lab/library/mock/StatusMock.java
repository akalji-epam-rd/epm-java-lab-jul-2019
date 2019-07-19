package com.epam.lab.library.mock;

import com.epam.lab.library.domain.Status;

import java.util.ArrayList;
import java.util.List;

public class StatusMock {

    private static List<Status> statusesMock = new ArrayList<>();

    public static List<Status> getStatusMockList() {

        List<String> namesList = new ArrayList<>();
        namesList.add("available");
        namesList.add("on hands");
        namesList.add("in erading room");
        namesList.add("ordered");
        namesList.add("unavailable");

        for (int i = 1; i <= 5; i++) {
            statusesMock.add(getStatus(i, namesList.get(i-1)));
        }

        return statusesMock;

    }

    private static Status getStatus(Integer id, String name) {
        Status status = new Status();
        status.setId(id);
        status.setName(name);

        return status;
    }

}
