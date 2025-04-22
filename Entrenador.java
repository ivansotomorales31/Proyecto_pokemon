/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pokemojuego;

public class Entrenador {
    private String nombre;
    private ListaEnlazada<Pokemon> equipo;

    public Entrenador(String nombre) {
        this.nombre = nombre;
        this.equipo = new ListaEnlazada<>();
    }

    public ListaEnlazada<Pokemon> getEquipo() {
        return equipo;
    }

    public void agregarPokemon(Pokemon pokemon) {
        equipo.add(pokemon);
    }

    public void reiniciarEquipo() {
        for (int i = 0; i < equipo.size(); i++) {
            Pokemon p = equipo.get(i);
            p.setHp(100); // Restablecer HP al valor máximo
        }
    }
}
