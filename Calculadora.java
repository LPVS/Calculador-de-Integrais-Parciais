import java.util.*;

public class Calculadora {

    public static boolean isParentesis(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {

        int a = 0, b = 0, c = 0, d = 0, n = 0, q = 0;
        int[] array = { 0, 0, 0 };
        String num, deno, tmp, numA = "", numB = "", numC = "";
        String[] partes = {"","",""};
        Scanner scan = new Scanner(System.in);

        // Recebe a entrada
        /*
        System.out.println("Digite o numerador:");
        num = scan.nextLine();
        num = num.replaceAll(" ", "");
        num = num.replaceAll("X", "x");
        System.out.println("Digite o denominador:");
        deno = scan.nextLine();
        deno = deno.replaceAll(" ", "");
        deno = deno.replaceAll("X", "x");
        */
        num = "x+5";
        deno = "(x-1)(x+2)";

        System.out.println("Numerador: " + num + "\nDenominador: " + deno);

        // Extrai a constante do numerador
        for (int i = 0; i < num.length(); i++) {
            if (num.charAt(i) == '+' || num.charAt(i) == '-') {
                c = Integer.parseInt(num.substring(i));
                break;
            } else {
                c = 0;
            }
        }

        // Extrai as constantes do denominador
        tmp = deno.replaceAll("x", "");
        for (int i = 0; i < tmp.length(); i++) {
            if (tmp.charAt(i) == '(') {
                q = i + 1;
            } else if (q != 0 && tmp.charAt(i) == ')') {
                array[n] = Integer.parseInt(tmp.substring(q, i));
                n++;
                q = 0;
            }
        }
        a = array[0];
        b = array[1];
        d = array[2];

        //Separar cada par de parenteses do denominador
        n = 0;
        for(int i = 0; i < deno.length(); i++){
            if(deno.charAt(i) == '('){
                q = i;
            } else if(deno.charAt(i) == ')'){
                partes[n] = deno.substring(q, i + 1);
                n++;
            }
        }

        //Caso sejam apenas 2 pares de parenteses no denominador
        if (d == 0) {
            // Calcula o número referente a A
            q = c - a;
            n = b - a;
            if(n < 0){
                n *= -1;
                q *= -1;
            }
            if (q % n == 0) {
                numA = Integer.toString(q / n);
            } else {
                numA = q + "/" + n;
            }

            // Calcula o número referente a B
            q = c - b;
            n = a - b;
            if(n < 0){
                n *= -1;
                q *= -1;
            }
            if (q % n == 0) {
                numB = Integer.toString(q / n);
            } else {
                numB = q + "/" + n;
                if(q >= 0){
                    numB = "+ " + numB;
                }
            }

            System.out.println(numA + "*ln" + partes[0] + " " + numB + "*ln" + partes[1] + " + C" );
        } else {
        

        }
    }
}