package Hilos.Aeropuerto;

import Hilos.*;

public class Avion extends Thread {
	
	private int coordenadaX, coordenadaY, limIzq, limDer, limInf, limIntento, limSup, noIntentos;
	private boolean aterrizajeBand, direccion;
	private static int turnoActual;
	private final int turno;
	private Semaforo semaphore;
	
	public Avion(int turno, int coordenadaX, int coordenadaY, int limDer) {
		this.turno = turno;
		this.coordenadaX = coordenadaX;
		this.coordenadaY = limSup = coordenadaY;
		this.limIzq = 50;
		this.limDer = limDer - 200;
		this.limInf = 700;
		this.limIntento = 290;
		noIntentos = 0;
		direccion = true;
		aterrizajeBand = false;
		turnoActual = 1;
		semaphore = new Semaforo(1);
	}
	
	public void run() {
		int cont = 0;
		while(!aterrizajeBand) {
			if(direccion) {
				coordenadaX-=10;
				if(coordenadaX<=limIzq)
					direccion = false;
			}
			else {
				coordenadaX+=10;
				if(coordenadaX>=limDer)
					direccion = true;
			}
			try {
				sleep(50);
			} catch (Exception e) {}
			cont++;
			if(cont==100) {	//cada cinco segundos
				intento();
				cont=0;
			}
		}
	}
	
	public void intento() {
		//Para que aterrice cuando va hacia la derecha
		if(direccion || coordenadaX>500)
			return;
		noIntentos++;
		
		//Que intente aterrizar
		while(this.coordenadaY<limIntento)
			mover(true);
		
		//Verifica si es su turno
		if(turno==turnoActual) {
			aterrizar();
			return;
		}
		
		//Si no es, se devuelve
		while(this.coordenadaY>limSup)
			mover(false);
	}
	
	public void mover(boolean direccionY) {
		coordenadaX+=10;
		if(direccionY)
			coordenadaY+=10;
		else
			coordenadaY-=10;
		try {
			sleep(50);
		} catch (Exception e) {}
	}
	
	public void aterrizar() {
		//Baja a la autopista
		while(coordenadaY<limInf)
			mover(true);
		//Se va hasta donde espera
		while(coordenadaX<limDer-100) {
			coordenadaX+=10;
			try {
				sleep(50);
			} catch (Exception e) {}
		}
		//Se espera
		try {
			sleep(Rutinas.nextInt(5000,10000));
		} catch (Exception e) {}
		//Y se va
		while(coordenadaX<limDer+200) {
			coordenadaX+=10;
			try {
				sleep(50);
			} catch (Exception e) {}
		}
		semaphore.Espera();
		aterrizajeBand = true;
		turnoActual++;
		semaphore.Libera();
	}
	
	//GETTERS
	public int getTurno() {
		return turno;
	}
	public int getNoIntentos() {
		return noIntentos;
	}
	public int getCoordenadaX() {
		return coordenadaX;
	}
	public int getCoordenadaY() {
		return coordenadaY;
	}
	public boolean getAterrizajeBand() {
		return aterrizajeBand;
	}
	public boolean getDireccion() {
		return direccion;
	}
}