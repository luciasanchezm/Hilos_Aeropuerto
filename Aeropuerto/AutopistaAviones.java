package Hilos.Aeropuerto;

import javax.swing.*;
import java.awt.*;
import Hilos.*;

public class AutopistaAviones extends JFrame {
	
	private ImageIcon imgIzq = new ImageIcon("Avion1.png");
	private ImageIcon imgDer = new ImageIcon("Avion1-.png");
	private ImageIcon imgAeropuerto;
	
	private Avion [] aviones;
	
	private Graphics g;
	private Image backbuffer = null;
	
	public AutopistaAviones() {
		super("AEROPUERTO");
		createInterface();
		//Crear variables
		imgAeropuerto = new ImageIcon("Aeropuerto.png");
		backbuffer = createImage(getWidth(), getHeight());
		g =backbuffer.getGraphics();
		aviones = new Avion[Rutinas.nextInt(2,5)];
		//Llenar vector de aviones
		for (int i = 0; i < aviones.length; i++)
			aviones[i] = new Avion(i+1, getWidth() + 50 + 500*i, i*65-40, getWidth());
	
		//Iniciar los hilos
		for (int i = 0; i < aviones.length; i++)
			aviones[i].start();
		//Simular
		while(aviones[aviones.length-1].isAlive())
			simular();
		JOptionPane.showMessageDialog(this, "¡Ha acabado la simulación!");
	}
	
	public void simular() {
		Dibuja();
		try {
			Thread.sleep(50);
		} catch(Exception E) {}
	}
	
	public void createInterface() {
		setSize(1500, 900);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}
	
	public void paint(Graphics g) {
		g.drawImage(backbuffer, 0, 0, getWidth(), getHeight(), this);
	}
	
	public void Dibuja() {
		super.paint(g);
		g.drawImage(imgAeropuerto.getImage(), 0, 0, getWidth(), getHeight()-30, this);
		Image img;
		String texto = "";
		for (int i = 0; i < aviones.length; i++) {
			texto += "        Avion #" + (i+1) + ": " + aviones[i].getNoIntentos();
			if(aviones[i].getDireccion())
				img = imgIzq.getImage();
			else
				img = imgDer.getImage();
			g.drawImage(img, aviones[i].getCoordenadaX(), aviones[i].getCoordenadaY(), 200, 200, this);
		}
		g.setFont(new Font("Calibri", Font.BOLD, 20));
		g.drawString(texto, 0, 890);
		repaint();
	}
	
	public static void main(String [] args) {
		new AutopistaAviones();
	}
}