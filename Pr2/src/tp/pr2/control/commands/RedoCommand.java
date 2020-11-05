package tp.pr2.control.commands;

import tp.pr2.control.Controller;
import tp.pr2.logic.multigames.Game;

public class RedoCommand extends NoParamsCommand {

	/**Da una descripción del comando redo*/
	public RedoCommand() {
		super("redo", "Este comando permite repetir la ultima accion efectuada.");
	}

	/**Ejecuta el comando redo*/
	@Override
	public void execute(Game game, Controller controller) {
		if(!game.Redo()) controller.setNoPrintGameState();
	}

}