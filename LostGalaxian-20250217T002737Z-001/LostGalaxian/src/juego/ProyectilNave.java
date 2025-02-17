package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class ProyectilNave {
	
	// Variables de instancia
	double x,y;
	double angulo;
	double escala;
	double velocidad;
	Image img;
	Entorno entorno;
	
	public ProyectilNave(Entorno ent, double esc, double vel, double inicioX, double inicioY) {

		//Si es de jugador
		this.entorno=ent;
		this.escala=esc;
		this.velocidad=vel;
		this.x=inicioX;
		this.y=inicioY - 80;
		this.angulo=0;
		img= Herramientas.cargarImagen("proyectilNave.png");
	}
	
	// Dibujar
	public void dibujar() {
		entorno.dibujarImagen(img, this.x, this.y, this.angulo, escala);
	}
	// movimientos
	public void mover() {
		this.y += Math.sin(-1)*velocidad;
	}
}
