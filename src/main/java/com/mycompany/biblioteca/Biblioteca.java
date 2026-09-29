/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.biblioteca;

import com.formdev.flatlaf.FlatLightLaf;
import model.conexao;
import view.principal;

/**
 *
 * @author leoci
 */
public class Biblioteca {

       public static void main(String args[]) {
        try { FlatLightLaf.setup(); } catch (Exception e) { e.printStackTrace(); }
        conexao.inicializarBanco();
        java.awt.EventQueue.invokeLater(() -> new principal().setVisible(true));
    }

}
