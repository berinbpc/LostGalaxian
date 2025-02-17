package juego;

import java.awt.Color;
import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	Image fondo;
	Image gameover;
	Image ganaste;
	
	Nave nave;
	ProyectilNave proyectilNave;
	boolean faseJefeFinalGanado;
	int puntaje;
	int velocidadEnemigos;
	int nivel = 1;
	
	
	
	
	Asteroide[] asteroides;
	
	Enemigo[] enemigos;
	ProyectilEnemigo[] ionesEnemigos;
	
	Enemigo jefeFinal;
	ProyectilEnemigo[] ionesJefeFinal;
	
	ItemVida extraVida;
	ItemPuntaje extraPuntaje;
	

	double anguloFondo;
	double escalaFondo;

	// Variables y métodos propios de cada grupo
	double rangoColision = 50; //rango de colision general entre objetos, reducido a la mitad o aumentado dependiendo del tamaño del objeto
	
	int cantidadEnemigos = 4;
	int cantidadAsteroides = 5;
	int cantidadIonesEnPantalla = 2;
	int cantidadIonesJefeFinalEnPantalla = 5;
	
	int dañoEnemigos = 20;
	int dañoAsteroides = 15;
	
	//Pantalla
	int ancho = 800;
	int alto = 600;
	// ...
	

	Juego() {
		// Inicializa el objeto entorno
		entorno = new Entorno(this, "Lost Galaxian - Grupo 8 - v1", ancho, alto);
		nave = new Nave(entorno, ancho/2, alto-40,0.2);
		fondo=Herramientas.cargarImagen("fondo.gif");
		
		
	    gameover=Herramientas.cargarImagen("gameover.png");
	    ganaste=Herramientas.cargarImagen("ganaste.png");
	    faseJefeFinalGanado=false;
	    puntaje=0;
	    velocidadEnemigos=3;
		anguloFondo= 0;
		escalaFondo=1.8;
		
		// Inicializar lo que haga falta para el juego
		
		//ASTEROIDES
		asteroides = new Asteroide[cantidadAsteroides];
		
		for(int i=0;i<4;i++) {
			Asteroide a = new Asteroide(entorno, 0.5 ,Utiles.generarRandomDouble(1,5));
			asteroides[i]=a;
		}
		
		
		//ENEMIGOS
		enemigos =new Enemigo[cantidadEnemigos];
		for (int i=0; i < enemigos.length;i++) {
			enemigos[i]= new Enemigo(entorno, 0.2 ,velocidadEnemigos, i); //crea a los enemigos uno por uno y le da una diferencia de altura por i
		}
		
		
		
		ionesEnemigos = new ProyectilEnemigo[cantidadIonesEnPantalla]; //Limite de cantidad de iones instanciados en pantalla 
		ionesJefeFinal = new ProyectilEnemigo[cantidadIonesJefeFinalEnPantalla]; //Limite de cantidad de iones instanciados en pantalla 
		
		// ...

		// Inicia el juego!
		entorno.iniciar();
	}

	public void tick() {
		
		//Fondo
		entorno.dibujarImagen(fondo, 400, 300, anguloFondo, escalaFondo);
		
		//CONTROLES
		if(nave != null) {
			if (entorno.estaPresionada(entorno.TECLA_DERECHA) || entorno.estaPresionada('d'))
				nave.mover(5);
				
			
			if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA) || entorno.estaPresionada('a'))
				nave.mover(-5);
			
			
			if (proyectilNave == null && entorno.estaPresionada(entorno.TECLA_ESPACIO)) {
				proyectilNave= new ProyectilNave(entorno,3,5,nave.x,nave.y);
			}
			
		}
		
		//ITEMS
		if(extraVida == null) {
			extraVida = new ItemVida(entorno, 0.05 ,Utiles.generarRandomDouble(1,5));
		}
		else if(extraVida != null) {
			extraVida.dibujar();
			extraVida.mover();
		}
		if(extraPuntaje == null) {
			extraPuntaje = new ItemPuntaje(entorno, 0.05 ,Utiles.generarRandomDouble(1,5));
		}
		else if(extraPuntaje != null) {
			extraPuntaje.dibujar();
			extraPuntaje.mover();
		}
		
		
		//PROYECTILES
		
		//Proyectil Nave
		if(proyectilNave != null){
			proyectilNave.dibujar();
			proyectilNave.mover();

			//Desaparecerlo si esta fuera del mapa
			if(!Detector.estarEnEntorno(proyectilNave.x, proyectilNave.y, entorno)) {
				proyectilNave=null;
			}
		}
		
		
		//Proyectil enemigos
		for(int i=0;i < ionesEnemigos.length;i++) {
			if(ionesEnemigos[i] !=  null) {
				//Accionar proyectiles
				ionesEnemigos[i].dibujar();
				ionesEnemigos[i].mover();
				
				//Si colisiona a un jugador
				if(nave != null && Detector.colisiona(nave.x,nave.y,ionesEnemigos[i].x,ionesEnemigos[i].y,rangoColision/2)) {
					System.out.println("Colision con iones!!!!");
					ionesEnemigos[i]=null;
					nave.vida-=dañoEnemigos;
				}
				//Desaparecerlo si esta fuera del mapa y no fue eliminado si hubo una colision con jugador
				if(ionesEnemigos[i] !=  null && !Detector.estarEnEntorno(ionesEnemigos[i].x, ionesEnemigos[i].y, entorno)) {
					ionesEnemigos[i] = null;
				}
			}
			if(ionesEnemigos[i] ==  null && Utiles.hayEnemigos(enemigos)) {
				int enemigoElegido = Utiles.generarRandom(0, enemigos.length - 1); //elije un enemigo entre el 0 y la cantidad de enenemigos
				while(enemigos[enemigoElegido] == null) {
					enemigoElegido = Utiles.generarRandom(0, enemigos.length - 1); //elije un enemigo entre el 0 y la cantidad de enenemigos
				}
				ionesEnemigos[i] = new ProyectilEnemigo(entorno,3,5,enemigos[enemigoElegido].x,enemigos[enemigoElegido].y);
			}
		}
		
		//Proyectil Jefe Final
		if(jefeFinal!=null) {
		for(int i=0;i < ionesJefeFinal.length;i++) {
			if(ionesJefeFinal[i] !=  null) {
				//Accionar proyectiles
				ionesJefeFinal[i].dibujar();
				ionesJefeFinal[i].mover();
				
				//Si colisiona a un jugador
				if(nave != null && Detector.colisiona(nave.x,nave.y,ionesJefeFinal[i].x,ionesJefeFinal[i].y,rangoColision/2)) {
					System.out.println("Colision con iones!!!!");
					ionesJefeFinal[i]=null;
					nave.vida-=dañoEnemigos;
				}
				//Desaparecerlo si esta fuera del mapa y no fue eliminado si hubo una colision con jugador
				if(ionesJefeFinal[i] !=  null && !Detector.estarEnEntorno(ionesJefeFinal[i].x, ionesJefeFinal[i].y, entorno)) {
					ionesJefeFinal[i] = null;
				}
			}
			if(ionesJefeFinal[i] ==  null) {
				ionesJefeFinal[i] = new ProyectilEnemigo(entorno,3,5,jefeFinal.x,80+i*18);
			}
		}
		}
		
		
		
		//ASTEROIDES
		for(int i=0;i<asteroides.length;i++) {
			if(asteroides[i] != null) {
				asteroides[i].mover();
				asteroides[i].dibujar();

				//Si un asteroide colisiona con el jugador
				if(nave != null  && asteroides[i] != null && 
					Detector.colisiona(nave.x,nave.y,asteroides[i].x,asteroides[i].y,rangoColision/2)) {
					System.out.println("Colision con asteroide!!!!");
					nave.vida-=dañoAsteroides;
					asteroides[i]=null;
				}
				//Desaparecerlo si esta fuera del mapa y no fue eliminado si hubo una colision con jugador
				if(proyectilNave != null && asteroides[i] != null && Detector.colisiona(proyectilNave.x,proyectilNave.y,asteroides[i].x,asteroides[i].y,rangoColision/2)) {
					proyectilNave=null;
				}
				//Si un asteoride ya no es visible verticalmente en la pantalla
				if(asteroides[i] != null && !Detector.estarEnEntorno(asteroides[i].x,asteroides[i].y,entorno)) {
					asteroides[i].y = 0;
				}
			}
			if(asteroides[i] == null) {
				asteroides[i]= new Asteroide(entorno, 0.5 ,Utiles.generarRandomDouble(1,5));
			}
			
		}
		
		
		//ENEMIGOS
		for (int i=0; i < enemigos.length;i++) {
			if(enemigos[i] != null) {
				enemigos[i].dibujar();
				enemigos[i].mover();
				
				//Si enemigo esta muy cerca del jugador
				if (nave != null  && enemigos[i] != null &&
				Detector.colisiona(nave.x,nave.y,enemigos[i].x,enemigos[i].y,rangoColision*2)) {
					//Entra en modo kamikaze (toma el angulo hacia el jugador)
					enemigos[i].cambiarAngulo(nave.x, nave.y);
					enemigos[i].velocidad=velocidadEnemigos+2;
				}
				
				//Regresa hacia arriba si bajo demasiado la nave
				if(!Detector.estarEnEntorno(enemigos[i].x,enemigos[i].y,entorno)) {
					enemigos[i].y = 0;
				}
				
				//En caso de cerca de otro enemigo
				for(int j=0;j < enemigos.length;j++) {
					
					//Si esta muy cerca de una nave
					if (enemigos[j] != null  && enemigos[i]!=null && i != j &&
						Detector.colisiona(enemigos[i].x,enemigos[i].y, enemigos[j].x,enemigos[j].y,rangoColision)) {
						enemigos[i].cambiarDireccion();
						enemigos[j].cambiarDireccion();
					}
				}
				//En caso de colision con un proyectil de jugador
				if(nave != null && proyectilNave != null && enemigos[i]!=null && Detector.colisiona(enemigos[i].x,enemigos[i].y,proyectilNave.x,proyectilNave.y,rangoColision/2)) {
					enemigos[i]=null;
					proyectilNave=null;
					puntaje+=50;
					System.out.println("Colision con proyectil!!!");
				}
				
				
				//Si esta muy cerca de un asteroide
				for(int k=0;k<asteroides.length;k++) {
					if (asteroides[k] != null && enemigos[i]!=null &&
						Detector.colisiona(asteroides[k].x,asteroides[k].y,enemigos[i].x,enemigos[i].y,rangoColision)) {
							enemigos[i].cambiarDireccion();
						}
				}
				//Si enemigo colisiona con el jugador
				if (nave != null  && enemigos[i] != null &&
					Detector.colisiona(nave.x,nave.y,enemigos[i].x,enemigos[i].y,rangoColision)) {
					System.out.println("Colision con enemigo!!!!");
					nave.vida-=dañoEnemigos;
					enemigos[i]=null;
				}
			}
			//regenera enemigos luego de cierto tiempo si no se gano el juego aun
			if(enemigos[i] == null && jefeFinal == null && !faseJefeFinalGanado &&(
			Utiles.segundosActuales() == 00 ||
			Utiles.segundosActuales() == 30 )) {
				enemigos[i] = new Enemigo(entorno, 0.2 ,3, 10); 
			}
			
		}
		
		//JEFE FINAL
		if(jefeFinal != null) {
			jefeFinal.dibujar();
			jefeFinal.moverSoloHorizontal();
			
			//En caso de colision con un proyectil de jugador
			if(nave != null && proyectilNave != null && jefeFinal!=null && Detector.colisiona(jefeFinal.x,jefeFinal.y,proyectilNave.x,proyectilNave.y,rangoColision*4)) {
				jefeFinal.vida-=3;
				proyectilNave=null;
				puntaje+=50;
				System.out.println("Colision con proyectil!!!");
			}
			if(jefeFinal.vida<=0) {
				jefeFinal=null;
				faseJefeFinalGanado = true;
			}
			else {
				//panel de vida Jefe
				entorno.cambiarFont("Arial", 20, Color.white);
				entorno.escribirTexto("JEFE FINAL vida: ", ancho/2-75, 50);
				entorno.dibujarRectangulo(ancho/2, 70, 400, 20, 0, Color.red);
				entorno.dibujarRectangulo(ancho/2, 70, jefeFinal.vida*4, 20, 0, Color.green);
			}
			
			
		}
		
		//NAVE
		
		if(nave != null && nave.vida <= 0)
			nave=null; //elimina la nave si no tiene vida
		
		if (nave != null) {
			nave.dibujarse();
			
			if(!Utiles.hayEnemigos(enemigos)) {
				
				if(jefeFinal == null && !faseJefeFinalGanado) {
					jefeFinal = new Enemigo(entorno, 1 ,2, 2);
				}
				
				if(jefeFinal == null && faseJefeFinalGanado) {
					entorno.dibujarImagen(ganaste,400, 300, 0.0);
					
					entorno.cambiarFont("Arial", 20, Color.white);
					entorno.escribirTexto("Puntaje Final: " + puntaje, ancho/2-75, alto-50);
					entorno.escribirTexto("Nivel Alcanzado: " + nivel, ancho/2-75, alto-100);
					//eliminar asteroides
					for(int i=0;i<asteroides.length;i++) {
						asteroides[i]=null;
					}
					//eliminar iones restantes ya que termino el juego
					for(int i=0;i<ionesEnemigos.length;i++) {
						ionesEnemigos[i]=null;
					}
					extraVida=null;
					extraPuntaje=null;
				}
			}
			if(extraVida != null) {
				if(Detector.colisiona(nave.x,nave.y,extraVida.x,extraVida.y,rangoColision/2)) {
					extraVida=null;
					nave.vida+=50;
				}
			}
			if(extraPuntaje != null) {
				if(Detector.colisiona(nave.x,nave.y,extraPuntaje.x,extraPuntaje.y,rangoColision/2)) {
					extraPuntaje=null;
					puntaje+=50;
				}
			}
			
			
			entorno.cambiarFont("Arial", 20, Color.white);
			entorno.escribirTexto("Vida: " + nave.vida, ancho-200, 100);
			entorno.escribirTexto("Puntaje: " + puntaje, ancho-200, 150);
			entorno.escribirTexto("Nivel: " + nivel, ancho-200, 200);
			
			
		}else if (nave == null) {
			entorno.cambiarFont("Arial", 20, Color.white);
			entorno.dibujarImagen(gameover, ancho/2, 300, 0.0);
			entorno.escribirTexto("Puntaje Final: " + puntaje, ancho/2-75, alto-50);
			entorno.escribirTexto("Nivel Alcanzado: " + nivel, ancho/2-75, alto-100);
		}
		
		//NIVEL, se aumentara por cada cierta cantidad de puntaje
		if(puntaje==100 && nivel == 1) {
			nivel++;
			dañoEnemigos+=20;
			dañoAsteroides+=20;
			for(int i=0;i<enemigos.length;i++) { //aumenta la velocidad de las naves que existen y las que van a aparecer
				if(enemigos[i]!= null)
					enemigos[i].velocidad+=2;
				velocidadEnemigos+=2;
			}
		}
		if(puntaje==200 && nivel == 2) {
			nivel++;
			dañoEnemigos+=20;
			dañoAsteroides+=20;
			for(int i=0;i<enemigos.length;i++) { //aumenta la velocidad de las naves que existen y las que van a aparecer
				if(enemigos[i]!= null)
					enemigos[i].velocidad+=2;
				velocidadEnemigos+=2;
			}
		}
		if(puntaje==300 && nivel == 3) {
			nivel++;
			dañoEnemigos+=20;
			dañoAsteroides+=20;
			for(int i=0;i<enemigos.length;i++) { //aumenta la velocidad de las naves que existen y las que van a aparecer
				if(enemigos[i]!= null)
					enemigos[i].velocidad+=2;
				velocidadEnemigos+=2;
			}
		}
	
	}
	
	

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}
