import javax.swing.*;
import java.awt.*;

// Clase principal que crea la ventana del programa
public class CifradoCesarMatriz extends JFrame {

    // Matriz 7x4 que contiene el alfabeto usado para el cifrado
    // Incluye la letra ñ y el espacio
    static char[][] matriz = {
            {'a','b','c','d'},
            {'e','f','g','h'},
            {'i','j','k','l'},
            {'m','n','ñ','o'},
            {'p','q','r','s'},
            {'t','u','v','w'},
            {'x','y','z',' '}
    };

    // Arreglo lineal que almacenará el alfabeto completo
    // Se usa para facilitar el cálculo del desplazamiento
    static char[] alfabeto = new char[28];

    // Arreglo de etiquetas (JLabel) para mostrar visualmente la matriz
    JLabel[][] labels = new JLabel[7][4];

    // Campos donde el usuario escribe el mensaje y el desplazamiento
    JTextField campoMensaje = new JTextField();
    JTextField campoDesplazamiento = new JTextField();

    // Área donde se mostrará el resultado del cifrado o descifrado
    JTextArea areaResultado = new JTextArea();

    // Constructor: aquí se construye toda la interfaz gráfica
    public CifradoCesarMatriz() {

        // Se construye el alfabeto a partir de la matriz
        construirAlfabeto();

        setTitle("Cifrado César con Matriz 7x4");
        setSize(650,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Título del sistema
        JLabel titulo = new JLabel("Sistema de Cifrado César", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        add(titulo, BorderLayout.NORTH);

        JPanel panelCentral = new JPanel(new GridLayout(1,2,20,20));
        add(panelCentral, BorderLayout.CENTER);

        // PANEL DE LA MATRIZ
        JPanel panelMatriz = new JPanel(new GridLayout(7,4));
        panelMatriz.setBorder(BorderFactory.createTitledBorder("Matriz del Alfabeto"));

        // Se crean las etiquetas para cada celda de la matriz
        for(int i=0;i<matriz.length;i++){
            for(int j=0;j<matriz[i].length;j++){

                JLabel letra = new JLabel(String.valueOf(matriz[i][j]), JLabel.CENTER);
                letra.setFont(new Font("Consolas", Font.BOLD,18));
                letra.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                letra.setOpaque(true);
                letra.setBackground(Color.WHITE);

                labels[i][j] = letra;

                panelMatriz.add(letra);
            }
        }

        panelCentral.add(panelMatriz);

        // PANEL DE CONTROL (entrada de datos)
        JPanel panelControl = new JPanel();
        panelControl.setLayout(new GridLayout(6,1,10,10));
        panelControl.setBorder(BorderFactory.createTitledBorder("Control"));

        panelControl.add(new JLabel("Mensaje"));
        panelControl.add(campoMensaje);

        panelControl.add(new JLabel("Desplazamiento"));
        panelControl.add(campoDesplazamiento);

        // BOTONES
        JPanel botones = new JPanel();

        JButton btnCifrar = new JButton("Cifrar");
        JButton btnDescifrar = new JButton("Descifrar");
        JButton btnLimpiar = new JButton("Limpiar");

        botones.add(btnCifrar);
        botones.add(btnDescifrar);
        botones.add(btnLimpiar);

        panelControl.add(botones);

        panelCentral.add(panelControl);

        // PANEL DE RESULTADO
        areaResultado.setLineWrap(true);
        areaResultado.setEditable(false);

        JScrollPane scroll = new JScrollPane(areaResultado);
        scroll.setBorder(BorderFactory.createTitledBorder("Resultado"));

        add(scroll, BorderLayout.SOUTH);

        // EVENTO DEL BOTÓN CIFRAR
        btnCifrar.addActionListener(e -> {

            try {

                String mensaje = campoMensaje.getText();
                int desplazamiento = Integer.parseInt(campoDesplazamiento.getText());

                // Llama al método de animación para cifrar
                animarProceso(mensaje, desplazamiento, true);

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(this,"Ingresa un desplazamiento válido");

            }

        });

        // EVENTO DEL BOTÓN DESCIFRAR
        btnDescifrar.addActionListener(e -> {

            try {

                String mensaje = campoMensaje.getText();
                int desplazamiento = Integer.parseInt(campoDesplazamiento.getText());

                // Llama al método de animación para descifrar
                animarProceso(mensaje, desplazamiento, false);

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(this,"Ingresa un desplazamiento válido");

            }

        });

        // BOTÓN LIMPIAR
        btnLimpiar.addActionListener(e -> {

            campoMensaje.setText("");
            campoDesplazamiento.setText("");
            areaResultado.setText("");
            limpiarColores();

        });

        setVisible(true);
    }

    // Método que regresa todas las celdas de la matriz a color blanco
    public void limpiarColores(){

        for(int i=0;i<labels.length;i++)
            for(int j=0;j<labels[i].length;j++)
                labels[i][j].setBackground(Color.WHITE);
    }

    // Método que realiza la animación del cifrado o descifrado
    public void animarProceso(String texto,int desplazamiento,boolean cifrar){

        // Convertimos el texto a minúsculas
        final String textoFinal = texto.toLowerCase();

        StringBuilder resultado = new StringBuilder();

        // Timer que controla la velocidad de la animación
        Timer timer = new Timer(700,null);

        final int[] i = {0};

        timer.addActionListener(e->{

            limpiarColores();

            // Si ya se procesó todo el texto se muestra el resultado
            if(i[0] >= textoFinal.length()){

                areaResultado.setText((cifrar ? "Mensaje cifrado:\n\n":"Mensaje descifrado:\n\n")+resultado);
                timer.stop();
                return;
            }

            char letra = textoFinal.charAt(i[0]);

            // Se busca la posición de la letra en el alfabeto
            int indice = buscarIndice(letra);

            if(indice != -1){

                int nuevoIndice;

                // Cálculo del desplazamiento
                if(cifrar)
                    nuevoIndice = (indice + desplazamiento) % alfabeto.length;
                else{
                    nuevoIndice = indice - desplazamiento;
                    while(nuevoIndice < 0)
                        nuevoIndice += alfabeto.length;
                }

                // Se obtiene la posición en la matriz
                int fila = nuevoIndice / 4;
                int col = nuevoIndice % 4;

                // Animación visual
                if(cifrar)
                    labels[fila][col].setBackground(Color.GREEN);
                else
                    labels[fila][col].setBackground(Color.YELLOW);

                resultado.append(alfabeto[nuevoIndice]);

            }else{
                resultado.append(letra);
            }

            i[0]++;

        });

        timer.start();
    }

    // Método que copia los valores de la matriz al arreglo alfabeto
    public static void construirAlfabeto() {

        int indice = 0;

        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                alfabeto[indice] = matriz[i][j];
                indice++;
            }
        }
    }

    // Método que busca una letra dentro del alfabeto
    public static int buscarIndice(char letra) {

        for (int i = 0; i < alfabeto.length; i++) {
            if (alfabeto[i] == letra) {
                return i;
            }
        }

        return -1;
    }

    // Método principal que ejecuta el programa
    public static void main(String[] args) {

        new CifradoCesarMatriz();

    }
}