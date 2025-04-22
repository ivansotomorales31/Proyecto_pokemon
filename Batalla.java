/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pokemojuego;

public class Batalla {
    private Entrenador jugador;
    private Entrenador oponente;
    private Cola<Pokemon> turnos;

    public Batalla(Entrenador jugador, Entrenador oponente) {
        this.jugador = jugador;
        this.oponente = oponente;
        this.turnos = new Cola<>();
        prepararTurnos();
    }

    private void prepararTurnos() {
        // Agregar los Pokémon del jugador y del oponente a la cola
        for (int i = 0; i < jugador.getEquipo().size(); i++) {
            turnos.encolar(jugador.getEquipo().get(i));
        }
        for (int i = 0; i < oponente.getEquipo().size(); i++) {
            turnos.encolar(oponente.getEquipo().get(i));
        }
    }

    public void iniciarCombate() {
        while (!turnos.estaVacia()) {
            Pokemon actual = turnos.desencolar();
            if (jugador.getEquipo().contains(actual)) {
                realizarAtaqueJugador(actual);
            } else {
                realizarAtaqueCPU(actual);
            }

            if (verificarFinCombate()) {
                break;
            }
        }
    }

    private void realizarAtaqueJugador(Pokemon pokemon) {
        // Ataque del jugador
        Pokemon enemigo = oponente.getEquipo().get(0); // Primer Pokémon del oponente
        enemigo.recibirDanio(pokemon.getAtaque());
        System.out.println(pokemon.getNombre() + " ataca a " + enemigo.getNombre() + " del oponente.");
    }

    private void realizarAtaqueCPU(Pokemon pokemon) {
        // Ataque del CPU
        Pokemon enemigo = jugador.getEquipo().get(0); // Primer Pokémon del jugador
        enemigo.recibirDanio(pokemon.getAtaque());
        System.out.println(pokemon.getNombre() + " del oponente ataca a " + enemigo.getNombre() + " del jugador.");
    }

    private boolean verificarFinCombate() {
        if (oponente.getEquipo().estanTodosDerrotados()) {
            System.out.println("El jugador ha ganado el combate.");
            restaurarSaludPokemon(jugador.getEquipo());
            return true;
        } else if (jugador.getEquipo().estanTodosDerrotados()) {
            System.out.println("El jugador ha perdido el combate.");
            reiniciarJuego();
            return true;
        }
        return false;
    }

    private void restaurarSaludPokemon(ListaEnlazada<Pokemon> equipo) {
        for (int i = 0; i < equipo.size(); i++) {
            Pokemon p = equipo.get(i);
            p.setHp(100); // Restablece la salud a 100 (o valor máximo)
        }
    }

    private void reiniciarJuego() {
        System.out.println("Reiniciando el juego...");
        jugador.getEquipo().reiniciarEquipo();
        oponente.getEquipo().reiniciarEquipo();
    }
}
