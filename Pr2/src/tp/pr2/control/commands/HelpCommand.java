package tp.pr2.control.commands;

import tp.pr2.control.Controller;
import tp.pr2.logic.multigames.Game;

/**La calse ExitCommand hereda de NoParamsCommand y ejecuta la acción de mostrar ayuda*/
public class HelpCommand extends NoParamsCommand {

	/**Da una descripción del comando help*/
	public HelpCommand() {
		super("help", "Este comando sirve para obtener ayuda sobre los comandos.");
	}

	/**Ejecuta el comando help*/
	@Override
	public void execute(Game game, Controller controller) {
		System.out.println(CommandParser.commandHelp());
		controller.setNoPrintGameState();
	}

}
