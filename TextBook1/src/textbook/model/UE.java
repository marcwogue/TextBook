package textbook.model;

public class UE {
    private String codeUE;
    private String intitule;

    public UE() {
    }

    public UE(String codeUE, String intitule) {
        this.codeUE = codeUE;
        this.intitule = intitule;
    }

    public String getCodeUE() {
        return codeUE;
    }

    public void setCodeUE(String codeUE) {
        this.codeUE = codeUE;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    @Override
    public String toString() {
        return codeUE + " - " + intitule;
    }
}
