package sample.Strategy;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectorInterface {
     static Connection connect() throws SQLException, ClassNotFoundException {
          return null;
     }

      static ConnectorInterface getInstance() {
          return null;
     }
}
