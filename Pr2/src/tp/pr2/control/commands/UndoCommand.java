package tp.pr2.control.commands;

import tp.pr2.control.Controller;
import tp.pr2.logic.multigames.Game;

public class UndoCommand extends NoParamsCommand {

	/**Da una descripción del comando undo*/
	public UndoCommand() {
		super("undo", "Este comando permite deshacer la ultima accion realizada.");
	}

	/**Ejecuta el comando undo*/
	@Override
	public void execute(Game game, Controller controller) {
		if(!game.Undo()) controller.setNoPrintGameState();
	}

}
