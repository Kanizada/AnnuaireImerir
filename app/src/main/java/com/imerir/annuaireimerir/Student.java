package com.imerir.annuaireimerir;

/**
 * Created by Axel Zapata on 09/01/2017.
 * For Imerir.
 * axel.zapata@imerir.com
 */
public class Student {

    private String firstName;
    private String lastName;
    private String address;
    private Enterprise enterprise;
    private Formation formation;
    private Promotion promotion;
    private Boolean hasEnterprise;

    public Student(){

    }

    public Student(String firstName, String lastName, String address, Enterprise enterprise, Formation formation, Promotion promotion, Boolean hasEnterprise) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.enterprise = enterprise;
        this.formation = formation;
        this.promotion = promotion;
        this.hasEnterprise = hasEnterprise;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Enterprise getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
    }

    public Formation getFormation() {
        return formation;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public Boolean getHasEnterprise() {
        return hasEnterprise;
    }

    public void setHasEnterprise(Boolean hasEnterprise) {
        this.hasEnterprise = hasEnterprise;
    }
}
