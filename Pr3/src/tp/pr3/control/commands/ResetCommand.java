package tp.pr3.control.commands;


import tp.pr3.logic.multigames.Game;

public class ResetCommand extends NoParamsCommand {

	/**Da una descripción del comando reset*/
	public ResetCommand() {
		super("reset", "Este comando sirve para resetear el juego.");
	}

	/**Ejecuta el comando reset*/
	@Override
	public boolean execute(Game game) {
		game.reset();
		return true;
	}

}
