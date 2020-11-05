package tp.pr2.control;

import java.util.Scanner;
import tp.pr2.logic.multigames.Game;
import tp.pr2.control.commands.CommandParser;
import tp.pr2.control.commands.*;

/**La clase Controller se encarga de ejecutar el juego*/
public class Controller {
	private Game _game;
	private Scanner _in;
	private boolean _printGameState;
	
	public Scanner getIn() {return _in;}
	
	public void setNoPrintGameState() {_printGameState = false;}
	public void setGame(Game game) {_game = game;}
	
	/**Construye un controlador para poder ejecutar el juego*/
	public Controller(Game game, Scanner in) {
		_game = game;
		_in = in;
		_printGameState = true;
	}
	
	/**Imprime el tablero, y hasta que el usuario no introduzca el comando "exit" este método lee el siguiente comando, comprueba
	 * que sea un comando válido, ejecuta la acción asociada a dicho comando y vuelve a imprimir el tablero y solicitar un nuevo comando*/
	public void run() {
		System.out.println(_game);
		String entrada;
		while (!_game.getExit()) {
			_printGameState = true;	
			System.out.println("Introduzca el siguiente comando: ");
			entrada = _in.nextLine();							//Lectura y ejecucion del comando dado
			String[] words = entrada.split("\\s+");
			Command command = CommandParser.parseCommand(words, this);
			if(command != null) command.execute(_game, this);
			else {
				System.out.println("Por favor, introduzca un comando valido(help para ayuda).");
				System.out.println('\n');
			}
			if(_printGameState) System.out.println(_game);
			
		}
		System.out.println("Game Over");
	}
}
