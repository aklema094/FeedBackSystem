
package feedbacksystem;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;
public class FeedBackSystem {
    
    private final static String url = "jdbc:mysql://localhost:3306/feedback";
    private final static String userName = "root";
    private final static String password = "29344";
    
    
    
    public static void main(String[] args) throws ClassNotFoundException, InterruptedException{
        
        Scanner sc = new Scanner(System.in);
        
        Class.forName("com.mysql.cj.jdbc.Driver");
        try{
            Connection con = DriverManager.getConnection(url,userName,password); 
            
            while(true){
                System.out.println("WELCOME TO FEEDBACK SYSTEM");
                System.out.println("==========================");
                System.out.println("1. LogIn");
                System.out.println("2. Sign Up");
                System.out.println("3. Exit");
                System.out.print("Enter your choice : ");
                int ch = sc.nextInt();
                switch(ch){
                    case 1: 
                        break;
                    case 2:
                        break;
                    case 3:
                        exit();
                        return;
                    default :
                        System.out.println("");
                        System.out.println("Invalid Choice!!!!");
                        break;
                }
                
                
            }
               
        }catch(SQLException e){
            e.printStackTrace();     
        }
        
        
      
    }
    
    public static void exit() throws InterruptedException{
        System.out.print("Existing System");
        for (int i = 0; i < 7; i++) {
            System.out.print(". ");
            Thread.sleep(250);   
        }
        System.out.println("");
    }
    
}
