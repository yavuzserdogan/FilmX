import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        // Connect Database
        Connect();

        var allUSer = getAllUser();

        for (var user : allUSer) {
            System.out.println(user);
        }

        logIn();

        register();

    }

    private static Connection con;

    public static Connection Connect() {
        String url = "jdbc:postgresql://localhost:5432/filmX";
        String username = "postgres";
        String password = "admin";
        try {
            con = DriverManager.getConnection(url, username, password);

        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return con;
    }

    public static ArrayList<String> getAllUser() {
        ArrayList<String> mailArray = new ArrayList<>();
        Statement st = null;
        ResultSet rs = null;
        try {
            st = con.createStatement();
            String sql1 = "SELECT email FROM users";
            rs = st.executeQuery(sql1);
            while (rs.next()) {
                String mailUser = rs.getString("email");
                mailArray.add(mailUser);
            }
        } catch (

        Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } finally {
            // Result set and Statement are closing
            try {
                if (rs != null)
                    rs.close();
                if (st != null)
                    st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return mailArray;
    }

    public static String logIn() {
        ArrayList<String> mailArray = new ArrayList<>();
        ArrayList<String> passArray = new ArrayList<>();
        Statement st = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;
        try {
            st = con.createStatement();
            String sql1 = "SELECT email FROM users";
            rs1 = st.executeQuery(sql1);
            while (rs1.next()) {
                String mailUser = rs1.getString("email");
                mailArray.add(mailUser);
            }

            String sql2 = "SELECT password FROM users";
            rs2 = st.executeQuery(sql2);
            while (rs2.next()) {
                String password = rs2.getString("password");
                passArray.add(password);
            }

            do {
                System.out.println("Enter your email");
                String emailInput = sc.nextLine();
                System.out.println("Enter your password");
                String passInput = sc.nextLine();

                if (mailArray.contains(emailInput) && passArray.contains(passInput)) {
                    System.out.println("Login Successful");
                    break;
                } else {
                    System.out.println("Incorrect mail or password. Try again!");
                }

            } while (true);

        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } finally {
            try {
                // Result set and Statement are closing
                if (rs1 != null)
                    rs1.close();
                if (rs2 != null)
                    rs2.close();
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String register() {
        ArrayList<String> mailArray = new ArrayList<>();
        Statement st = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            st = con.createStatement();
            String sql1 = "SELECT email FROM users";
            rs = st.executeQuery(sql1);
            while (rs.next()) {
                String mailUser = rs.getString("email");
                mailArray.add(mailUser);
            }
            do {
                System.out.println("Enter your email");
                String emailInput = sc.nextLine();
                System.out.println("Enter your password");
                String passInput = sc.nextLine();
                System.out.println("Enter your age");
                Integer ageInput = sc.nextInt();
                System.out.println("Enter your name");
                String nameInput = sc.nextLine();
                sc.nextLine();

                // Safe addition with Prepared class
                if (mailArray.contains(emailInput)) {
                    System.out.println("Email is already registered");
                } else {
                    String sql2 = "INSERT INTO users (user_name, user_age, email, password) VALUES (?, ?, ?, ?)";
                    ps = con.prepareStatement(sql2);
                    ps.setString(1, nameInput);
                    ps.setInt(2, ageInput);
                    ps.setString(3, emailInput);
                    ps.setString(4, passInput);
                    ps.executeUpdate();
                    System.out.println("Registration Successful");
                    break;
                }

            } while (true);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } finally {
            // Result set and Statement are closing
            try {
                if (rs != null)
                    rs.close();
                if (st != null)
                    st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
