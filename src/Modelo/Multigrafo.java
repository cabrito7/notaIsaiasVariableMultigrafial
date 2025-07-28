/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author carlosmamut1
 */


import java.util.ArrayList;

public class Multigrafo {
    private char[] vertices;
    private ArrayList<Arista> aristas;
    private boolean dirigido;

    public static class Arista {
        private char origen;
        private char destino;
        private int peso;
        private int id; // Unique identifier for each edge

        public Arista(char origen, char destino, int peso, int id) {
            this.origen = origen;
            this.destino = destino;
            this.peso = peso;
            this.id = id;
        }

        public char getOrigen() { return origen; }
        public char getDestino() { return destino; }
        public int getPeso() { return peso; }
        public int getId() { return id; }
        
        public void setOrigen(char origen) { this.origen = origen; }
        public void setDestino(char destino) { this.destino = destino; }
        public void setPeso(int peso) { this.peso = peso; }
    }

    public Multigrafo(char[] vertices, ArrayList<Arista> aristas, boolean dirigido) {
        this.vertices = vertices;
        this.aristas = aristas;
        this.dirigido = dirigido;
    }

    public char[] getVertices() { return vertices; }
    public void setVertices(char[] vertices) { this.vertices = vertices; }
    
    public ArrayList<Arista> getAristas() { return aristas; }
    public void setAristas(ArrayList<Arista> aristas) { this.aristas = aristas; }
    
    public boolean isDirigido() { return dirigido; }
    public void setDirigido(boolean dirigido) { this.dirigido = dirigido; }
}
