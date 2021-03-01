package kuhar.modeli;

import kuhar.izuzeci.PogresnaJedinica;

public class JediničniSastojak extends Sastojak{
    private String jedinica;

    public JediničniSastojak(String naziv, double kolicina, Jedinice jedinica) {
        super(naziv, kolicina);
        if (jedinica!= Jedinice.komad && jedinica!= Jedinice.pakovanje)
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
