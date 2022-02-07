package user;

public class Book {
    private String identyfikator;
    private String tytul;
    private String autor;
    private String wydawnictwo;
    private String dataWyda;
    private String sztuk;
    private String cena;
    private String opis;
    private String kategoria;
    private String jezyk;
    private String uniwersum;
    private String strony;
    private String cenaWyp;

    public Book(String identyfikator, String tytul, String autor, String wydawnictwo, String dataWyda, String sztuk,
            String cena, String opis, String kategoria, String jezyk, String uniwersum, String strony, String cenaWyp) {
        this.identyfikator = identyfikator;
        this.tytul = tytul;
        this.autor = autor;
        this.wydawnictwo = wydawnictwo;
        this.dataWyda = dataWyda;
        this.sztuk = sztuk;
        this.cena = cena;
        this.opis = opis;
        this.kategoria = kategoria;
        this.jezyk = jezyk;
        this.uniwersum = uniwersum;
        this.strony = strony;
        this.cenaWyp = cenaWyp;
    }

    public Book(String identyfikator, String tytul, String autor, String wydawnictwo, String sztuk,
            String cena, String cenaWyp) {
        this.identyfikator = identyfikator;
        this.tytul = tytul;
        this.autor = autor;
        this.wydawnictwo = wydawnictwo;
        this.sztuk = sztuk;
        this.cena = cena;
        this.cenaWyp = cenaWyp;
    }

    public String getIdentyfikator() {
        return identyfikator;
    }

    public void setIdentyfikator(String identyfikator) {
        this.identyfikator = identyfikator;
    }

    public String getTytul() {
        return tytul;
    }

    public void setTytul(String tytul) {
        this.tytul = tytul;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getWydawnictwo() {
        return wydawnictwo;
    }

    public void setWydawnictwo(String wydawnictwo) {
        this.wydawnictwo = wydawnictwo;
    }

    public String getDataWyda() {
        return dataWyda;
    }

    public void setDataWyda(String dataWyda) {
        this.dataWyda = dataWyda;
    }

    public String getSztuk() {
        return sztuk;
    }

    public void setSztuk(String sztuk) {
        this.sztuk = sztuk;
    }

    public String getCena() {
        return cena;
    }

    public void setCena(String cena) {
        this.cena = cena;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getKategoria() {
        return kategoria;
    }

    public void setKategoria(String kategoria) {
        this.kategoria = kategoria;
    }

    public String getJezyk() {
        return jezyk;
    }

    public void setJezyk(String jezyk) {
        this.jezyk = jezyk;
    }

    public String getUniwersum() {
        return uniwersum;
    }

    public void setUniwersum(String uniwersum) {
        this.uniwersum = uniwersum;
    }

    public String getStrony() {
        return strony;
    }

    public void setStrony(String strony) {
        this.strony = strony;
    }

    public String getCenaWyp() {
        return cenaWyp;
    }

    public void setCenaWyp(String cenaWyp) {
        this.cenaWyp = cenaWyp;
    }

}
