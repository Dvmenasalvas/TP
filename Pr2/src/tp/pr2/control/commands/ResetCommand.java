package tp.pr2.control.commands;

import tp.pr2.control.Controller;
import tp.pr2.logic.multigames.Game;

public class ResetCommand extends NoParamsCommand {

	/**Da una descripción del comando reset*/
	public ResetCommand() {
		super("reset", "Este comando sirve para resetear el juego.");
	}

	/**Ejecuta el comando reset*/
	@Override
	public void execute(Game game, Controller controller) {
		game.reset();
	}

}
