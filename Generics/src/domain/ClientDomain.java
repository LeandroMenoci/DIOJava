package domain;

import java.util.Objects;

public class ClientDomain implements GenericDomain<String> {

    private String id;
    private String name;
    private int age;

    public ClientDomain() {
    }

    public ClientDomain(String id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ClientDomain that = (ClientDomain) o;
        return Objects.equals(name, that.name) && Objects.equals(age, that.age) && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age);
    }

    @Override
    public String toString() {
        return "ClientDomain{" +
                "id='" + id + '\'' +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}
