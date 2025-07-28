/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;
import Modelo.Multigrafo;
import java.util.ArrayList;
/**
 *
 * @author carlosmamut1
 */


public class ControlPrincipal {
    private char[] abecedario;
    private Fachada fachada;
    private ControlMultigrafo cMultigrafo;
    private char[] verticesDelMultigrafo;
    private ArrayList<Multigrafo.Arista> aristasDelMultigrafo;
    private int contadorDeAristas; // For unique edge IDs
    private static final int INFINITY = Integer.MAX_VALUE;

    public ControlPrincipal() {
        this.fachada = new Fachada(this);
        this.cMultigrafo = new ControlMultigrafo(this);
        this.abecedario = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        this.verticesDelMultigrafo = new char[]{};
        this.aristasDelMultigrafo = new ArrayList<>();
        this.contadorDeAristas = 0;
    }

    public void crearMultigrafoEnControlPrincipal(boolean dirigido) {
        this.cMultigrafo.crearMultigrafo(verticesDelMultigrafo, aristasDelMultigrafo, dirigido);
    }

    public void extraerVertices(int num) {
        this.verticesDelMultigrafo = new char[num];
        for (int i = 0; i < num; i++) {
            verticesDelMultigrafo[i] = this.abecedario[i];
        }
    }

    public boolean encontrarVertice(char verticeAEncontrar) {
        for (int i = 0; i < this.verticesDelMultigrafo.length; i++) {
            if (verticesDelMultigrafo[i] == verticeAEncontrar) {
                return true;
            }
        }
        return false;
    }

    // Modified to allow multiple edges between same vertices
    public void agregarArista(String texto, String pesoDeLaArista, boolean dirigido) {
        try {
            if (texto.length() == 3 && texto.charAt(1) == ',' && 
                encontrarVertice(texto.charAt(0)) && encontrarVertice(texto.charAt(2))) {
                
                int peso = Integer.parseInt(pesoDeLaArista);
                if (peso > 0) {
                    char origen = texto.charAt(0);
                    char destino = texto.charAt(2);
                    
                    // Always allow adding edges (this enables multigraph functionality)
                    if (!dirigido) {
                        // Add edge in both directions for undirected multigraph
                        Multigrafo.Arista arista1 = new Multigrafo.Arista(origen, destino, peso, ++contadorDeAristas);
                        Multigrafo.Arista arista2 = new Multigrafo.Arista(destino, origen, peso, ++contadorDeAristas);
                        aristasDelMultigrafo.add(arista1);
                        aristasDelMultigrafo.add(arista2);
                        this.fachada.getvPrincipal().mostrarMensaje("Arista múltiple agregada exitosamente (bidireccional)");
                    } else {
                        // Add single directed edge
                        Multigrafo.Arista arista = new Multigrafo.Arista(origen, destino, peso, ++contadorDeAristas);
                        aristasDelMultigrafo.add(arista);
                        this.fachada.getvPrincipal().mostrarMensaje("Arista múltiple agregada exitosamente (dirigida)");
                    }
                } else {
                    this.fachada.getvPrincipal().mostrarMensajeError("El peso debe ser mayor a 0");
                }
            } else {
                this.fachada.getvPrincipal().mostrarMensajeError("Formato de arista inválido (use formato: a,b)");
            }
        } catch (NumberFormatException e) {
            this.fachada.getvPrincipal().mostrarMensajeError("El peso debe ser un número válido");
        }
    }

    public int buscarEnElAbecedario(char letra) {
        for (int i = 0; i < abecedario.length; i++) {
            if (letra == abecedario[i]) {
                return i;
            }
        }
        return -1;
    }

