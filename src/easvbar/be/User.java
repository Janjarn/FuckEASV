package easvbar.be;

public class User {
    private int id;
    private String name;
    private String lastname;

    private int pending;

    public User(int id, String name, String lastname, int pending) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.pending = pending;
    }

    public User() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String name) {
        this.lastname = name;
    }

    public int getPending() {
        return pending;
    }

    public void setPending(int pending) {
        this.pending = pending;
    }

    @Override
    public String toString() {
        return
                ", name='" + name + '\'' +
                ", lastname='" + lastname;
    }
}
