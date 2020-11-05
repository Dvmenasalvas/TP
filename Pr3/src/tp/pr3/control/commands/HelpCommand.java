package tp.pr3.control.commands;



import tp.pr3.logic.multigames.Game;

/**La calse ExitCommand hereda de NoParamsCommand y ejecuta la acción de mostrar ayuda*/
public class HelpCommand extends NoParamsCommand {

	/**Da una descripción del comando help*/
	public HelpCommand() {
		super("help", "Este comando sirve para obtener ayuda sobre los comandos.");
	}

	/**Ejecuta el comando help*/
	@Override
	public boolean execute(Game game) {
		System.out.println(CommandParser.commandHelp());
		return false;
	}
}
