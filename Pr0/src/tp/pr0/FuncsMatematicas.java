package tp.pr0;

public class FuncsMatematicas {
	/**Toma como argumento un entero n y devuelve 0 si n<0, 1 si n=1 0 n= 0 y n! si n>1*/
	public static int factorial(int n){
		int result;
		if(n<0) result = 0;
		else if(n <= 1) result = 1;
		else result = n * factorial(n-1);
		return result;
	}
	/**El método estático combinatorio toma como argumento dos números enteros y retoma su nñumero combinarorio factorial(n)\factorial(k)*factorial(n-k)*/
	public static int combinatorio(int n, int k){
		if(k<0 || n<k) return 0;
		else return factorial(n)/(factorial(k)*factorial(n-k));
	}
	/**El método principal permite que la clase sea ejecutada como aplicación y llamar a otros métodos*/
	public static void main(String args[]) {
		for (int i = 0; i < 6; ++i) {
			for (int j = 0; j <= i; ++j)
			System.out.print(FuncsMatematicas.combinatorio(i,j) + " ");
			System.out.println();
			}
	}
}