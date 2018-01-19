package com.epam.dto;

import java.sql.Date;

public class PrescriptionDto {
    private int idPrescription;
    private String doctorName;
    private String doctorSurname;
    private String medicamentName;
    private int number;
    private int dosage;
    private int idDosage;
    private Date dateOfIssue;
    private Date dateOfCompletion;
    private boolean valid;

    public PrescriptionDto() {
    }

    public int getIdPrescription() {
        return idPrescription;
    }

    public void setIdPrescription(int idPrescription) {
        this.idPrescription = idPrescription;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorSurname() {
        return doctorSurname;
    }

    public void setDoctorSurname(String doctorSurname) {
        this.doctorSurname = doctorSurname;
    }

    public String getMedicamentName() {
        return medicamentName;
    }

    public void setMedicamentName(String medicamentName) {
        this.medicamentName = medicamentName;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getDosage() {
        return dosage;
    }

    public void setDosage(int dosage) {
        this.dosage = dosage;
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

    public void setIdDosage(int idDosage) {
        this.idDosage = idDosage;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
