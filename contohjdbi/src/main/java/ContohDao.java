import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.util.List;

public class ContohDao {
    public static void main(String []args) {
        Jdbi jdbi = Jdbi.create("jdbc:sqlite::memory:");
        jdbi.installPlugin(new SqlObjectPlugin());

        List<Person> people = jdbi.withExtension(PersonDao.class, dao -> {
            dao.createTable();

            dao.insertPositional("budi", 'm', 20);
            dao.insertNamed("susi", 'f', 22);
            dao.insertBean(new Person("rudi", 'm', 27));

            return dao.listUsers();
        });

        people.stream().forEach(person -> {
            System.out.printf("%2d %20s %c %2d\n", person.getId(), person.getName(),
                    person.getGender(), person.getAge());
        });
    }
}
