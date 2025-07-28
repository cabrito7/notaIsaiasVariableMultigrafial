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


public class ControlMultigrafo {
    private ControlPrincipal cPrincipal;
    private Multigrafo multigrafo;

    public ControlMultigrafo(ControlPrincipal cPrincipal) {
        this.cPrincipal = cPrincipal;
    }

    public void crearMultigrafo(char[] vertices, ArrayList<Multigrafo.Arista> aristas, boolean dirigido) {
        this.multigrafo = new Multigrafo(vertices, aristas, dirigido);
    }

    public Multigrafo getMultigrafo() {
        return multigrafo;
    }

    public void setMultigrafo(Multigrafo multigrafo) {
        this.multigrafo = multigrafo;
    }
}
