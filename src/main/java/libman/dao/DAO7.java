package libman.dao;

import libman.db.DBConnection7;
import java.sql.Connection;

public abstract class DAO7 {
    protected Connection con;

    public DAO7() {
        try {
            this.con = DBConnection7.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DAO7(Connection con) {
        this.con = con;
    }

    public Connection getConnection() {
        try {
            if (this.con == null || this.con.isClosed()) {
                this.con = DBConnection7.getConnection();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.con;
    }
}

