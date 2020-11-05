package tp.pr3.control.commands;

import tp.pr3.logic.multigames.Game;

public class RedoCommand extends NoParamsCommand {

	/**Da una descripción del comando redo*/
	public RedoCommand() {
		super("redo", "Este comando permite repetir la ultima accion efectuada.");
	}

	/**Ejecuta el comando redo*/
	@Override
	public boolean execute(Game game) {
		if(!game.Redo()) return false;
		else return true;
	}

}