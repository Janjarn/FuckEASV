package easvbar.be;

public class User {
    private int id;
    private String name;
    private String Login;
    private String Password;
    private boolean IsSysAdmin;

    private User(int id, String name, String login, String password, boolean isSysAdmin) {
        this.id = id;
        this.name = name;
        Login = login;
        Password = password;
        IsSysAdmin = isSysAdmin;
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
        return Login;
    }

    private void setLogin(String login) {
        Login = login;
    }

    private String getPassword() {
        return Password;
    }

    private void setPassword(String password) {
        Password = password;
    }

    private boolean isSysAdmin() {
        return IsSysAdmin;
    }

    private void setSysAdmin(boolean sysAdmin) {
        IsSysAdmin = sysAdmin;
    }
}
