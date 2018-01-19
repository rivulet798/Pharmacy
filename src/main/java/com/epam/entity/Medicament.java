package com.epam.entity;

import java.util.Objects;

public class Medicament implements Id<Integer>{
    private Integer id;
    private String name;
    private String producer;
    private float price;
    private boolean prescription;
    private String image;
    private boolean availability;
    private String modeOfApplication;
    private String contraindications;
    private String sideEffects;

    public Medicament() {
    }

    public Medicament(Integer id, String name, String producer, float price, boolean prescription, String image, boolean availability, String modeOfApplication, String contraindications, String sideEffects) {
        this.id = id;
        this.name = name;
        this.producer = producer;
        this.price = price;
        this.prescription = prescription;
        this.image = image;
        this.availability = availability;
        this.modeOfApplication = modeOfApplication;
        this.contraindications = contraindications;
        this.sideEffects = sideEffects;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isPrescription() {
        return prescription;
    }

    public void setPrescription(boolean prescription) {
        this.prescription = prescription;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public String getModeOfApplication() {
        return modeOfApplication;
    }

    public void setModeOfApplication(String modeOfApplication) {
        this.modeOfApplication = modeOfApplication;
    }

    public String getContraindications() {
        return contraindications;
    }

    public void setContraindications(String contraindications) {
        this.contraindications = contraindications;
    }

    public String getSideEffects() {
        return sideEffects;
    }

    public void setSideEffects(String sideEffects) {
        this.sideEffects = sideEffects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medicament that = (Medicament) o;
        return Float.compare(that.price, price) == 0 &&
                prescription == that.prescription &&
                availability == that.availability &&
                Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(producer, that.producer) &&
                Objects.equals(image, that.image) &&
                Objects.equals(modeOfApplication, that.modeOfApplication) &&
                Objects.equals(contraindications, that.contraindications) &&
                Objects.equals(sideEffects, that.sideEffects);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, producer, price, prescription, image, availability, modeOfApplication, contraindications, sideEffects);
    }

    @Override
    public String toString() {
        return "Medicament{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", producer='" + producer + '\'' +
                ", price=" + price +
                ", prescription=" + prescription +
                ", image='" + image + '\'' +
                ", availability=" + availability +
                ", modeOfApplication='" + modeOfApplication + '\'' +
                ", contraindications='" + contraindications + '\'' +
                ", sideEffects='" + sideEffects + '\'' +
                '}';
    }
}
