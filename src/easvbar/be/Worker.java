package easvbar.be;

public class Worker {
    private int id;
    private String name;
    private String password;
    private String role;
    private int roleId;
    private String picture;

    public Worker(int id, String name, String password, String role, int roleId, String picture) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.role = role;
        this.roleId = roleId;
        this.picture = picture;
    }
    public Worker(int id, String name, String password, String role, int roleId) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.role = role;
        this.roleId = roleId;
    }



    public Worker() {

    }

    public Worker(int i) {
        this.id = i;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
    /*
    @Override
    public String toString() {
        return

                "Name " + name + " | " +
                "Occupation " + role;
    }

     */

    @Override
    public String toString() {
        return "Worker{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", roleId=" + roleId +
                ", picture='" + picture + '\'' +
                '}';
    }
}
