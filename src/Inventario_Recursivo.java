import java.util.HashMap;
import java.util.Map;

public class Inventario_Recursivo {
    //Solucion Recurisiva
    public static int maximizar_Recursivo(Producto[] productos, int capacidad, int presupuesto, int n){
        if (n == 0 || capacidad == 0 || presupuesto == 0) {
            return 0;
        }
        if (productos[n - 1].peso > capacidad || productos[n - 1].costo > presupuesto) {
            return maximizar_Recursivo(productos, capacidad, presupuesto, n - 1);
        }

        int incluir = productos[n - 1].valor + maximizar_Recursivo(
            productos, 
            capacidad - productos[n - 1].peso, 
            presupuesto - productos[n - 1].costo, n - 1);

            int No_Incluye = maximizar_Recursivo(productos, capacidad, presupuesto, n - 1);

            return Math.max(incluir, No_Incluye);
    }

    //Solucion por Bottom-up
    public static int maximizar_ButtomUp(Producto[] productos, int capacidad, int presupuesto){
        int n = productos.length;
        int[][][] dp = new int[n + 1][capacidad + 1][presupuesto + 1];

        for(int i = 1; i <= n; i++){
            Producto producto = productos[i - 1];
            for(int cap = 0; cap <= capacidad; cap++){
                for(int pres = 0; pres <= presupuesto; pres++){
                    if (producto.peso > cap || producto.costo > pres) {
                        dp[i][cap][pres] = dp[i - 1][cap][pres];
                    }else{
                        dp[i][cap][pres] = Math.max(dp[i-1][cap][pres], producto.valor + dp[i - 1][cap - producto.peso][pres - producto.costo]);
                    }
                }
            }
        }
        return dp[n][capacidad][presupuesto];
    }
    
    //Solucion por Top-Down con Memorizacion
    public static int maximizar_TopDown(Producto[] productos, int capacidad, int presupuesto){
        Map<String, Integer> memo = new HashMap<>();
        return maximizar_TopDown_Helper(productos, capacidad, presupuesto, productos.length, memo);
    }

    private static int maximizar_TopDown_Helper(Producto[] productos, int capacidad, int presupuesto, int n, Map<String, Integer> memo){
        if (n == 0 || capacidad == 0 || presupuesto == 0) {
            return 0;
        }

        String key = n + "|" + capacidad + "|" + presupuesto;

        if (memo.containsKey(key)) {
            return memo.get(key);
        }

        int resultado;
        if (productos[n - 1].peso > capacidad || productos[n - 1].costo > presupuesto) {
            resultado = maximizar_TopDown_Helper(productos, capacidad, presupuesto, n - 1, memo);
        }else{
            int incluir = productos[n - 1].valor + maximizar_TopDown_Helper(
                productos, capacidad - productos[n - 1].peso, presupuesto - productos[n - 1].costo, n - 1, memo);
                
            int No_Incluye = maximizar_TopDown_Helper(productos, capacidad, presupuesto, n - 1, memo);

            resultado = Math.max(incluir, No_Incluye);
        }

        memo.put(key, resultado);
        return resultado;
    }

    //Metodo principal donde ejecutaremos las pruebas

    public static void main(String[] args) {
        Producto[] productos = {
            new Producto(60, 10, 20),
            new Producto(100, 20, 50),
            new Producto(120, 30, 60)

        };

        int capacidad = 50;
        int presupuesto = 100;

        System.out.println("Solucion Recursiva: " + maximizar_Recursivo(productos, capacidad, presupuesto, productos.length));
        System.out.println("Solucion Bottom-Up: " + maximizar_ButtomUp(productos, capacidad, presupuesto));
        System.out.println("Solucion Top-Down: " + maximizar_TopDown(productos, capacidad, presupuesto));

        //Medicion de tiempos de ejecucion
        long inicio,fin;

        inicio = System.nanoTime();
        maximizar_Recursivo(productos, capacidad, presupuesto, productos.length);
        fin = System.nanoTime();
        System.out.println("Tiempo Recursivo: " + (fin - inicio) + "ns");

        inicio = System.nanoTime();
        maximizar_ButtomUp(productos, capacidad, presupuesto);
        fin = System.nanoTime();
        System.out.println("Tiempo Bottom-Up: " + (fin - inicio) + "ns");

        inicio = System.nanoTime();
        maximizar_TopDown(productos, capacidad, presupuesto);
        fin = System.nanoTime();
        System.out.println("Tiempo Top-Down: " + (fin - inicio) + "ns");
    }
}
