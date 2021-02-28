package kuhar;

import kuhar.modeli.Korisnik;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class KuharDAO {
    private static KuharDAO instance;
    private Connection conn;
    private Korisnik user;

    public Korisnik getUser() {
        return user;
    }

    public void setUser(Korisnik user) {
        this.user = user;
    }

    private PreparedStatement testniUpit, korisnikUpit, dodajKorisnikaUpit;

    public static KuharDAO getInstance() {
        if (instance == null) instance = new KuharDAO();
        return instance;
    }

    private KuharDAO() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:baza.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            testniUpit = conn.prepareStatement("SELECT * FROM korisnici");
        } catch (SQLException e) {
            regenerisiBazu();
            try {
                testniUpit = conn.prepareStatement("SELECT * FROM korisnici");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

        try {
            korisnikUpit = conn.prepareStatement("SELECT * FROM korisnici WHERE username=? AND password=?");
            dodajKorisnikaUpit = conn.prepareStatement("INSERT into korisnici VALUES(?,?,?,?,?)");
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    public static void removeInstance() {
        if (instance == null) return;
        instance.close();
        instance = null;
    }

    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void regenerisiBazu() {
        Scanner ulaz = null;
        try {
            ulaz = new Scanner(new FileInputStream("baza.db.sql"));
            String sqlUpit = "";
            while (ulaz.hasNext()) {
                sqlUpit += ulaz.nextLine();
                if (sqlUpit.length() > 1 && sqlUpit.charAt(sqlUpit.length() - 1) == ';') {
                    try {
                        Statement stmt = conn.createStatement();
                        stmt.execute(sqlUpit);
                        sqlUpit = "";
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            ulaz.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void dodajKorisnika(String username)
    {
        try{
            dodajKorisnikaUpit.setInt(1, 2);
            dodajKorisnikaUpit.setString(2,"Test");
            dodajKorisnikaUpit.setString(3,username);
            dodajKorisnikaUpit.setString(4,"Test");
            dodajKorisnikaUpit.setInt(5, 0);
            dodajKorisnikaUpit.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Korisnik dajKorisnika(String username, String password) {
        try {
            korisnikUpit.setString(1, username);
            korisnikUpit.setString(2, password);
            ResultSet rs = korisnikUpit.executeQuery();
            if (!rs.next())
            {
                return null;
            }
            return new Korisnik(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getBoolean(5));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}