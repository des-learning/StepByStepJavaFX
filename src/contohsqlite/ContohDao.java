package contohsqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class ContohDao {
    public static void displayPerson(Person p) {
        if (p == null) {
            System.out.println("Not a person");
        } else {
            System.out.printf("%2d %20s %c\n", p.getId(), p.getName(), p.getGender());
        }
    }

    public static void displayPeople(List<Person> people) {
        people.forEach(p -> displayPerson(p));
    }

    public static void main(String []args) throws SQLException {
        String url = "jdbc:sqlite:./testing.sqlite";
        Connection conn = DriverManager.getConnection(url);

        PersonDao dao = new PersonDao(conn);
        Person p = dao.getById(1);
        displayPerson(p);

        p = dao.getById(2);
        displayPerson(p);

        List<Person> people = dao.getByGender('f');
        displayPeople(people);

        dao.add(new Person("Budi", 'm'));
        dao.add(new Person("Iwan", 'm'));
        people = dao.getAll();
        displayPeople(people);

        people.get(5).setName("Robi");
        dao.updateById(people.get(5).getId(), people.get(5));

        people = dao.getAll();
        displayPeople(people);

        dao.deleteById(people.get(5).getId());
        people = dao.getAll();
        displayPeople(people);
    }
}
