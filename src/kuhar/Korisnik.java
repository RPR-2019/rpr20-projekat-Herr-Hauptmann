package kuhar;

public class Korisnik {
    private int id;
    private String ime;
    private String username;

    public Korisnik(int id, String ime, String username) {
        this.id = id;
        this.ime = ime;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
