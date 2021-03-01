package kuhar.modeli;

public class TečniSastojak extends Sastojak{
    public TečniSastojak(String naziv, double kolicina) {
        super(naziv, kolicina);
    }

    @Override
    public String getJedinica() {
        return null;
    }

    @Override
    public void setJedinica() {

    }
}
