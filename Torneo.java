/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pokemojuego;

public class Torneo {
    private Entrenador jugador;
    private ListaEnlazada<Entrenador> entrenadoresCPU;

    public Torneo(Entrenador jugador, ListaEnlazada<Entrenador> entrenadoresCPU) {
        this.jugador = jugador;
        this.entrenadoresCPU = entrenadoresCPU;
    }
}
