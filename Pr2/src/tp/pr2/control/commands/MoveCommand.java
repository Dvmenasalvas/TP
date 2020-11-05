package tp.pr2.control.commands;

import tp.pr2.Direction;
import tp.pr2.control.Controller;
import tp.pr2.logic.multigames.Game;
/**La clase hija MoveCommand gestiona los distintos metodos relacionados con los cuatro movimientos que hay*/
public class MoveCommand extends Command {
	private Direction _dir;

	/**Da una descrición del comando move*/
	public MoveCommand() {
		super("move <direction>", "Este comando sirve para salir hacer un movimiento, debe de estar seguido de una direccion valida(up, down, right o left).");
		_dir = null;
	}

	/**Ejecuta el juego*/
	@Override
	public void execute(Game game, Controller controller) {
		if(!game.move(_dir, game.getCurrentRules()))
			controller.setNoPrintGameState();
	}
    /**Devuelve cual de los posibles movimientos(up down right left) es el que se ha introducido*/
	@Override
	public Command parse(String[] commandWords, Controller controller) {
		Command salida = null;
		if(commandWords[0].toLowerCase().equals(_commandName)) {
			if(commandWords.length > 1) {
				switch(commandWords[1].toLowerCase()) {
				case "up":
					_dir = Direction.UP;
					salida = this;
					break;
				case "down":
					_dir = Direction.DOWN;
					salida = this;
					break;
				case "right":
					_dir = Direction.RIGHT;
					salida = this;
					break;
				case "left":
					_dir = Direction.LEFT;
					salida = this;
					break;
				default:
					System.out.println("Move debe estar seguido de una direccion valida: up, down, left o right.");
				}
			}
			else System.out.println("Move debe estar seguido de una direccion: up, down, left o right.");
		}
		return salida;
	}
}
