package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private final Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String creatT = "CREATE TABLE `user` (\n" +
                "  `idUser` int NOT NULL AUTO_INCREMENT,\n" +
                "  `userName` varchar(60) DEFAULT NULL,\n" +
                "  `userLastname` varchar(60) DEFAULT NULL,\n" +
                "  `userAge` int DEFAULT NULL,\n" +
                "  PRIMARY KEY (`idUser`)\n" +
                ") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb3";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(creatT);
        } catch (SQLException e) {
            System.out.println("Таблица уже существует");
        }
    }

    public void dropUsersTable() {
        String dropT = "DROP TABLE `users`.`user`";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(dropT);
        } catch (SQLException e) {
            System.out.println("Таблицы не существует");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String addUser = "insert into user(userName,userLastname,userAge) values(?,?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(addUser)) {
            stmt.setString(1, name);
            stmt.setString(2, lastName);
            stmt.setByte(3, age);
            stmt.execute();
            System.out.println("User с именем — " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            System.out.println("не удалось добавить пользователя");
        }
    }

    public void removeUserById(long id) {
        String delUser = "delete from `user` where idUser=?";
        try (PreparedStatement stmt = connection.prepareStatement(delUser)) {
            stmt.setLong(1, id);
            stmt.execute();
            System.out.println("User с id " + id + " удален");
        } catch (SQLException e) {
            System.out.println("User c указанным id не существует");
        }
    }

    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        String sql = "SELECT idUser,userName,userLastname,userAge FROM `user`";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("idUser"));
                user.setName(rs.getString("userName"));
                user.setLastName(rs.getString("userLastname"));
                user.setAge(rs.getByte("userAge"));
                allUsers.add(user);
            }

        } catch (SQLException e) {
            System.out.println("не удалось получить пользователей");
        }
        System.out.println(allUsers);
        return allUsers;
    }

    public void cleanUsersTable() {
        String delUser = "delete from `user`";
        try (PreparedStatement stmt = connection.prepareStatement(delUser)) {
            stmt.execute();
            System.out.println("удалены все пользователи");
        } catch (SQLException e) {
            System.out.println("не удалось удалить пользователей");
        }
    }
}
