package contohsqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonDao {
    private Connection conn;

    public PersonDao(Connection conn) {
        this.conn = conn;
    }

    public Person getById(Integer id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("select * from Person where id = ?");
        stmt.setInt(1, id);
        ResultSet result = stmt.executeQuery();
        if (result.next()) {
            return new Person(result.getInt("id"),
                    result.getString("name"),
                    result.getString("gender").charAt(0));
        } else {
            return null;
        }
    }

    public List<Person> getByGender(Character gender) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("select * from Person where gender = ?");
        stmt.setString(1, gender.toString());
        ResultSet result = stmt.executeQuery();
        List<Person> people = new ArrayList<>();
        while (result.next()) {
            people.add(new Person(result.getInt("id"),
                    result.getString("name"),
                    result.getString("gender").charAt(0)));
        }

        return people;
    }

    public List<Person> getAll() throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("select * from person");
        ResultSet result = stmt.executeQuery();
        List<Person> people = new ArrayList<>();
        while (result.next()) {
            people.add(new Person(result.getInt("id"),
                    result.getString("name"),
                    result.getString("gender").charAt(0)));
        }
        return people;
    }

    public boolean add(Person p) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("Insert into Person values(null, ?, ?)");
        stmt.setString(1, p.getName());
        stmt.setString(2, p.getGender().toString());
        return stmt.execute();
    }

    public boolean updateById(Integer id, Person p) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("update person set name=?, gender=? where id=?");
        stmt.setString(1, p.getName());
        stmt.setString(2, p.getGender().toString());
        stmt.setInt(3, id);

        return stmt.execute();
    }

    public boolean deleteById(Integer id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("delete from person where id=?");
        stmt.setInt(1, id);
        return stmt.execute();
    }
}
