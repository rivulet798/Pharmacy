package com.epam.entity;

import java.util.Objects;

public class Order {
    private int id;
    private int idUser;
    private int idMedicament;
    private int number;
    private int idDosage;
    private int idOrderStatus;
    private int idPrescription;

    public Order() {
    }

    public Order(int id, int idUser, int idMedicament, int number, int idDosage, int idOrderStatus, int idPrescription) {
        this.id = id;
        this.idUser = idUser;
        this.idMedicament = idMedicament;
        this.number = number;
        this.idDosage = idDosage;
        this.idOrderStatus = idOrderStatus;
        this.idPrescription = idPrescription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdMedicament() {
        return idMedicament;
    }

    public void setIdMedicament(int idMedicament) {
        this.idMedicament = idMedicament;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getIdDosage() {
        return idDosage;
    }

    public void setIdDosage(int idDosage) {
        this.idDosage = idDosage;
    }

    public int getIdOrderStatus() {
        return idOrderStatus;
    }

    public void setIdOrderStatus(int idOrderStatus) {
        this.idOrderStatus = idOrderStatus;
    }

    public int getIdPrescription() {
        return idPrescription;
    }

    public void setIdPrescription(int idPrescription) {
        this.idPrescription = idPrescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return (id == order.id) &&
                (idMedicament == order.idMedicament) &&
                (number == order.number) &&
                (idDosage == order.idDosage) &&
                (idOrderStatus == order.idOrderStatus) &&
                (idUser == order.idUser) &&
                (idPrescription == order.idPrescription);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, idUser, idMedicament, number, idDosage, idOrderStatus, idPrescription);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", idUser='" + idUser + '\'' +
                ", idMedicament=" + idMedicament +
                ", number=" + number +
                ", idDosage=" + idDosage +
                ", idOrderStatus=" + idOrderStatus +
                ", idPrescription=" + idPrescription +
                '}';
    }
}
