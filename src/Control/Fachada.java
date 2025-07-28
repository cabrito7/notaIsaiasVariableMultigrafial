/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;
import Vista.VentanaPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 *
 * @author carlosmamut1
 */

public class Fachada implements ActionListener {
    private ControlPrincipal cPrincipal;
    private VentanaPrincipal vPrincipal;
    
    public Fachada(ControlPrincipal cPrincipal) {
        this.cPrincipal = cPrincipal;
        this.vPrincipal = new VentanaPrincipal();
        this.vPrincipal.setVisible(true);
        this.vPrincipal.jButton1.addActionListener(this);
        this.vPrincipal.getvGrafial().jButton2.addActionListener(this);
        this.vPrincipal.getvGrafial().jButton3.addActionListener(this);
        this.vPrincipal.getvGrafial().jButton4.addActionListener(this);
        this.vPrincipal.getvResultados().jButton1.addActionListener(this);
    }

    public VentanaPrincipal getvPrincipal() {
        return vPrincipal;
    }

    public void setvPrincipal(VentanaPrincipal vPrincipal) {
        this.vPrincipal = vPrincipal;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Enviar":
                if (this.cPrincipal.validarValorNumerico(this.vPrincipal.jTextField1.getText())) {
                    this.vPrincipal.getvGrafial().setVisible(true);
                    this.vPrincipal.setVisible(false);
                } else {
                    this.vPrincipal.mostrarMensajeError("Los multigrafos deben tener un valor NUMÉRICO de vértices entre 1 y 26");
                }
                break;
                
            case "Salir":
                this.vPrincipal.getvGrafial().setVisible(false);
                System.exit(0);
                break;
                
            case "AgregarArista":
                this.cPrincipal.agregarArista(
                    this.vPrincipal.getvGrafial().jTextField2.getText(), 
                    this.vPrincipal.getvGrafial().jTextField3.getText(), 
                    this.vPrincipal.jCheckBox1.isSelected()
                );
                break;
                
            case "EmpezarSimulacion":
                this.cPrincipal.crearMultigrafoEnControlPrincipal(this.vPrincipal.jCheckBox1.isSelected());
                this.vPrincipal.getvResultados().setVisible(true);
                break;   
            case "EnviarResultados":
                String texto = this.cPrincipal.dijkstra(
                    this.vPrincipal.getvResultados().jTextField1.getText(), 
                    this.cPrincipal.crearMatrizDeAdyacencia()
                );
                this.vPrincipal.getvResultados().jLabel3.setText(texto);
                break;
        }
    }
}

