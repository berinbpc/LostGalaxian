package juego;

import entorno.Entorno;

public class Detector {
	
	//Colisiones y limites de mapa
	public static boolean tocarBordeEjeX(double x, Entorno e) {
    	return x <10 || x > e.ancho()-10;
    }
	public static boolean colisiona(double x1,double y1,double x2,double y2,double dist){	
		return (x1-x2)*(x1-x2)+(y1-y2)*(y1-y2) < dist * dist;
	}
	
	//El objeto esta en el mapa?
	public static boolean estarEnEntorno(double x, double y, Entorno e) {
		return !(x < - e.ancho()*0.2 || x > e.ancho()*1.2 ||
				y < - e.alto()*0.2  || y > e.alto()*1.2);  
	}
	
}
