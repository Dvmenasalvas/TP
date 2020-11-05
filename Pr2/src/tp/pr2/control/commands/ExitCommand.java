package tp.pr2.control.commands;

import tp.pr2.control.Controller;
import tp.pr2.logic.multigames.Game;
/**La calse ExitCommand hereda de NoParamsCommand y ejecuta la acción de salir del juego*/
public class ExitCommand extends NoParamsCommand {

	/**Da una descripción del comando exit*/
	public ExitCommand() {
		super("exit", "Este comando sirve para salir del programa");
	}

	/**Ejecuta el comando exit*/
	@Override
	public void execute(Game game, Controller controller) {
		game.exit();
		controller.setNoPrintGameState();
	}

}
