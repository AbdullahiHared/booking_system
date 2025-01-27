Class SeatDAO {
Private connection: Connection  // Store the database connection

    Constructor(connection: Connection)
        Set this.connection = connection

    // Method: Add a new seat to the database
    Method addSeat(seat: Seat)
        SQL: "INSERT INTO seats (seat_number, bus_id, is_booked) VALUES (?, ?, ?)"
        Prepare the SQL statement
        Set the parameters:
            1. seat.getSeatNumber()
            2. seat.getBusId()
            3. seat.getIsBooked()
        Execute the update
        If rows affected > 0, print "Seat added successfully"
        Else, print "Failed to add seat"
        Handle SQLException

    // Method: Get all seats
    Method getAllSeats() -> List<Seat>
        SQL: "SELECT * FROM seats"
        Prepare the statement
        Execute the query
        Create an empty list to store seats
        While result set has more rows
            Create a Seat object
            Set Seat properties from result set
            Add the Seat object to the list
        Return the list of seats
        Handle SQLException

    // Method: Find a seat by ID
    Method getSeatById(seatId: Int) -> Seat
        SQL: "SELECT * FROM seats WHERE seat_id = ?"
        Prepare the statement
        Set the seatId parameter
        Execute the query
        If result set has a row
            Create a Seat object
            Set Seat properties from result set
            Return the Seat object
        Else, return null
        Handle SQLException

    // Method: Update a seat's information
    Method updateSeat(seat: Seat)
        SQL: "UPDATE seats SET seat_number = ?, bus_id = ?, is_booked = ? WHERE seat_id = ?"
        Prepare the statement
        Set the parameters:
            1. seat.getSeatNumber()
            2. seat.getBusId()
            3. seat.getIsBooked()
            4. seat.getSeatId()
        Execute the update
        If rows affected > 0, print "Seat updated successfully"
        Else, print "No seat found to update"
        Handle SQLException

    // Method: Delete a seat by ID
    Method deleteSeatById(seatId: Int)
        SQL: "DELETE FROM seats WHERE seat_id = ?"
        Prepare the statement
        Set the seatId parameter
        Execute the update
        If rows affected > 0, print "Seat deleted successfully"
        Else, print "No seat found to delete"
        Handle SQLException
}
