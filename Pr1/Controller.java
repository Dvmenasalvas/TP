package tp.pr1;

import java.util.Scanner;

public class Controller {
	private Game _game;
	private Scanner _in;
	
	public Controller(Game game, Scanner in) {
		_game = game;
		_in = in;
	}
	
	public void run() {
		_game.print();
		System.out.println("Introduzca el siguiente comando: ");
		String entrada;
		entrada = _in.next();
		boolean won = false;
		while (!entrada.equals("exit") && _game.getPosMove()) {
			switch(entrada.toLowerCase()) {
			case "reset":
				_game.reset();
				_game.print();
				break;
			case "move":
				_in.hasNext();
				String direction;
				Direction dir;
				direction = _in.next();
				switch(direction) {
				case "up":
					dir = Direction.UP;
					_game.move(dir);
					_game.print();
					break;
				case "down":
					dir = Direction.DOWN;
					_game.move(dir);
					_game.print();
					break;
				case "right":
					dir = Direction.RIGHT;
					_game.move(dir);
					_game.print();
					break;
				case "left":
					dir = Direction.LEFT;
					_game.move(dir);
					_game.print();
					break;
				default:
					System.out.println("Move debe estar seguido de una direccion valida: up, down, left o right.");
				}		
				break;
			case "help":
				System.out.println("Los posibles comandos a introducir son:" + '\n');
				System.out.println("Move <direction>: Realizar un movimiento en la dirección indicada(up,down,right y left)" + '\n'); 
				System.out.println("Reset: Reiniciar el tablero de juego" + '\n'); 
				System.out.println("Exit: Salir del juego" + '\n');
				break;
			default:
				if(!entrada.equals("exit")) {
					System.out.println("Por favor, introduzca un comando valido(help para ayuda).");
					System.out.println('\n');
				}
			}
			if(_game.getHighest() == 2048 && !won) {
				won = true;
				System.out.println("Well done!");
			}
			if(_game.getPosMove()) {
				_in.nextLine();
				System.out.println("Introduzca el siguiente comando: ");
				entrada = _in.next();
			}
			
		}
		System.out.println("Game Over");
	}
}
