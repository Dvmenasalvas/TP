package tp.pr2.control.commands;

import tp.pr2.control.Controller;
/**Clase abstracta que hereda de Command y es heredada por algunos de los comandos simples*/
public abstract class NoParamsCommand extends Command {
	
	/**Llama al constructor de la clase padre Command*/
	public NoParamsCommand(String commandInfo, String helpInfo){
		super(commandInfo, helpInfo);
	}
	
	/**Comprueba que commandWords coincide con el nombre de algun comando y devuelve dicho comando si es asi o null si no*/
	@Override
	public Command parse(String[] commandWords, Controller controller) {
		Command comando = null;
		if(commandWords[0].toLowerCase().equals(_commandName))comando = this;
		return comando;
	}
}