    // Modified adjacency matrix creation for multigraph (keeps minimum weight between vertices)
    public int[][] crearMatrizDeAdyacencia() {
        int numVertices = this.cMultigrafo.getMultigrafo().getVertices().length;
        int[][] matrizDeAdyacencia = new int[numVertices][numVertices];
        
        // Initialize with zeros
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                matrizDeAdyacencia[i][j] = 0;
            }
        }
        
        // For multigraphs, we keep the minimum weight between any two vertices
        for (Multigrafo.Arista arista : this.cMultigrafo.getMultigrafo().getAristas()) {
            int origenIdx = buscarEnElAbecedario(arista.getOrigen());
            int destinoIdx = buscarEnElAbecedario(arista.getDestino());
            
            if (matrizDeAdyacencia[origenIdx][destinoIdx] == 0 || 
                arista.getPeso() < matrizDeAdyacencia[origenIdx][destinoIdx]) {
                matrizDeAdyacencia[origenIdx][destinoIdx] = arista.getPeso();
            }
        }
        
        return matrizDeAdyacencia;
    }

    public String dijkstra(String verticeInicial, int[][] matrizDeAdyacencia) {
        if (encontrarVertice(verticeInicial.charAt(0)) && verticeInicial.length() == 1) {
            int numNodes = matrizDeAdyacencia.length;
            int[] distances = new int[numNodes];
            boolean[] visited = new boolean[numNodes];

            for (int i = 0; i < numNodes; i++) {
                distances[i] = INFINITY;
                visited[i] = false;
            }
            
            distances[buscarEnElAbecedario(verticeInicial.charAt(0))] = 0;
            
            for (int i = 0; i < numNodes - 1; i++) {
                int minDistanceNode = findMinDistanceNode(distances, visited);
                visited[minDistanceNode] = true;

                for (int j = 0; j < numNodes; j++) {
                    if (!visited[j] && matrizDeAdyacencia[minDistanceNode][j] != 0
                            && distances[minDistanceNode] != INFINITY
                            && distances[minDistanceNode] + matrizDeAdyacencia[minDistanceNode][j] < distances[j]) {
                        distances[j] = distances[minDistanceNode] + matrizDeAdyacencia[minDistanceNode][j];
                    }
                }
            }
            
            String text = "<html><h3>Resultados del Algoritmo de Dijkstra (Multigrafo):</h3>";
            for (int i = 0; i < numNodes; i++) {
                if (distances[i] == INFINITY) {
                    text += "Hasta el nodo " + this.cMultigrafo.getMultigrafo().getVertices()[i] + ": ∞ (inaccesible)<br>";
                } else {
                    text += "Hasta el nodo " + this.cMultigrafo.getMultigrafo().getVertices()[i] + ": " + distances[i] + "<br>";
                }
            }
            text += "<br><i>Nota: En multigrafos se usa el peso mínimo entre aristas múltiples</i></html>";
            return text;
        }
        return "<html>Error: Vértice inicial no válido</html>";
    }

    public static int findMinDistanceNode(int[] distances, boolean[] visited) {
        int minDistance = INFINITY;
        int minDistanceNode = -1;

        for (int i = 0; i < distances.length; i++) {
            if (!visited[i] && distances[i] <= minDistance) {
                minDistance = distances[i];
                minDistanceNode = i;
            }
        }
        return minDistanceNode;
    }

    // Method to get information about multiple edges
    public String obtenerInfoMultigrafo() {
        if (cMultigrafo.getMultigrafo() == null) {
            return "<html>No hay multigrafo creado</html>";
        }
        
        StringBuilder info = new StringBuilder("<html><h3>Información del Multigrafo:</h3>");
        info.append("Vértices: ");
        for (char v : cMultigrafo.getMultigrafo().getVertices()) {
            info.append(v).append(" ");
        }
        info.append("<br><br>Aristas múltiples:<br>");
        
        for (Multigrafo.Arista arista : cMultigrafo.getMultigrafo().getAristas()) {
            info.append("ID: ").append(arista.getId())
                .append(" | ").append(arista.getOrigen())
                .append(" → ").append(arista.getDestino())
                .append(" (peso: ").append(arista.getPeso()).append(")<br>");
        }
        
        info.append("</html>");
        return info.toString();
    }

    public boolean validarValorNumerico(String num) {
        try {
            int numeroDeVertices = Integer.parseInt(num);
            if (numeroDeVertices > 0 && numeroDeVertices <= 26) {
                this.extraerVertices(numeroDeVertices);
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
