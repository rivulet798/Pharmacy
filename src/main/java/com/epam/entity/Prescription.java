package com.epam.entity;

import java.sql.Date;
import java.util.Objects;


public class Prescription {
    private int id;
    private int idDoctor;
    private int idUser;
    private int idMedicament;
    private Date dateOfIssue;
    private Date dateOfCompletion;
    private int idDosage;
    private int number;
    private boolean valid;

    public Prescription() {
    }

    public Prescription(int id, int idDoctor, int idUser, int idMedicament, Date dateOfIssue, Date dateOfCompletion, boolean status, int idDosage, int number) {
        this.id = id;
        this.idDoctor = idDoctor;
        this.idUser = idUser;
        this.idMedicament = idMedicament;
        this.dateOfIssue = dateOfIssue;
        this.dateOfCompletion = dateOfCompletion;
        this.idDosage = idDosage;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdDoctor() {
        return idDoctor;
    }

    public void setDoctorId(int idDoctor) {
        this.idDoctor = idDoctor;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setUserId(int idUser) {
        this.idUser = idUser;
    }

    public int getIdMedicament() {
        return idMedicament;
    }

    public void setIdMedicament(int idMedicament) {
        this.idMedicament = idMedicament;
    }

    public Date getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(Date dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public Date getDateOfCompletion() {
        return dateOfCompletion;
    }

    public void setDateOfCompletion(Date dateOfCompletion) {
        this.dateOfCompletion = dateOfCompletion;
    }

    public int getIdDosage() {return idDosage;}

    public void setIdDosage(int idDosage) {this.idDosage = idDosage;}

    public int getNumber() {return number;}

    public void setNumber(int number) {this.number = number;}
    public void setIdDoctor(int idDoctor) {
        this.idDoctor = idDoctor;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prescription that = (Prescription) o;
        return (id == that.id) &&
                (idMedicament == that.idMedicament) &&
                (idDoctor == that.idDoctor) &&
                (idUser == that.idUser) &&
                Objects.equals(dateOfIssue, that.dateOfIssue) &&
                Objects.equals(dateOfCompletion, that.dateOfCompletion);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, idDoctor, idUser, idMedicament, dateOfIssue, dateOfCompletion);
    }

    @Override
    public String toString() {
        return "Prescription{" +
                "id=" + id +
                ", doctorLogin='" + idDoctor + '\'' +
                ", doctorLogin='" + idUser + '\'' +
                ", idMedicament=" + idMedicament +
                ", dateOfIssue=" + dateOfIssue +
                ", dateOfCompletion=" + dateOfCompletion +
                ", idDosage=" + idDosage +
                ", number=" + number +
                '}';
    }
}
