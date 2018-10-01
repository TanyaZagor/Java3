
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class BaseAuthService implements AuthService {
    private Map<String, User> users = new HashMap<>();
    private static Connection connection;
    private static Statement statement;

    public BaseAuthService() {
/*        users.put("nick1", new User("login1", "pass1", "nick1"));
        users.put("nick2", new User("login2", "pass2", "nick2"));
        users.put("nick3", new User("login3", "pass3", "nick3"));
*/
        try {
            connect();
            String sqlQuery = "SELECT login, pass, nick FROM users;";
            ResultSet rs = statement.executeQuery(sqlQuery);
            while (rs.next()) {
                String login = rs.getString(1);
                String pass = rs.getString(2);
                String nick = rs.getString(3);
                users.put(login, new User(login, pass, nick));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }

    public static void connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection( "jdbc:sqlite:main.db");
        statement = connection.createStatement();
    }

    public static void disconnect() {
        try {
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeNick(String newNick, String nick) {
        try {
            connect();
            statement.execute("UPDATE users SET nick=" + newNick + " WHERE login = " + nick);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }


    @Override
    public String authByLoginAndPassword(String login, String password) {
        for (User user : users.values()) {
            if (login.equals(user.getLogin())
                    && password.equals(user.getPassword())
                    && user.isActive())
                return user.getNickname();
        }
        return null;
    }

    @Override
    public User createOrActivateUser(String login, String password, String nick) {
        User user = new User(login, password, nick);
        if (users.containsKey(nick)) {
            users.get(nick).setActive(true);
            System.out.println("User with nick " + nick + "already exist");
        } else {
            users.put(nick, user);
            persist(user);
        }
        return user;
    }

    private void persist(User user) {
        try {
            connect();
            statement.execute("INSERT INTO users ( login, pass, nick ) VALUES ("
                    + user.getLogin() + ", "
                    + user.getPassword() + ", " + user.getNickname() + "  )");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }

    @Override
    public boolean deactivateUser(String nick) {
        User user = users.get(nick);
        if (user != null) {
            user.setActive(false);
            return true;
        }
        return false;
    }
}