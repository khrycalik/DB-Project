package user;

public class Admin {
    // <TableColumn fx:id="wydawnictwo" prefWidth="90.0" text="Wydawnictwa" />
    // <TableColumn fx:id="kategoria" prefWidth="48.7999267578125" text="Kategorie"
    // />
    // <TableColumn fx:id="magazyn" prefWidth="120.0" text="Numer Magazyny" />
    // <TableColumn fx:id="jezyk" prefWidth="103.99993896484375" text="Języki" />
    // <TableColumn fx:id="uniwersum" prefWidth="272.79997558593755" text="Uniwersa"
    // />
    private String autor;
    private String wydawnictwo;
    private String kategoria;
    private String magazyn;
    private String jezyk;
    private String uniwersum;
    private String pole;

    public Admin(String autor, String wydawnictwo, String kategoria, String magazyn, String jezyk, String uniwersum) {
        this.autor = autor;
        this.wydawnictwo = wydawnictwo;
        this.kategoria = kategoria;
        this.magazyn = magazyn;
        this.jezyk = jezyk;
        this.uniwersum = uniwersum;
    }

    public Admin(String pole) {
        this.pole = pole;
    }

    public String getPole() {
        return pole;
    }

    public void setPole(String pole) {
        this.pole = pole;
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

    public String getKategoria() {
        return kategoria;
    }

    public void setKategoria(String kategoria) {
        this.kategoria = kategoria;
    }

    public String getMagazyn() {
        return magazyn;
    }

    public void setMagazyn(String magazyn) {
        this.magazyn = magazyn;
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

}
