package feedbacksystem;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FeedBackSystem {

    private final static String url = "jdbc:mysql://localhost:3306/feedback";
    private final static String userName = "root";
    private final static String password = "29344";

    public static void main(String[] args) throws ClassNotFoundException, InterruptedException {

        Scanner sc = new Scanner(System.in);

        Class.forName("com.mysql.cj.jdbc.Driver");
        try {
            Connection con = DriverManager.getConnection(url, userName, password);

            while (true) {
                System.out.println("WELCOME TO FEEDBACK SYSTEM");
                System.out.println("==========================");
                System.out.println("1. LogIn");
                System.out.println("2. Sign Up");
                System.out.println("3. Exit");
                System.out.print("Enter your choice : ");
                int ch = sc.nextInt();
                switch (ch) {
                    case 1:
                        logIn(con, sc);
                        System.out.println("");
                        break;
                    case 2:
                        registration(con, sc);
                        System.out.println("");
                        break;
                    case 3:
                        exit("Existing System");
                        System.out.println("");
                        return;
                    default:
                        System.out.println("\nInvalid Choice!!!!\n");
                        break;
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void exit(String s) throws InterruptedException {
        System.out.print(s);
        for (int i = 0; i < 7; i++) {
            System.out.print(". ");
            Thread.sleep(250);
        }
        System.out.println("");
    }

    public static void registration(Connection con, Scanner sc) throws SQLException {
        sc.nextLine();
        System.out.print("Enter name : ");
        String name = sc.nextLine();
        System.out.print("Enter email : ");
        String email = sc.nextLine();
        while (!email.contains("@")) {
            System.out.print("Enter a valid email : ");
            email = sc.nextLine();
        }
        if (!isExist(email, con)) {
            System.out.print("Enter password : ");
            String pass = sc.nextLine();
            while (pass.length() < 6) {
                System.out.println("Error: Password must be more than 6 characters.");
                pass = sc.nextLine();
            }

            PreparedStatement ps = con.prepareStatement("INSERT INTO users(name,email,role,password) VALUES(?,?,'user',?);");
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, pass);
            int r = ps.executeUpdate();
            if (r > 0) {
                System.out.println("Sign Up successfully.");
            } else {
                System.out.println("Failed to Sign Up.");
            }

        } else {
            System.out.println("An account already exist with this email, Try with new email!!!");
        }

    }

    public static boolean isExist(String email, Connection con) throws SQLException {
        PreparedStatement p = con.prepareStatement("SELECT * FROM users WHERE email = ?;");
        p.setString(1, email);
        if (p.executeQuery().next()) {
            return true;
        }
        return false;
    }

    public static void logIn(Connection con, Scanner sc) throws SQLException, InterruptedException {
        sc.nextLine();
        System.out.print("Enter email : ");
        String em = sc.nextLine();
        System.out.print("Enter password : ");
        String pass = sc.nextLine();

        PreparedStatement p = con.prepareStatement("SELECT * FROM users WHERE email = ? AND password = ?;");
        p.setString(1, em);
        p.setString(2, pass);
        ResultSet rs = p.executeQuery();
        if (rs.next()) {
            System.out.println("LOg In Successfully " + rs.getString("name"));
            String role = rs.getString("role");
            if (role.equals("admin")) {
                adminMenu(con, sc);

            } else {
                userMenu(con, sc, rs.getInt("id"));
            }

        } else {
            System.out.println("Invalid credentials!!! Try Again.");
        }

    }

    public static void adminMenu(Connection con, Scanner sc) throws InterruptedException {

        sc.nextLine();
        while (true) {
            System.out.println("          Admin Menu");
            System.out.println("===============================");
            System.out.println("1. View All FeedBack");
            System.out.println("2. Generate DeshBoard");
            System.out.println("3. Log out ");
            System.out.print("Enter you Choice : ");
            int c = sc.nextInt();
            switch (c) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    exit("Logging out");
                    return;
                default:
                    System.out.println("Invalid choice !!!");
                    break;
            }
        }

    }

    public static void userMenu(Connection con, Scanner sc, int id) throws InterruptedException, SQLException {

        sc.nextLine();
        while (true) {
            System.out.println("          User Menu");
            System.out.println("===============================");
            System.out.println("1. Submit Feedback");
            System.out.println("2. View My Feedback");
            System.out.println("3. Logout");
            System.out.print("Enter you Choice : ");
            int c = sc.nextInt();
            switch (c) {
                case 1:
                    submitFeedback(con, sc, id);
                    break;
                case 2:
                    viewMyFeedBack(con, id);
                    break;
                case 3:
                    exit("Logging out");
                    return;
                default:
                    System.out.println("Invalid choice !!!");
                    break;
            }
        }

    }

    private static void submitFeedback(Connection conn, Scanner scanner, int userId) throws SQLException {
        System.out.print("Enter rating (1-5): ");
        int rating = scanner.nextInt();
        scanner.nextLine(); // consume newline

        System.out.print("Enter comments: ");
        String comments = scanner.nextLine();

        String query = "INSERT INTO feedbackT (user_id, rating, comments) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, rating);
            pstmt.setString(3, comments);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Feedback submitted successfully!");
            } else {
                System.out.println("Failed to submit feedback. Try again.");
            }
        }
    }

    public static void viewMyFeedBack(Connection con, int id) throws SQLException {

        PreparedStatement pst = con.prepareStatement("SELECT * FROM feedbackT WHERE user_id = ?");
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            System.out.println("Rating : " + rs.getInt("rating") + "     Comment : " + rs.getString("comments"));
        }

    }

}
