import java.util.*;

public class CifradoCesarMatriz {

static char[][] m={
{'a','b','c','d'},
{'e','f','g','h'},
{'i','j','k','l'},
{'m','n','o','p'},
{'q','r','s','t'},
{'u','v','w','x'},
{'y','z',' '}
};

static String a="";

static void construir(){
for(char[] f:m) for(char c:f) a+=c;
}

static String cifrar(String t,int d){
d%=a.length();
String r="";
for(char c:t.toCharArray()){
int i=a.indexOf(c);
r+= i>=0 ? a.charAt((i+d)%a.length()) : c;
}
return r;
}

static String descifrar(String t,int d){
d%=a.length();
String r="";
for(char c:t.toCharArray()){
int i=a.indexOf(c);
r+= i>=0 ? a.charAt((i-d+a.length())%a.length()) : c;
}
return r;
}

static void fuerza(String t){
for(int i=0;i<a.length();i++)
System.out.println("k="+i+" -> "+descifrar(t,i));
}

public static void main(String[] args){
Scanner s=new Scanner(System.in);
construir();

while(true){
System.out.println("\n1 Cifrar\n2 Descifrar\n3 Fuerza\n4 Salir");
int op=s.nextInt(); s.nextLine();
if(op==4) break;

System.out.print("Mensaje: ");
String t=s.nextLine().toLowerCase();

if(op==3){fuerza(t); continue;}

System.out.print("Desplazamiento: ");
int d=s.nextInt(); s.nextLine();

System.out.println(op==1?cifrar(t,d):descifrar(t,d));
}
}
}