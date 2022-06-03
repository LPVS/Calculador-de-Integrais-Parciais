import java.util.*;

// x + a
// ----------
// (x+b)(x+c)
//
// x + a = A(x+c) + B(x+b)
// A = a - b / c - b
// B = a - c / b - c
// =======================
// x + a
// ----------
// (x+b)(x+c)(x+d)
//
// x + a = A(x+c)(x+d) + B(x+b)(x+d) + C(x+b)(x+c)
// A = a - b / (c - b) * (d - b)
// B = a - c / (b - c) * (d - c)
// C = a - d / (b - d) * (c - d)

public class Calculadora {

    // Confere se a string possui parenteses ou se precisa fatorar
    public static boolean isParentesis(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                return true;
            }
        }
        return false;
    }

    /**
     * Método para fatorar a string utilizando Bhaskara
     * 
     * @param s : expressão a ser fatorada
     * @return String fatorada e em parenteses
     */
    public static String fatorar(String s) {
        int[] array = { 1, 0, 0 };
        double x1, x2;
        String tmp;
        int n = 0, q = 0;
        if (s.charAt(0) == 'x') {
            n++;
        }

        // Separa as constantes da expressão
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == 'x') {
                tmp = s.substring(q, i);
                if (tmp.length() > 1) {
                    array[n] = Integer.parseInt(tmp);
                } else {
                    array[n] = 1;
                }
                n++;
            } else if (s.charAt(i) == '+' || s.charAt(i) == '-') {
                q = i;
            }
        }
        tmp = s.substring(q);
        if (tmp.length() > 1) {
            array[n] = Integer.parseInt(tmp);
        }

        // Calculo do delta
        n = (array[1] * array[1]) + (-4 * (array[0] * array[2]));

        if (n >= 0) {
            x1 = (double) ((-(array[1]) + Math.sqrt(n)) / 2 * array[0]);
            x2 = (double) ((-(array[1]) - Math.sqrt(n)) / 2 * array[0]);
            if (x1 > 0) {
                tmp = "(x-" + (int) x1 + ")";
            } else {
                tmp = "(x+" + (int) (-1 * x1) + ")";
            }
            if (x2 > 0) {
                s = tmp + "(x-" + (int) x2 + ")";
            } else {
                s = tmp + "(x+" + (int) (-1 * x2) + ")";
            }
        } else {
            System.out.println("ERRO (DENOMINADOR): Delta não possui raiz!");
            System.exit(0);
        }
        return s;
    }

    /**
     * Método para calcular números de uma string
     * 
     * @param s : String com os números
     * @return : resultado do calculo
     */
    public static int calcular(String s) {
        int n = 0;
        s = s.substring(0, s.length() - 1);
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) == '+') {
                n = Integer.parseInt(s.substring(1, i)) + Integer.parseInt(s.substring(i));
                break;
            } else if (s.charAt(i) == '-') {
                n = Integer.parseInt(s.substring(1, i)) - Integer.parseInt(s.substring(i + 1));
                break;
            }
        }
        return n;
    }

    public static void main(String[] args) {
        int a = 0, b = 0, c = 0, d = 0, n = 0, m = 0, q = 1, lmtBaixo = 0, lmtCima = 0;
        int[] array = { 0, 0, 0 };
        String num, deno, tmp, numA = "", numB = "", numC = "";
        String[] partes = { "", "", "" };
        Scanner scan = new Scanner(System.in);

        // Recebe a entrada

        System.out.println("Digite o numerador:");
        num = scan.nextLine();
        num = num.replaceAll(" ", "");
        num = num.replaceAll("X", "x");
        System.out.println("Digite o denominador:");
        deno = scan.nextLine();
        deno = deno.replaceAll(" ", "");
        deno = deno.replaceAll("X", "x");
        System.out.println("Digite o limite superior e inferior (Escreva 0 0 para ser indefinida):");
        lmtCima = scan.nextInt();
        lmtBaixo = scan.nextInt();

        if(lmtCima < lmtBaixo){
            System.out.println("ERRO: limite superior menor que inferior.");
        }

        if (!isParentesis(deno)) {
            deno = fatorar(deno);
        }
        System.out.println("Numerador: " + num + "\nDenominador: " + deno);

        // Extrai as constantes do numerador
        for (int i = 0; i < num.length(); i++) {
            if (i != 0 && num.charAt(i) == 'x') {
                q = Integer.parseInt(num.substring(0, i));
            }
            if (num.charAt(i) == '+' || num.charAt(i) == '-') {
                a = Integer.parseInt(num.substring(i));
                break;
            } else {
                a = 0;
            }
        }

        // Extrai as constantes do denominador
        tmp = deno.replaceAll("x", "");
        for (int i = 0; i < tmp.length(); i++) {
            if (tmp.charAt(i) == '(') {
                m = i + 1;
            } else if (m != 0 && tmp.charAt(i) == ')') {
                array[n] = Integer.parseInt(tmp.substring(m, i));
                n++;
                m = 0;
            }
        }
        b = array[0];
        c = array[1];
        d = array[2];

        // Separar cada par de parenteses do denominador
        n = 0;
        for (int i = 0; i < deno.length(); i++) {
            if (deno.charAt(i) == '(') {
                m = i;
            } else if (deno.charAt(i) == ')') {
                partes[n] = deno.substring(m, i + 1);
                n++;
            }
        }

        tmp = "";
        // Calcula o número referente a A
        m = a - (b * q);
        if (d == 0) { // Caso possua apenas 2 pares de parenteses
            n = c - b;
        } else {
            n = (c - b) * (d - b);
        }
        if (m != 0 || !(partes[0].isBlank())) {
            if (n < 0) { // se for negativo inverte os valores
                n *= -1;
                m *= -1;
            }
            if (n == 0) {
                numA = Integer.toString(m);
            } else if (m % n == 0) {
                numA = Integer.toString(m / n);
            } else {
                numA = m + "/" + n;
            }
            tmp += numA + "*ln" + partes[0] + " ";
        }

        // Calcula o número referente a B
        m = a - (c * q);
        if (d == 0) {
            n = b - c;
        } else {
            n = (b - c) * (d - c);
        }
        if (m != 0 || !(partes[1].isBlank())) {
            if (n < 0) {
                n *= -1;
                m *= -1;
            }

            if (n == 0) {
                numB = Integer.toString(m);
            } else if (m % n == 0) {
                numB = Integer.toString(m / n);
            } else {
                numB = m + "/" + n;
                if (m >= 0) {
                    numB = "+ " + numB;
                }
            }
            tmp += numB + "*ln" + partes[1] + " ";
        }

        // Calcula o número referente a C
        if (!(partes[2].isBlank())) {
            m = a - (d * q);
            n = (b - d) * (c - d);

            if (m != 0) {
                if (n < 0) {
                    n *= -1;
                    m *= -1;
                }

                if (n == 0) {
                    numC = Integer.toString(m);
                } else if (m % n == 0) {
                    numC = Integer.toString(m / n);
                } else {
                    numC = m + "/" + n;
                    if (m >= 0) {
                        numC = "+ " + numC;
                    }
                }

                tmp += numC + "*ln" + partes[2] + " ";
            }
        }

        // Calculo do limite
        if (lmtCima != 0 || lmtBaixo != 0) {
            tmp = "";
            array[0] = calcular(partes[0].replaceAll("x", Integer.toString(lmtCima)));
            array[1] = calcular(partes[1].replaceAll("x", Integer.toString(lmtCima)));
            if (!partes[2].isBlank()) {
                array[2] = calcular(partes[2].replaceAll("x", Integer.toString(lmtCima)));
            }
            if (array[0] != 0) {
                tmp += numA + "*ln(" + array[0] + ") ";
            }
            if (array[1] != 0) {
                tmp += numB + "*ln(" + array[1] + ") ";
            }
            if (array[2] != 0) {
                tmp += numC + "*ln(" + array[0] + ") ";
            }

            array[0] = calcular(partes[0].replaceAll("x", Integer.toString(lmtBaixo)));
            array[1] = calcular(partes[1].replaceAll("x", Integer.toString(lmtBaixo)));
            if (!partes[2].isBlank()) {
                array[2] = calcular(partes[2].replaceAll("x", Integer.toString(lmtBaixo)));
            }

            tmp += "- (";
            if (array[0] != 0) {
                tmp += numA + "*ln(" + array[0] + ") ";
            }
            if (array[1] != 0) {
                tmp += numB + "*ln(" + array[1] + ") ";
            }
            if (array[2] != 0) {
                tmp += numC + "*ln(" + array[0] + ") ";
            }

            tmp += ") ";
        }

        System.out.println(tmp + "+ C");

    }

}