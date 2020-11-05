package tp.pr3.control;

import java.util.Scanner;

import tp.pr3.control.commands.*;
import tp.pr3.logic.multigames.Game;
import tp.pr3.exceptions.*;

/**La clase Controller se encarga de ejecutar el juego*/
public class Controller {
	private Game _game;
	private Scanner _in;
	public static final String commandErrorMsg = "Comando desconocido. Usa 'help' para ver los comandos disponibles.";
	
	public void setGame(Game game) {_game = game;}
	
	/**Construye un controlador para poder ejecutar el juego*/
	public Controller(Game game, Scanner in) {
		_game = game;
		_in = in;
	}
	
	/**Imprime el tablero, y hasta que el usuario no introduzca el comando "exit" este método lee el siguiente comando, comprueba
	 * que sea un comando válido, ejecuta la acción asociada a dicho comando y vuelve a imprimir el tablero y solicitar un nuevo comando*/
	public void run() {
		System.out.println(_game);
		String entrada;
		while (!_game.getExit()) {
			System.out.println("Introduzca el siguiente comando: ");
			entrada = _in.nextLine();							//Lectura y ejecucion del comando dado
			String[] words = entrada.split("\\s+");
			try {
			Command command = CommandParser.parseCommand(words, _in);
			if(command != null) 
				if(command.execute(_game))
					System.out.println(_game);	
			} catch (ParseException e) {
				System.out.println(commandErrorMsg);
			} catch (ExecutionException e) {
				System.out.println(e.getMessage());
			} catch (GameOverException e) {
				System.out.println(e.getMessage());
			}
		}
		_in.close();
	}
}
