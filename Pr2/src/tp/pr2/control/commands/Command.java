package tp.pr2.control.commands;

import tp.pr2.control.Controller;
import tp.pr2.logic.multigames.Game;
/**La clase abstracta Command será luego heredada por la clase NoParamsCommand */
public abstract class Command {
	private String _helpText;
	private String _commandText;
	protected final String _commandName;
	
	/**Construye la clase Command utilizando el nombre del comando y la descripción del comando*/
	public Command(String commandInfo, String helpInfo) {
		_commandText = commandInfo;
		_helpText = helpInfo;
		String[] commandInfoWords = _commandText.split("\\s+");
		_commandName = commandInfoWords[0];
	}
	
	/**Metodo abstracto que es heredo por NoParamsCommand*/
	public abstract void execute(Game game, Controller controller);
	
	/**Metodo abstracto que es heredo por NoParamsCommand*/
	public abstract Command parse(String[] commandWords, Controller controller);
	
	/**Devuelve un String con el nombre y descripcion del comando*/
	public String helpText() {
		return " " + _commandText + ": " + _helpText;
	}
} 