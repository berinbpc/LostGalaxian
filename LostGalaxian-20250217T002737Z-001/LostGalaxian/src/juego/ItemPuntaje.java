package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class ItemPuntaje {
	// Variables de instancia
	double x,y;
	double angulo;
	double escala;
	double velocidad;
	Image img;
	Entorno entorno;
	
	public ItemPuntaje(Entorno ent, double esc, double vel) 
	{
		this.entorno=ent;
		this.escala=esc;
		this.velocidad=vel;
		this.x=Math.random()*entorno.ancho();
		this.y=-Math.random()*entorno.alto()*0.2;
		
		angulo=Utiles.generarRandomDouble(0,Math.PI/2);
		velocidad=Utiles.generarRandomDouble(1,5);
		
		img = Herramientas.cargarImagen("extraPuntaje.png");
		 
	}
	
	public void dibujar()
	{
		entorno.dibujarImagen(img, this.x, this.y, 0, this.escala);
       	
	}

			
	public void mover() {
		this.x += Math.cos(this.angulo)*velocidad;
		this.y += Math.sin(this.angulo)*velocidad;
		if(this.x > entorno.ancho()*1.05) {
			this.x=-entorno.ancho()*0.05;
		}
		if(this.x < -entorno.ancho()*0.05) {
			this.x=entorno.ancho()*1.05;
		}
		if(this.y > entorno.alto()*1.05) {
			this.y=-entorno.alto()*0.05;
		}
		if(this.y < -entorno.alto()*0.05) {
			this.y=entorno.alto()*1.05;
		}
	}
}