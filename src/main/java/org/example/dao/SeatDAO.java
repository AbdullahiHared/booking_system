package org.example.dao;
import org.example.model.Seat;

import java.sql.Connection;

public class SeatDAO {
    private Connection connection;

    public SeatDAO(Connection connection) {
        this.connection = connection;
    }

}
