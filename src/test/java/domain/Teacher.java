package domain;

import java.util.Date;

public class Teacher {

    /**
     * tno
     */
    private String tno;

    /**
     * tname
     */
    private String tName;

    /**
     * tsex
     */
    private String tSex;

    /**
     * tbirthday
     */
    private Date tBirthday;

    /**
     * prof
     */
    private String prof;

    /**
     * depart
     */
    private String depart;

    public String getTno() {
        return tno;
    }

    public void setTno(String tno) {
        this.tno = tno;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public String gettSex() {
        return tSex;
    }

    public void settSex(String tSex) {
        this.tSex = tSex;
    }

    public Date gettBirthday() {
        return tBirthday;
    }

    public void settBirthday(Date tBirthday) {
        this.tBirthday = tBirthday;
    }

    public String getProf() {
        return prof;
    }

    public void setProf(String prof) {
        this.prof = prof;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }
}
