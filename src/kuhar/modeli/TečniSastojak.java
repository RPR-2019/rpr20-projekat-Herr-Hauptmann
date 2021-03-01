package kuhar.modeli;

import kuhar.izuzeci.PogresnaJedinica;

public class TečniSastojak extends Sastojak{
    private String jedinica;

    public TečniSastojak(String naziv, double kolicina, Jedinice jedinica) {
        super(naziv, kolicina);
        if (jedinica!= Jedinice.dl && jedinica!= Jedinice.l && jedinica!= Jedinice.ml)
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
