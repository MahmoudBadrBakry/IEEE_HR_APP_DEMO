
package ieee.hr.app;

public class Member {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    private String name ;
    private int acadimicYear;
    private String mail;
    private String phone;
    private String committee;
    private String info;

    
    public Member(int id ,String name, int acadimicYear, String mail, String phone, String committee,String info) {
        this.id = id;
        this.name = name;
        this.acadimicYear = acadimicYear;
        this.mail = mail;
        this.phone = phone;
        this.committee = committee;
        this.info = info;
    }
    public Member(int  id ,String name, int acadimicYear, String mail, String phone, String committee) {
        this(id,name,acadimicYear,mail,phone,committee," ");
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAcadimicYear() {
        return acadimicYear;
    }

    public void setAcadimicYear(int acadimicYear) {
        this.acadimicYear = acadimicYear;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCommittee() {
        return committee;
    }

    public void setCommittee(String committee) {
        this.committee = committee;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
    
    
}
