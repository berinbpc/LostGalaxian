package juego;

import java.util.Calendar;

public class Utiles {
	public static double generarRandomDouble(double valorMinimo, double valorMaximo) {
		
	    double rango = valorMaximo - valorMinimo; //Calcular el rango de números posibles
	    double numeroAleatorio = Math.random() * rango; //Generar un número aleatorio dentro del rango
	    numeroAleatorio += valorMinimo; //Ajustar el número aleatorio al rango deseado
	    
	    return numeroAleatorio;
	}
	public static int generarRandom(int valorMinimo, int valorMaximo) {
		
	    int rango = valorMaximo - valorMinimo + 1; //Calcular el rango de números posibles
	    int numeroAleatorio = (int) (Math.random() * rango); //Generar un número aleatorio dentro del rango
	    numeroAleatorio += valorMinimo; //Ajustar el número aleatorio al rango deseado
	    
	    return numeroAleatorio;
	}
	public static int segundosActuales() {
		//Obtener la hora actual por bilioteca estandar de java
        Calendar calendar = Calendar.getInstance();
        //Obtener los segundos de la hora actual
        int segundos = calendar.get(Calendar.SECOND);
        
        return segundos;
	}
	public static boolean hayEnemigos(Enemigo enemigos[]) {
		//Veo si queda alguna Enemigo vivo
		boolean sinEnemigos = false; //asume que no hay enemigos hasta que se demuestre que haya al menos uno
		for(int i=0;i<enemigos.length;i++) {
			if(enemigos[i] != null) {
				sinEnemigos = true;
			}
		}
		return sinEnemigos;
	}
	
}
