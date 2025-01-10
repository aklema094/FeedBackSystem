
package feedbacksystem;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;
public class FeedBackSystem {
    
    private final static String url = "jdbc:mysql://localhost:3306/feedback";
    private final static String userName = "root";
    private final static String password = "29344";
    
    
    
    public static void main(String[] args) throws ClassNotFoundException{
        
        Scanner sc = new Scanner(System.in);
        
        Class.forName("com.mysql.cj.jdbc.Driver");
        try{
            Connection con = DriverManager.getConnection(url,userName,password); 
               
        }catch(SQLException e){
            e.printStackTrace();     
        }
        
        
      
    }
    
}
