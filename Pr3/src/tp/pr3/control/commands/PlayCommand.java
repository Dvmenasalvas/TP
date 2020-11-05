
package tp.pr3.control.commands;

import java.util.Random;
import tp.pr3.exceptions.*;
import java.util.Scanner;
import tp.pr3.logic.multigames.GameType;
import tp.pr3.logic.multigames.*;


/**La clase PlayCommand hereda directamente de Command y se encarga de gestionar el comando play que sirve para cambiar la modalidad de juego*/
public class PlayCommand extends Command {
	GameType _gameType;
	int _boardSize = 4, _initCells = 2;
	long _seed = new Random().nextInt(1000);
	

	public PlayCommand() {
		super("play <game>", "Este comando permite cambiar de modo de juego. Actualmente las opciones son: " + GameType.externaliseAll());
	}
	
	/**Asegura que la cadena devuelta es vacia, o representa un solo entero positivo*/
	private static int readPosInt(Scanner in) { //Asegura que la cadena devuelta es vacia, o representa un solo entero positivo
		String entrada;
		int salida  = 0;
		boolean valid = false;
		while (!valid) {
			entrada = in.nextLine();						
			try {
				if(entrada.equals("")) {
					valid = true;
					salida = 0;
				}
				else {
					salida = Integer.parseInt(entrada);
					if(salida <= 0) System.out.println("El entero debe ser positivo: ");
					else valid = true;
				}
			}
			catch(Exception NumberFormatException) {
				System.out.println("Por favor, introduzca un solo entero positivo o pulse enter: ");
			}
		}
		return salida;
	}

	/**Pide al usuario los parametros necesarios para iniciar una nueva partida y crea la partida teniendo en cuenta la modalidad de  juego escogida */
	@Override
	public boolean execute(Game game) {
		game.setGame(_boardSize, _initCells, _seed, _gameType);
		return true;
	}

	/**Comprueba que play esta sucedido por una modalidad de juego posible*/
	@Override
	public Command parse(String[] commandWords, Scanner in) throws ExecutionException{
		Command salida = null;
		if(commandWords[0].equals(_commandName)){
			if(commandWords.length == 2) {
				_gameType = GameType.parse(commandWords[1].toLowerCase());
				if(_gameType == null)
					throw new ExecutionException("Play debe estar seguido de una modalidad de juego valida: " + GameType.externaliseAll() + ".");
				
				System.out.println("Por favor, introduzca el tamanio del tablero: ");
				int entrada = readPosInt(in);
				if(entrada == 0) {
					_boardSize = 4;
					System.out.println("Usando tamanio de tablero por defecto: " + _boardSize);
				}
				else _boardSize = entrada;
				
				System.out.println("Por favor, introduzca el el numero de celdas iniciales: ");
				boolean valid = false;
				while(!valid) {	//Comprobamos que no hay demasiadas casillas iniciales
					entrada = readPosInt(in);
					if(entrada == 0){								//Casillas iniciales por defecto
						valid = true;
						_initCells = 2;
						System.out.println("Usando el numero de casillas iniciales por defecto: " + _initCells);
					}
					else if(entrada <= _boardSize * _boardSize) {	//Numero de casillas iniciales valido
						valid = true;		
						_initCells = entrada;
					}
					else System.out.println("El numero de casillas iniciales debe ser menor que el numero de celdas del tablero: ");
				}
				
				System.out.println("Por favor, introduzca la semilla para el generador de numeros pseudo-aleatorios: ");
				entrada = readPosInt(in);
				if(entrada == 0) {
					_seed = new Random().nextInt(1000);
					System.out.println("Usando la semilla para el generador de numeros pseudo-aleatorios por defecto: " + _seed);
				}
				else _seed = entrada;
				salida = this;
			} 
			else if(commandWords.length == 1) throw new ExecutionException("Play debe estar seguido de una modalidad de juego: " + GameType.externaliseAll() + ".");
			else throw new ExecutionException("Play debe estar seguido de una modalidad de juego: " + GameType.externaliseAll() + ".");
		}
		return salida;
	}

}
