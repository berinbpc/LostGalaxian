package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class Nave 
{
	// Variables de instancia
	double x,y;
	double escala;
	double angulo;
	int vida;
	Image img1;
	Entorno entorno;
	
	public Nave(Entorno ent,double x, double y, double esc) 
	{
		this.vida=100;
		this.x = x;
		this.y = y;
		this.escala = esc;
		entorno = ent;
	
		img1 = Herramientas.cargarImagen("nave.png");
	}
	
	public void dibujarse(){
		entorno.dibujarImagen(img1, this.x, this.y, this.angulo, escala);
	}
	
	public void mover(double direccion) {
		//Segun positivo seria derecha y negativo izquierda
		if(!Detector.tocarBordeEjeX(this.x,entorno))
			this.x += direccion;
		else
			if(this.x > 0)
				this.x -= 10;
			else if(this.x < 5)
				this.x += 15;
	}
}
