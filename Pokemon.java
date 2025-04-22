/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pokemojuego;

public class Pokemon {
    private String nombre;
    private String tipo;
    private int hp;
    private int ataque;

    public Pokemon(String nombre, String tipo, int hp, int ataque) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.hp = hp;
        this.ataque = ataque;
    }

    public String getNombre() {
        return nombre;
    }

    public int getHp() {
        return hp;
    }

    public int getAtaque() {
        return ataque;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void recibirDanio(int danio) {
        this.hp -= danio;
        if (this.hp < 0) {
            this.hp = 0;
        }
    }

    public boolean estaDerrotado() {
        return this.hp <= 0;
    }
}
