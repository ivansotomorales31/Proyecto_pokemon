/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.pokemojuego;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class Combate extends javax.swing.JFrame {

    private ListaEnlazada<JLabel> pokemonesJugador;
    private ListaEnlazada<ListaEnlazada<JLabel>> equiposCPU;
    private int indiceEquipoCPUActual; 
    private int indicePokemonCPUActual; 
    private JLabel pokemonJugadorActual;
    private JLabel pokemonCPUActual;

    private int jugadorHP = 100;
    private int cpuHP = 100;
    private boolean esTurnoJugador = true;

    public Combate() {
        initComponents();
    }

    public Combate(ListaEnlazada<JLabel> pokemonesJugador, ListaEnlazada<ListaEnlazada<JLabel>> equiposCPU) {
        this.pokemonesJugador = pokemonesJugador;
        this.equiposCPU = equiposCPU;
        this.indiceEquipoCPUActual = 0; // Inicializar con el primer equipo
        this.indicePokemonCPUActual = 0; // Inicializar con el primer Pokémon del equipo

        initComponents();
        prepararCombate();
        mostrarPokemonEnCombate();

        // Configurar los botones de ataque y selección de Pokémon
        configurarBotonesDeAtaque();
        configurarBotonesPokemones();
        actualizarBotonesPokemones();
    }

    private void prepararCombate() {
        // Reiniciar los índices para el siguiente combate
        indicePokemonCPUActual = 0;

        // Obtener el primer equipo de la CPU
        ListaEnlazada<JLabel> equipoActualCPU = equiposCPU.get(indiceEquipoCPUActual);

        // Inicializar el primer Pokémon de cada lado
        pokemonJugadorActual = pokemonesJugador.get(0);
        pokemonCPUActual = equipoActualCPU.get(indicePokemonCPUActual);

        System.out.println("Iniciando combate con:");
        System.out.println("Pokémon del Jugador: " + pokemonJugadorActual.getText());
        System.out.println("Pokémon del CPU: " + pokemonCPUActual.getText());
    }

    private void configurarBotonesDeAtaque() {
        ataque1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                realizarAtaqueJugador("Ataque 1", 20);
            }
        });

        ataque2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                realizarAtaqueJugador("Ataque 2", 15);
            }
        });

        ataque3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                realizarAtaqueJugador("Ataque 3", 50);
            }
        });
    }

    private void configurarBotonesPokemones() {
        // Configurar botones para cambiar de Pokémon
        poke1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                cambiarPokemonJugador(0);
            }
        });

        poke2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                cambiarPokemonJugador(1);
            }
        });

        poke3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                cambiarPokemonJugador(2);
            }
        });
    }

    private void realizarAtaqueJugador(String nombreAtaque, int dañoBase) {
        // Ataque del jugador
        texto.setText(PokemonNameUser.getText() + " usó " + nombreAtaque);
        if (ataqueAciertaSiempre()) {
            cpuHP -= dañoBase;
            texto.setText(texto.getText() + " e hizo " + dañoBase + " de daño!");
        } else {
            texto.setText(texto.getText() + ", pero falló!");
        }

        verificarFinDelCombate();

        if (cpuHP > 0) {
            // Esperar un momento y luego realizar el ataque del CPU
            Timer timer = new Timer(1000, new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    realizarAtaqueCPU();
                }
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    private void realizarAtaqueCPU() {
        deshabilitarBotones();
        String nombreAtaque = "Ataque del CPU";
        int dañoBase = 20;
        texto.setText(PokemonNameCPU.getText() + " usó " + nombreAtaque);
        if (ataqueAcierta()) {
            jugadorHP -= dañoBase;
            texto.setText(texto.getText() + " e hizo " + dañoBase + " de daño!");
        } else {
            texto.setText(texto.getText() + ", pero falló!");
        }
        habilitarBotones();
        verificarFinDelCombate();
    }

    private boolean ataqueAcierta() {
        return Math.random() > 0.9; // 90% de probabilidad de fallar
    }

    private boolean ataqueAciertaSiempre() {
        return true; // 100% de probabilidad de acierto
    }

    private void verificarFinDelCombate() {
        if (jugadorHP <= 0) {
            texto.setText(PokemonNameUser.getText() + " ha sido derrotado.");
            deshabilitarBotones();
            bloquearBotonPokemonMuerto();
            if (quedanMasPokemonesJugador()) {
                texto.setText(texto.getText() + " Selecciona tu próximo Pokémon.");
            } else {
                texto.setText(texto.getText() + " ¡Has perdido el combate!");
            }
        } else if (cpuHP <= 0) {
            texto.setText(PokemonNameCPU.getText() + " ha sido derrotado.");
            deshabilitarBotones();
            avanzarAlSiguienteCombate();
        }
    }

    private void avanzarAlSiguienteCombate() {
        // Avanzar al siguiente Pokémon del equipo actual del CPU
        indicePokemonCPUActual++;

        ListaEnlazada<JLabel> equipoActualCPU = equiposCPU.get(indiceEquipoCPUActual);
        if (indicePokemonCPUActual < equipoActualCPU.size()) {
            pokemonCPUActual = equipoActualCPU.get(indicePokemonCPUActual);
            cpuHP = 100; // Restablecer HP para el próximo Pokémon
            mostrarPokemonEnCombate();
        } else {
            avanzarAlSiguienteEquipo();
        }
    }

    private void avanzarAlSiguienteEquipo() {
        indiceEquipoCPUActual++; // Avanzar al siguiente equipo

        if (indiceEquipoCPUActual < equiposCPU.size()) {
            JOptionPane.showMessageDialog(this, "¡Has vencido al equipo del CPU! Prepárate para el siguiente equipo.");
            restablecerPokemonesJugador(); // Restablece los Pokémon del jugador
            prepararCombate(); // Configurar el siguiente equipo del CPU
            mostrarPokemonEnCombate();
        } else {
            texto.setText("¡Has ganado el torneo!");
            deshabilitarBotones();
        }
    }

    private void restablecerPokemonesJugador() {
        jugadorHP = 100; // Reinicia el HP del jugador

        for (int i = 0; i < pokemonesJugador.size(); i++) {
        }

        // Habilitar los botones de selección de Pokémon del jugador
        poke1.setEnabled(true);
        poke2.setEnabled(true);
        poke3.setEnabled(true);
    }


    private void cambiarPokemonJugador(int indice) {
        if (indice < pokemonesJugador.size() && pokemonesJugador.get(indice) != null) {
            pokemonJugadorActual = pokemonesJugador.get(indice);
            jugadorHP = 100; // Reiniciar HP del nuevo Pokémon
            mostrarPokemonEnCombate();
            habilitarBotones();
        }
    }

    private void bloquearBotonPokemonMuerto() {
        if (pokemonJugadorActual == pokemonesJugador.get(0)) {
            poke1.setEnabled(false);
        } else if (pokemonJugadorActual == pokemonesJugador.get(1)) {
            poke2.setEnabled(false);
        } else if (pokemonJugadorActual == pokemonesJugador.get(2)) {
            poke3.setEnabled(false);
        }
    }

    private boolean quedanMasPokemonesJugador() {
        return pokemonesJugador.size() > 1; // Verificar si hay más de un Pokémon disponible
    }

    private void habilitarBotones() {
        ataque1.setEnabled(true);
        ataque2.setEnabled(true);
        ataque3.setEnabled(true);
    }

    private void deshabilitarBotones() {
        ataque1.setEnabled(false);
        ataque2.setEnabled(false);
        ataque3.setEnabled(false);
    }

    private void mostrarPokemonEnCombate() {
        // Obtener el primer JLabel del jugador y CPU
        JLabel primerPokemonJugador = pokemonJugadorActual;
        JLabel primerPokemonCPU = pokemonCPUActual;

        // Obtener el icono y nombre desde el JLabel directamente
        ImageIcon iconJugador = (ImageIcon) primerPokemonJugador.getIcon();
        String nombrePokemonJugador = primerPokemonJugador.getText();

        ImageIcon iconCPU = (ImageIcon) primerPokemonCPU.getIcon();
        String nombrePokemonCPU = primerPokemonCPU.getText();

        // Verificar si el icono se ha cargado correctamente
        if (iconCPU.getImageLoadStatus() != java.awt.MediaTracker.COMPLETE) {
            System.out.println("Error al cargar la imagen para " + nombrePokemonCPU);
        } else {
            System.out.println("Imagen cargada correctamente para " + nombrePokemonCPU);
        }

        // Configurar los JLabels para mostrar el nombre y la imagen del Pokémon del jugador
        PokemonNameUser.setText(nombrePokemonJugador);
        PokemonUser.setIcon(iconJugador);

        // Configurar los JLabels para mostrar el nombre y la imagen del Pokémon del CPU
        PokemonNameCPU.setText(nombrePokemonCPU);
        PokemonCPU.setIcon(iconCPU);
    }

    private void actualizarBotonesPokemones() {
        // Actualizar los nombres de los botones con los nombres de los Pokémon del jugador
        poke1.setText(pokemonesJugador.size() > 0 ? pokemonesJugador.get(0).getText() : "");
        poke2.setText(pokemonesJugador.size() > 1 ? pokemonesJugador.get(1).getText() : "");
        poke3.setText(pokemonesJugador.size() > 2 ? pokemonesJugador.get(2).getText() : "");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PokemonCPU = new javax.swing.JLabel();
        PokemonNameCPU = new javax.swing.JLabel();
        PokemonUser = new javax.swing.JLabel();
        PokemonNameUser = new javax.swing.JLabel();
        ataque1 = new javax.swing.JButton();
        ataque2 = new javax.swing.JButton();
        ataque3 = new javax.swing.JButton();
        poke2 = new javax.swing.JButton();
        poke3 = new javax.swing.JButton();
        poke1 = new javax.swing.JButton();
        texto = new javax.swing.JLabel();
        fondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PokemonCPU.setText("jLabel3");
        getContentPane().add(PokemonCPU, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 0, 300, 210));

        PokemonNameCPU.setText("jLabel2");
        getContentPane().add(PokemonNameCPU, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, -1, -1));

        PokemonUser.setText("jLabel2");
        getContentPane().add(PokemonUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, 50, 310, 200));

        PokemonNameUser.setText("jLabel3");
        getContentPane().add(PokemonNameUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 180, -1, -1));

        ataque1.setText("Ataque 1");
        getContentPane().add(ataque1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, -1, -1));

        ataque2.setText("Ataque 2");
        getContentPane().add(ataque2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 300, -1, -1));

        ataque3.setText("Ataque 3");
        getContentPane().add(ataque3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 330, -1, -1));
        getContentPane().add(poke2, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 300, -1, 20));
        getContentPane().add(poke3, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 330, -1, 20));
        getContentPane().add(poke1, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 270, -1, 20));
        getContentPane().add(texto, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 270, 410, 80));

        fondo.setIcon(new javax.swing.JLabel() {
            public javax.swing.Icon getIcon() {
                try {
                    return new javax.swing.ImageIcon(
                        new java.net.URL("https://i.pinimg.com/564x/99/26/ce/9926cea75b7feebf809ee0d9bf576f21.jpg")
                    );
                } catch (java.net.MalformedURLException e) {
                }
                return null;
            }
        }.getIcon());
        getContentPane().add(fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Combate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Combate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Combate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Combate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Combate().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel PokemonCPU;
    private javax.swing.JLabel PokemonNameCPU;
    private javax.swing.JLabel PokemonNameUser;
    private javax.swing.JLabel PokemonUser;
    private javax.swing.JButton ataque1;
    private javax.swing.JButton ataque2;
    private javax.swing.JButton ataque3;
    private javax.swing.JLabel fondo;
    private javax.swing.JButton poke1;
    private javax.swing.JButton poke2;
    private javax.swing.JButton poke3;
    private javax.swing.JLabel texto;
    // End of variables declaration//GEN-END:variables
}
