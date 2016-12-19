
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SingletonSQL {
    private static SingletonSQL instance = null;
    private Connection conn=null;
    SingletonSQL(){}
    
    public SingletonSQL access(){
        if (SingletonSQL.instance==null){
            return instance = new SingletonSQL();
        }
        return instance;
    }
    private void createConnection(String url, String login, String password){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, login, password);
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Błąd połączenia");
        }
    }
    private ResultSet exequteQuery(String query){
        if (conn==null){
            System.out.println("No connection");
            return null;
        }
        try {PreparedStatement p = conn.prepareStatement(query);
            ResultSet result = p.executeQuery();
                return result;
        } catch (SQLException ex) {
            System.out.println("Error in query" + ex.getMessage());
        }
        return null;
    }
    private void closeConnection(){
        if (conn==null){
            System.out.println("Connection isn't open");
            return;
        }
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(SingletonSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private ResultSet getAll(String table){
        if (conn==null){
            System.out.println("No connection");
        }
        try {PreparedStatement p = conn.prepareStatement("SELECT * FROM "+table);
            ResultSet result = p.executeQuery();
                return result;
        } catch (SQLException ex) {
            System.out.println("Error in query" + ex.getMessage());
        }
        return null;
    }
    private int executeUpdate(String query){
        if (conn==null){
            System.out.println("No connection");
            return 0;
        }
        try
            (PreparedStatement p = conn.prepareStatement(query)){
            return p.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error in query" + ex.getMessage());
        }
        return 0;
    }
}

