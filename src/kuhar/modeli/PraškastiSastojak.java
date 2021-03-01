package kuhar.modeli;

import kuhar.izuzeci.PogresnaJedinica;

public class PraškastiSastojak extends Sastojak{
    private String jedinica;

    public PraškastiSastojak(String naziv, double kolicina, Jedinice jedinica) {
        super(naziv, kolicina);
        if (jedinica!= Jedinice.gr && jedinica!= Jedinice.kg)
            throw new PogresnaJedinica();
        this.jedinica = jedinica.getJedinica(jedinica.ordinal());
    }

    @Override
    public String getJedinica() {
        return jedinica;
    }

    @Override
    public void setJedinica(String jedinica) {
        this.jedinica = jedinica;
    }
}
