package overridetech.jdbc.jpa;

import overridetech.jdbc.jpa.model.User;
import overridetech.jdbc.jpa.service.UserService;
import overridetech.jdbc.jpa.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();

        userService.saveUser("Dasha", "Shumarina",(byte) 23);
        userService.saveUser("Misha", "Shumarin",(byte) 19);
        userService.saveUser("Ilya", "Shumarin",(byte) 5);
        userService.saveUser("Seryozha", "Shumarin",(byte) 12);

        for (User user : userService.getAllUsers()) {
            System.out.println(user);
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
