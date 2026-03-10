import java.util.Scanner;

public class CifradoCesarMatriz {

    static char[][] matriz = {
            {'a','b','c','d'},
            {'e','f','g','h'},
            {'i','j','k','l'},
            {'m','n','o','p'},
            {'q','r','s','t'},
            {'u','v','w','x'},
            {'y','z',' ',' '}
    };

    static char[] alfabeto = new char[28];

    public static void construirAlfabeto(){
        int k = 0;
        for(int i=0;i<matriz.length;i++)
            for(int j=0;j<matriz[i].length;j++)
                alfabeto[k++] = matriz[i][j];
    }

    public static int buscarIndice(char c){
        for(int i=0;i<alfabeto.length;i++)
            if(alfabeto[i]==c)
                return i;
        return -1;
    }

    public static String cifrar(String texto,int d){
        String r="";
        for(char c: texto.toCharArray()){
            int i = buscarIndice(c);
            if(i!=-1)
                r += alfabeto[(i+d)%alfabeto.length];
            else
                r += c;
        }
        return r;
    }

    public static String descifrar(String texto,int d){
        String r="";
        for(char c: texto.toCharArray()){
            int i = buscarIndice(c);
            if(i!=-1)
                r += alfabeto[(i-d+alfabeto.length)%alfabeto.length];
            else
                r += c;
        }
        return r;
    }

    public static void fuerzaBruta(String texto){
        for(int d=0; d<alfabeto.length; d++){
            System.out.println("k=" + d + " -> " + descifrar(texto,d));
        }
    }

    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);
        construirAlfabeto();

        while(true){

            System.out.println("\n1. Cifrar");
            System.out.println("2. Descifrar");
            System.out.println("3. Fuerza bruta");
            System.out.println("4. Salir");
            System.out.print("Opcion: ");
            int op = sc.nextInt();
            sc.nextLine();

            if(op==4) break;

            System.out.print("Mensaje: ");
            String msg = sc.nextLine().toLowerCase();

            if(op==3){
                fuerzaBruta(msg);
                continue;
            }

            System.out.print("Desplazamiento: ");
            int d = sc.nextInt();
            sc.nextLine();

            if(op==1)
                System.out.println("Cifrado: " + cifrar(msg,d));
            else if(op==2)
                System.out.println("Descifrado: " + descifrar(msg,d));
        }
    }
}