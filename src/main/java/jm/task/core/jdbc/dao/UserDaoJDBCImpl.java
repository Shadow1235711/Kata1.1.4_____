package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {


    public UserDaoJDBCImpl() {

    }

    public  void createUsersTable() {
         final Connection connection = Util.getConnection();
        Statement stmt = null;
        String creat_T="CREATE TABLE `user` (\n" +
                "  `id_user` int NOT NULL AUTO_INCREMENT,\n" +
                "  `user_name` varchar(60) DEFAULT NULL,\n" +
                "  `user_lastname` varchar(60) DEFAULT NULL,\n" +
                "  `user_age` int DEFAULT NULL,\n" +
                "  PRIMARY KEY (`id_user`)\n" +
                ") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb3";
        try {
           stmt = connection.createStatement();
            stmt.execute(creat_T);
        } catch (SQLException e) {
            System.out.println("Таблица уже существует");
        }
        finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {

                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {

                }
            }
        }
    }

    public void dropUsersTable() {
        final Connection connection = Util.getConnection();
        Statement stmt = null;
        String drop_T="DROP TABLE `users`.`user`";
        try {
            stmt = connection.createStatement();
            stmt.execute(drop_T);
        } catch (SQLException e) {
            System.out.println("Таблицы не существует");
        }
        finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {

                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {

                }
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        final Connection connection = Util.getConnection();
        PreparedStatement stmt = null;
        String add_User = "insert into user(user_name,user_lastname,user_age) values(?,?,?)";
        try {
            stmt = connection.prepareStatement(add_User);
            stmt.setString(1,name);
            stmt.setString(2,lastName);
            stmt.setByte(3,age);
            stmt.execute();
            System.out.println("User с именем — " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            System.out.println("не удалось добавить пользователя");
        }
        finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {

                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {

                }
            }
        }
    }

    public void removeUserById(long id) {
        final Connection connection = Util.getConnection();
        PreparedStatement stmt = null;
        String del_User = "delete from `user` where id_user=?";
        try {
            stmt = connection.prepareStatement(del_User);
            stmt.setLong(1, id);
            stmt.execute();
            System.out.println("User с id "+id+" удален");
        } catch (SQLException e) {
            System.out.println("User c указанным id не существует");
        }
        finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {

                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {

                }
            }
        }
    }

    public List<User> getAllUsers() {
        final Connection connection = Util.getConnection();
        List<User> allUsers = new ArrayList<>();
        Statement stmt = null;
        String sql = "SELECT id_user,user_name,user_lastname,user_age FROM `user`";
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id_user"));
                    user.setName(rs.getString("user_name"));
                user.setLastName(rs.getString("user_lastname"));
                user.setAge(rs.getByte("user_age"));
                allUsers.add(user);
            }

        } catch (SQLException e) {
            System.out.println("не удалось получить пользователей");
        }
        finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {

                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {

                }
            }
        }
        System.out.println(allUsers);
        return allUsers;
    }

    public void cleanUsersTable() {
        final Connection connection = Util.getConnection();
        PreparedStatement stmt = null;
        String del_User = "delete from `user`";
        try {
            stmt = connection.prepareStatement(del_User);
            stmt.execute();

            System.out.println("удалены все пользователи");
        } catch (SQLException e) {
            System.out.println("не удалось удалить пользователей");
        }
        finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {

                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {

                }
            }
        }
    }
}
