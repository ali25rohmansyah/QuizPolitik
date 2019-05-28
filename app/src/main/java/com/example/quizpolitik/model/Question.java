package com.example.quizpolitik.model;

public class Question {

    private String Image, JawabanA, jawabanB, JawabanBenar, JawabanC, JawabanD, Pertanyaan;

    public Question() {
    }

    public Question(String image, String jawabanA, String jawabanB, String jawabanBenar, String jawabanC, String jawabanD, String pertanyaan) {
        Image = image;
        JawabanA = jawabanA;
        this.jawabanB = jawabanB;
        JawabanBenar = jawabanBenar;
        JawabanC = jawabanC;
        JawabanD = jawabanD;
        Pertanyaan = pertanyaan;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getJawabanA() {
        return JawabanA;
    }

    public void setJawabanA(String jawabanA) {
        JawabanA = jawabanA;
    }

    public String getJawabanB() {
        return jawabanB;
    }

    public void setJawabanB(String jawabanB) {
        this.jawabanB = jawabanB;
    }

    public String getJawabanBenar() {
        return JawabanBenar;
    }

    public void setJawabanBenar(String jawabanBenar) {
        JawabanBenar = jawabanBenar;
    }

    public String getJawabanC() {
        return JawabanC;
    }

    public void setJawabanC(String jawabanC) {
        JawabanC = jawabanC;
    }

    public String getJawabanD() {
        return JawabanD;
    }

    public void setJawabanD(String jawabanD) {
        JawabanD = jawabanD;
    }

    public String getPertanyaan() {
        return Pertanyaan;
    }

    public void setPertanyaan(String pertanyaan) {
        Pertanyaan = pertanyaan;
    }
}
