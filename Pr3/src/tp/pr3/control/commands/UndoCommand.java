package tp.pr3.control.commands;

import tp.pr3.logic.multigames.Game;

public class UndoCommand extends NoParamsCommand {

	/**Da una descripción del comando undo*/
	public UndoCommand() {
		super("undo", "Este comando permite deshacer la ultima accion realizada.");
	}

	/**Ejecuta el comando undo*/
	@Override
	public boolean execute(Game game) {
		if(!game.Undo()) return false;
		else return true;
	}

}
