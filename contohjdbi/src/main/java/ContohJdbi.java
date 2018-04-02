import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class ContohJdbi {
    public static void main(String []args) {
        Jdbi jdbi = Jdbi.create("jdbc:sqlite::memory:");


        // row mapper to class
        jdbi.registerRowMapper(new PersonMapper());

        List<Person> people =
        jdbi.withHandle(handle -> {
            // create table
            handle.execute("create table Person (" +
                    "id integer primary key, " +
                    "name varchar(255), " +
                    "gender char, " +
                    "age integer" +
                    ")"
            );

            // inline positional parameter
            handle.execute("insert into Person values(null, ?, ?, ?)", "Budi", 'm', 20);

            // position parameter
            handle.createUpdate("insert into Person values(null, ?, ?, ?)")
                    .bind(0, "Rudi")
                    .bind(1, 'm')
                    .bind(2, 27)
                    .execute();

            // named parameter
            handle.createUpdate("insert into Person values(null, :name, :gender, :age)")
                .bind("name", "Susi")
                .bind("gender", 'f')
                .bind("age", 22)
                .execute();

            // named parameter from bean
            handle.createUpdate("insert into Person values(null, :name, :gender, :age)")
                    .bindBean(new Person("Rita", 'f', 25))
                    .execute();


            // return list of person (people)
            return handle.createQuery("select * from person")
                    .mapTo(Person.class)
                    .list();
        });

        people.stream().forEach(x -> {
            System.out.printf("%2d %20s %c %2d\n", x.getId(), x.getName(), x.getGender(), x.getAge());
        });
    }
}
