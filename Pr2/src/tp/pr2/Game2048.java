//Hecho por Daniel Valverde Menasalvas y Sergio Gil Gavela

package tp.pr2;
import tp.pr2.control.Controller;
import tp.pr2.logic.multigames.*;

import java.util.Scanner;
import java.util.Random;
/**La clase pública Game 2048 es el main de nuestro programa*/

public class Game2048 {

/**El main recoge los argumentos que se tienen que introducir para generar el tablero
* y poder empezar el juego, comprueban que todos sean correctos, genera una semilla aleatoria si no se le ha dado una y a partir de ahi
* construye un juego, un controlador y ejecuta el juego*/
	public static void main(String []args){
		if(args.length < 2) System.out.println("Faltan argumentos");
		else {
			
			if(args.length > 3) System.out.print("Sobran argumentos."); // Hay demasiados argumentos
			else{
				Scanner in = new Scanner(System.in);
				int dim = Integer.parseInt(args[0]);
				int cells = Integer.parseInt(args[1]);
				long seed = 0;
				if(args.length == 3) { //Tenemos 3 argumentos, nos dan la semilla
					seed = Long.parseLong(args[2]);
				}
				else if (args.length == 2) { //Tenemos dos argumentos, no hay semilla
					seed = new Random().nextInt(1000);
				}
				
				GameRules initRules = new Rules2048();
				if(cells <= dim * dim && cells > 0 && dim > 0) {		//Argumentos correctos
					Game game = new Game(dim, cells, seed, initRules); //Crea nuestro juego
					Controller controller = new Controller(game, in); //Crea un controlador
					controller.run(); //Ejecuta el juego
				}
				else System.out.println("Los argumentos introducidos al programa son incorrectos.");
				in.close();
			}
		
			
		}
	}
}