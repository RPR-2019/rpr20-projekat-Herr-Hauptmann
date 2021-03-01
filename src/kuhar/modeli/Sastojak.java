package kuhar.modeli;

public abstract class Sastojak {
    String naziv;
    double kolicina;

    public Sastojak(String naziv, double kolicina) {
        this.naziv = naziv;
        this.kolicina = kolicina;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public double getKolicina() {
        return kolicina;
    }

    public void setKolicina(double kolicina) {
        this.kolicina = kolicina;
    }

    public abstract String getJedinica();
    public abstract void setJedinica();

}
