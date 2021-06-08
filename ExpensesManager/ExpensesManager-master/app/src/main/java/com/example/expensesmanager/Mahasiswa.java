package com.example.expensesmanager;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "tblMahasiswa")
public class Mahasiswa implements Serializable {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "nim")
    private String nim;

    @ColumnInfo(name = "nama")
    private String nama;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "nomorhp")
    private String nomorhp;

    @ColumnInfo(name = "foto")
    private String foto;

    public Mahasiswa(String nim, String nama, String foto){
        this.nim = nim;
        this.nama = nama;
        this.foto = foto;
    }
    public void setFoto(String foto) {this.foto = foto; }

    public void setNim(String nim) {this.nim = nim; }

    public void setNama(String nama) {this.nama = nama; }

    public void setEmail(String email) {this.email = email;}

    public void setNomorhp(String nohp){this.nomorhp = nohp; }

    public String getFoto() {return this.foto; }

    public String getNim() { return this.nim; }

    public String getNama() { return this.nama; }

    public String getEmail() { return this.email;}

    public String getNomorhp() { return this.nomorhp;}
}