package easvbar.be;

public class User {
    private int id;
    private String name;
    private String login;
    private String password;
    private boolean isSysAdmin;

    private User(int id, String name, String login, String password, boolean isSysAdmin) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.isSysAdmin = isSysAdmin;
    }

    private int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    private String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    private String getLogin() {
        return login;
    }

    private void setLogin(String login) {
        this.login = login;
    }

    private String getPassword() {
        return password;
    }

    private void setPassword(String password) {
        this.password = password;
    }

    private boolean isSysAdmin() {
        return isSysAdmin;
    }

    private void setSysAdmin(boolean sysAdmin) {
        isSysAdmin = sysAdmin;
    }
}
