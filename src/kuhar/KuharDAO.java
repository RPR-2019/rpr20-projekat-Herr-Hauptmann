package kuhar;

import kuhar.izuzeci.NemateOvlastiIzuzetak;
import kuhar.modeli.Korisnik;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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

    private PreparedStatement sviKorisniciUpit, korisnikUpit, dodajKorisnikaUpit, urediKorisnikaUpitSLozinkom, urediKorisnikaUpitBezLozinke, posljednjiKorisnikUpit, provjeriUsernameUpit, izbrisiKorisnikaUpit;

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
            sviKorisniciUpit = conn.prepareStatement("SELECT * FROM korisnici");
        } catch (SQLException e) {
            regenerisiBazu();
            try {
                sviKorisniciUpit = conn.prepareStatement("SELECT * FROM korisnici");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

        try {
            korisnikUpit = conn.prepareStatement("SELECT * FROM korisnici WHERE username=? AND password=?");
            dodajKorisnikaUpit = conn.prepareStatement("INSERT into korisnici VALUES(?,?,?,?,?)");
            urediKorisnikaUpitSLozinkom = conn.prepareStatement("UPDATE korisnici SET ime=?, username=?, password=?, admin=? WHERE id=?");
            posljednjiKorisnikUpit = conn.prepareStatement("SELECT max(id) FROM korisnici");
            provjeriUsernameUpit = conn.prepareStatement("SELECT * FROM korisnici WHERE username=?");
            urediKorisnikaUpitBezLozinke = conn.prepareStatement("UPDATE korisnici SET ime=?, username=?, admin=? WHERE id=?");
            izbrisiKorisnikaUpit = conn.prepareStatement("DELETE FROM korisnici WHERE id=?");
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

    private String hashFunkcija(String password)
    {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public void dodajKorisnika(String ime, String username, String password, boolean admin)
    {
        if (user == null)
            throw new NemateOvlastiIzuzetak();
        try{
            int id = posljednjiKorisnikUpit.executeQuery().getInt(1)+1;
            dodajKorisnikaUpit.setInt(1, id);
            dodajKorisnikaUpit.setString(2,ime);
            dodajKorisnikaUpit.setString(3,username);
            dodajKorisnikaUpit.setString(4,hashFunkcija(password));
            if (admin)
                dodajKorisnikaUpit.setInt(5, 1);
            else
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
            korisnikUpit.setString(2, hashFunkcija(password));
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

    public List<Korisnik> korisnici()
    {
        ArrayList<Korisnik> korisnici = new ArrayList<>();
        try {
            ResultSet rs = sviKorisniciUpit.executeQuery();
            while (rs.next()){
                Korisnik korisnik = new Korisnik(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getBoolean(5));
                korisnici.add(korisnik);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return korisnici;
    }

    public boolean provjeriUsername(String username) {
        try {
            provjeriUsernameUpit.setString(1, username);
            ResultSet rs = provjeriUsernameUpit.executeQuery();
            return rs.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public void urediKorisnika(int id, String ime, String username, String password, boolean admin)
    {
        if (user == null)
            throw new NemateOvlastiIzuzetak();
        try {
            urediKorisnikaUpitSLozinkom.setString(1, ime);
            urediKorisnikaUpitSLozinkom.setString(2, username);
            urediKorisnikaUpitSLozinkom.setString(3, hashFunkcija(password));
            if (admin)
                urediKorisnikaUpitSLozinkom.setInt(4, 1);
            else
                urediKorisnikaUpitSLozinkom.setInt(4, 0);
            urediKorisnikaUpitSLozinkom.setInt(5, id);
            urediKorisnikaUpitSLozinkom.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void urediKorisnika(int id, String ime, String username, boolean admin)
    {
        if (user == null)
            throw new NemateOvlastiIzuzetak();
        try {
            urediKorisnikaUpitBezLozinke.setString(1, ime);
            urediKorisnikaUpitBezLozinke.setString(2, username);
            if (admin)
                urediKorisnikaUpitBezLozinke.setInt(3, 1);
            else
                urediKorisnikaUpitBezLozinke.setInt(3, 0);
            urediKorisnikaUpitBezLozinke.setInt(4, id);
            urediKorisnikaUpitBezLozinke.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void izbrisiKorisnika(int id)
    {
        try{
            izbrisiKorisnikaUpit.setInt(1, id);
            izbrisiKorisnikaUpit.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}