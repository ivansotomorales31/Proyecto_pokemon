/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.pokemojuego;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;


public class PokemonGame {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Crear instancia de MenuPrincipal
                Menu menuPrincipal = new Menu();

                // Configurar la ventana para que aparezca en el centro de la pantalla
                menuPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ajusta el tamaño según tus necesidades
                menuPrincipal.setLocationRelativeTo(null); // Esto centra la ventana
                menuPrincipal.setVisible(true);
            }
        });
    }
}
