package tp.pr2.control.commands;

import java.util.Random;

import tp.pr2.GameType;
import tp.pr2.logic.multigames.*;

import java.util.Scanner;

import tp.pr2.control.Controller;
/**La clase PlayCommand hereda directamente de Command y se encarga de gestionar el comando play que sirve para cambiar la modalidad de juego*/
public class PlayCommand extends Command {
	GameType _gameType;
	int _boardSize = 4, _initCells = 2;
	long _seed = new Random().nextInt(1000);
	

	public PlayCommand() {
		super("play <game>", "Este comando permite cambiar de modo de juego. Actualmente las opciones son: original, fib o inverse");
	}
	
	/**Asegura que la cadena devuelta es vacia, o representa un solo entero positivo*/
	private static int readPosInt(Scanner in) { //Asegura que la cadena devuelta es vacia, o representa un solo entero positivo
		String entrada;
		int salida  = 0;
		boolean valid = false;
		while (!valid) {
			entrada = in.nextLine();						
			String[] words = entrada.split("\\s+");
			if(words.length == 1) {
				if(words[0].equals("")) {
					valid = true;
					salida = 0;
				}
				else {
					salida = Integer.parseInt(words[0]);
					if(salida <= 0) System.out.println("El entero debe ser positivo: ");
					else valid = true;
				}
			}
			else System.out.println("Por favor, introduzca un solo entero positivo o pulse enter: ");
		}
		return salida;
	}

	/**Pide al usuario los parametros necesarios para iniciar una nueva partida y crea la partida teniendo en cuenta la modalidad de  juego escogida */
	@Override
	public void execute(Game game, Controller controller) {
		System.out.println("Por favor, introduzca el tamanio del tablero: ");
		int entrada = readPosInt(controller.getIn());
		if(entrada == 0) System.out.println("Usando tamanio de tablero por defecto: " + _boardSize);
		else _boardSize = entrada;
		
		System.out.println("Por favor, introduzca el el numero de celdas iniciales: ");
		boolean valid = false;
		while(!valid) {	//Comprobamos que no hay demasiadas casillas iniciales
			entrada = readPosInt(controller.getIn());
			if(entrada == 0){								//Casillas iniciales por defecto
				valid = true;		
				System.out.println("Usando el numero de casillas iniciales por defecto: " + _initCells);
			}
			else if(entrada <= _boardSize * _boardSize) {	//Numero de casillas iniciales valido
				valid = true;		
				_initCells = entrada;
			}
			else System.out.println("El numero de casillas iniciales debe ser menor que el numero de celdas del tablero: ");
		}
		
		System.out.println("Por favor, introduzca la semilla para el generador de numeros pseudo-aleatorios: ");
		entrada = readPosInt(controller.getIn());
		if(entrada == 0)System.out.println("Usando la semilla para el generador de numeros pseudo-aleatorios por defecto: " + _seed);
		else _seed = entrada;
		
		GameRules rules = null;
		switch(_gameType){
		case ORIG: 
			rules = new Rules2048();
			break;
		case FIB: 
			rules = new RulesFib();
			break;
		case INV:
			rules = new RulesInverse();
			break;
		}
		controller.setGame(new Game(_boardSize, _initCells, _seed, rules));

	}

	/**Comprueba que play esta sucedido por una modalidad de juego posible*/
	@Override
	public Command parse(String[] commandWords, Controller controller) {
		Command salida = null;
		if(commandWords[0].toLowerCase().equals(_commandName)) {
			if(commandWords.length > 1) {
				switch(commandWords[1].toLowerCase()) {
				case "original":
					_gameType = GameType.ORIG;
					salida = this;
					break;
				case "fib":
					_gameType = GameType.FIB;
					salida = this;
					break;
				case "inverse":
					_gameType = GameType.INV;
					salida = this;
					break;
				default:
					System.out.println("Play debe estar seguido de una modalidad de juego valida: original, fib o inverse.");
				}
			}
			else System.out.println("Play debe estar seguido de una modalidad de juego: original, fib o inverse.");
		}
		return salida;
	}

}
