import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface PersonDao {
    @SqlUpdate("create table person (id integer primary key, name varchar(255), gender char, age int)")
    void createTable();

    @SqlUpdate("insert into person values(null, ?, ?, ?)")
    void insertPositional(String name, char gender, int age);

    @SqlUpdate("insert into person values(null, :name, :gender, :age)")
    void insertNamed(@Bind("name") String name,
                     @Bind("gender") char gender,
                     @Bind("age") int age);

    @SqlUpdate("insert into person values(null, :name, :gender, :age)")
    void insertBean(@BindBean Person person);

    @SqlQuery("select * from person")
    @RegisterRowMapper(PersonMapper.class)
    List<Person> listUsers();
}
