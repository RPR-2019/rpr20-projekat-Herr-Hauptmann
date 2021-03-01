package kuhar.modeli;

import kuhar.izuzeci.PogresnaJedinica;

public enum Jedinice {
    kg,
    gr,
    dl,
    ml,
    l,
    komad,
    pakovanje;

    private static final String[] jedinice = {"kg", "gr", "dl", "ml", "l", "komad", "pakovanje"};
    static String getJedinica(int i) {
        if (i < 0 || i > 7)
            throw new PogresnaJedinica();
        return jedinice[i];
    }
}
