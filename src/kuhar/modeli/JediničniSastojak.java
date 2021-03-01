package kuhar.modeli;

public class JediničniSastojak extends Sastojak{


    public JediničniSastojak(String naziv, double kolicina) {
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
