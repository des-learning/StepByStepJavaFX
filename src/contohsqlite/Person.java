package contohsqlite;

import java.util.Objects;

public class Person {
    private Integer id;
    private String name;
    private Character gender;

    public Person(Integer id, String name, Character gender) {
        this.id = id;
        this.name = name;
        this.gender = gender;
    }

    public Person(String name, Character gender) {
        this.id = null;
        this.name = name;
        this.gender = gender;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return Objects.equals(getId(), person.getId()) &&
                Objects.equals(getName(), person.getName()) &&
                Objects.equals(getGender(), person.getGender());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getName(), getGender());
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                '}';
    }
}
