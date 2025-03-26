//package com.example.masanz.aimar.actividades.model.entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//@Entity
//@Table
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class Imagen {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Lob
//    @Column(length = 1000)
//    private byte[] foto;
//
//    // Constructor sin @Builder, simplemente con @AllArgsConstructor
//
//    public Imagen(byte[] foto) {
//        this.foto = foto;
//    }
//
//    public Imagen() {
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public byte[] getFoto() {
//        return foto;
//    }
//
//    public void setFoto(byte[] foto) {
//        this.foto = foto;
//    }
//}
