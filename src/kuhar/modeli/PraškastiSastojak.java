package kuhar.modeli;

public class PraškastiSastojak extends Sastojak{
    public PraškastiSastojak(String naziv, double kolicina) {
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
