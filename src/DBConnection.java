import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

   public class DBConnection
{
    private static DBConnection singleInstance;
    private static DataSource dataSource;
    private static Connection dbConnect;

    private DBConnection()
    {
        try
        {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/db");

            try {
                dbConnect = dataSource.getConnection();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public static DBConnection getInstance()
    {
        if(singleInstance == null) {
            synchronized (DBConnection.class) {
                if(singleInstance == null) {
                    singleInstance = new DBConnection();
                }
            }
        }

        return singleInstance;
    }

    public static Connection getConnInst()
    {
        try {
            dbConnect = dataSource.getConnection();
        }
        catch (SQLException e1) {
            e1.printStackTrace();
        }

        if(dbConnect == null) {
            try
            {
                Context initContext = new InitialContext();
                Context envContext  = (Context) initContext.lookup("java:/comp/env");
                dataSource = (DataSource) envContext.lookup("jdbc/db");

                try {
                    dbConnect = dataSource.getConnection();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            catch (NamingException e) {
                e.printStackTrace();
            }
        }

        return dbConnect;
    }
}
