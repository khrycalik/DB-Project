package user;

public class Klient {
    private String name;
    private String surname;
    private String tytul;
    private String cena;
    private String email;
    private String phoneNumber;
    private String statusZam;
    private String dataZam;
    private String statusPl;
    private String adresDost;
    private String pole;
    private String kto;

    /**
     * Constructs Patient object
     * 
     * @param patientName    [String] - imię pacjenta
     * @param patientSurname [String] - nazwisko pacjenta
     * @param date           [String] - data wizyty
     * @param diseases       [String] - choroby pacjenta
     */
    public Klient(String patientName, String patientSurname, String statusZam, String diseases) {
        this.tytul = patientName;
        this.cena = patientSurname;
        this.statusZam = statusZam;
        this.dataZam = diseases;
    }

    /**
     * Constructs Patient object
     * 
     * @param name        [String] - imię pacjenta
     * @param surname     [String] - nazwisko pacjenta
     * @param age         [int] - wiek pacjenta
     * @param phoneNumber [String] - numer telefonu pacjenta
     * @param date        [String] - data wizyty
     * @param diseases    [String] - choroby pacjenta
     */

    public Klient(String id, String tytul, String cena, String statusZam, String dataZam, String statusPl,
            String adresDost) {
        this.pole = id;
        this.tytul = tytul;
        this.cena = cena;
        this.statusPl = statusPl;
        this.adresDost = adresDost;
        this.statusZam = statusZam;
        this.dataZam = dataZam;
    }

    public Klient(String tytul, String cena, String statusZam, String dataZam, String statusPl, String adresDost) {
        this.tytul = tytul;
        this.cena = cena;
        this.statusPl = statusPl;
        this.adresDost = adresDost;
        this.statusZam = statusZam;
        this.dataZam = dataZam;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getTytul() {
        return tytul;
    }

    public void setTytul(String tytul) {
        this.tytul = tytul;
    }

    public String getCena() {
        return cena;
    }

    public void setCena(String cena) {
        this.cena = cena;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStatusZam() {
        return statusZam;
    }

    public void setStatusZam(String statusZam) {
        this.statusZam = statusZam;
    }

    public String getDataZam() {
        return dataZam;
    }

    public void setDataZam(String dataZam) {
        this.dataZam = dataZam;
    }

    public String getStatusPl() {
        return statusPl;
    }

    public void setStatusPl(String statusPl) {
        this.statusPl = statusPl;
    }

    public String getAdresDost() {
        return adresDost;
    }

    public void setAdresDost(String adresDost) {
        this.adresDost = adresDost;
    }

    public String getPole() {
        return pole;
    }

    public void setPole(String pole) {
        this.pole = pole;
    }

}