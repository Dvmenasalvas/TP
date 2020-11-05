//Hecho por Daniel Valverde Menasalvas y Sergio Gil Gavela

package tp.pr1;

import java.util.Scanner;
import java.util.Random;

public class Game2048 {
	public static void main(String []args){
		Scanner in = new Scanner(System.in);
		int dim = Integer.parseInt(args[0]);
		int cells = Integer.parseInt(args[1]);
		long seed;
		
		if(args.length == 3) { //Comprueba si hemos metido una semilla como parametro
			seed = Long.parseLong(args[2]);
		}
		else { //Si no crea una aleatoria
			seed = new Random().nextInt(1000);
		}
		
		Random random = new Random(seed); //Crea nuestro generador de numero aleatorios
		if(cells <= dim * dim && cells >= 0 && dim > 0) {
		Game game = new Game(dim, cells, random); //Crea nuestro juego
		Controller controller = new Controller(game, in); //Crea un controlador
		controller.run(); //Ejecuta el juego
		}
		else System.out.println("Los argumentos introducidos al programa son incorrectos.");
		in.close();
	}
}