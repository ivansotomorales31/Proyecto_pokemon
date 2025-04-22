/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pokemojuego;

public class ListaEnlazada<T> {

    private Nodo<T> cabeza;

    private static class Nodo<T> {

        T dato;
        Nodo<T> siguiente;

        Nodo(T dato) {
            this.dato = dato;
            this.siguiente = null;
        }
    }

    public ListaEnlazada() {
        cabeza = null;
    }

    public void add(T dato) {
        Nodo<T> nuevoNodo = new Nodo<>(dato);
        if (cabeza == null) {
            cabeza = nuevoNodo;
        } else {
            Nodo<T> temp = cabeza;
            while (temp.siguiente != null) {
                temp = temp.siguiente;
            }
            temp.siguiente = nuevoNodo;
        }
    }

    public T get(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Índice fuera de rango");
        }

        Nodo<T> temp = cabeza;
        for (int i = 0; i < index; i++) {
            temp = temp.siguiente;
        }

        return temp.dato;
    }

    public boolean contains(T dato) {
        Nodo<T> temp = cabeza;
        while (temp != null) {
            if (temp.dato.equals(dato)) {
                return true;
            }
            temp = temp.siguiente;
        }
        return false;
    }

    public int size() {
        int count = 0;
        Nodo<T> temp = cabeza;
        while (temp != null) {
            count++;
            temp = temp.siguiente;
        }
        return count;
    }

    public boolean estanTodosDerrotados() {
        Nodo<T> temp = cabeza;
        while (temp != null) {
            if (temp.dato instanceof Pokemon && !((Pokemon) temp.dato).estaDerrotado()) {
                return false;
            }
            temp = temp.siguiente;
        }
        return true;
    }

    public void reiniciarEquipo() {
        Nodo<T> temp = cabeza;
        while (temp != null) {
            if (temp.dato instanceof Pokemon) {
                ((Pokemon) temp.dato).setHp(100); // Restaurar la salud
            }
            temp = temp.siguiente;
        }
    }

    public boolean estaVacia() {
        return cabeza == null;
    }
    
    public void remove(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Índice fuera de rango");
        }

        if (index == 0) {
            cabeza = cabeza.siguiente;
        } else {
            Nodo<T> temp = cabeza;
            for (int i = 0; i < index - 1; i++) {
                temp = temp.siguiente;
            }
            temp.siguiente = temp.siguiente.siguiente;
        }
    }
}

