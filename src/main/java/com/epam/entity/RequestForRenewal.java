package com.epam.entity;

import java.util.Objects;

public class RequestForRenewal {
    private int id;
    private int idPrescription;
    private int complete;

    public RequestForRenewal() {
    }

    public RequestForRenewal(Integer id, int idPrescription, int complete) {
        this.id = id;
        this.idPrescription = idPrescription;
        this.complete = complete;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdPrescription() {
        return idPrescription;
    }

    public void setIdPrescription(int idPrescription) {
        this.idPrescription = idPrescription;
    }

    public int getComplete() {
        return complete;
    }

    public void setComplete(int complete) {
        this.complete = complete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestForRenewal that = (RequestForRenewal) o;
        return idPrescription == that.idPrescription &&
                complete == that.complete &&
                Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, idPrescription, complete);
    }

    @Override
    public String toString() {
        return "RequestForRenewal{" +
                "id=" + id +
                ", idPrescription=" + idPrescription +
                ", complete=" + complete +
                '}';
    }
}
