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
    
    
}
