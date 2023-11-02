package com.bo.upb.algoritmica1.ui;


import com.bo.upb.algoritmica1.model.NodeN;
import com.bo.upb.algoritmica1.model.TreeNario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.List;


public class MainForm extends JFrame {
    private static final int distanciasEntreNodos = 300; // Espacio horizontal entre los nodos
    private static final int VERTICAL_SPACING = 30;   // Espacio vertical entre los niveles del árbol
    public static final int WIDTH_NODE = 50;

    private JTextField txtValues;
    private JButton btnInsertar;
    private JButton btnClear;
    private JPanel pnlArbol;

    private JPanel pnlMain;
    private JMenuBar menuBar = new JMenuBar();

    private TreeNario<String> tn = new TreeNario<>();

    public MainForm() throws HeadlessException {
        this.setTitle("Árbol N-ario");
        this.setContentPane(pnlMain);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(1920, 700);

        this.setJMenuBar(menuBar);
        JMenu jmArchivos = new JMenu("Archivos");
        menuBar.add(jmArchivos);

        JMenuItem jmCargarArbol = new JMenuItem("Cargar Arbol");
        jmArchivos.add(jmCargarArbol);

        JMenuItem jmGuardarArbol = new JMenuItem("Guardar Arbol");
        jmArchivos.add(jmGuardarArbol);

        Font font1 = new Font("SansSerif", Font.PLAIN, 16);
        txtValues.setFont(font1);
        jmCargarArbol.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();

                int result = fileChooser.showOpenDialog(MainForm.this); // MainForm es la instancia de tu ventana principal

                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();

                    if (selectedFile.getName().endsWith(".txt")) {
                        try {
                            BufferedReader br = new BufferedReader(new FileReader(selectedFile));
                            String line;
                            while ((line = br.readLine()) != null) {
                                leerInstrucciones(line); // Procesa cada línea del archivo como una instrucción
                            }
                            br.close();
                            repaint(); // Actualiza la representación gráfica del árbol
                        } catch (IOException ex) {
                            ex.printStackTrace();
                            // Manejo de errores en caso de que ocurra una excepción al leer el archivo
                        }
                    } else {
                        // El usuario seleccionó un archivo que no es de tipo .txt, puedes mostrar un mensaje de error
                        System.err.println("El archivo seleccionado no es un archivo de texto (.txt).");
                    }
                }
            }
        });


        jmGuardarArbol.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();

                int result = fileChooser.showSaveDialog(MainForm.this); // MainForm es la instancia de tu ventana principal

                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();

                    if (!selectedFile.getName().endsWith(".txt")) {
                        // Asegúrate de que el archivo tenga una extensión .txt
                        selectedFile = new File(selectedFile.getAbsolutePath() + ".txt");
                    }

                    // Escribe las instrucciones de tu árbol en el archivo
                    try {
                        String instructions = obtenerInstrucciones(tn.getRoot());
                        if (instructions != null) {
                            BufferedWriter bw = new BufferedWriter(new FileWriter(selectedFile));
                            System.out.println("Guardando instrucciones en: " + selectedFile.getAbsolutePath());
                            Utils.writeStringToFile(selectedFile, instructions);
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        // Manejo de errores en caso de que ocurra una excepción al escribir el archivo
                    }
                }
            }
        });


        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Limpia el árbol
                tn.clear();
                repaint();
            }
        });

        btnInsertar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtiene las instrucciones ingresadas y las procesa
                String instructions = txtValues.getText();
                if (!instructions.isEmpty()) {
                    leerInstrucciones(instructions);
                    txtValues.setText("");
                }
            }
        });
    }

    private void leerInstrucciones(String instructions) {
        // Divide las instrucciones y procesa cada una
        String[] instructionList = instructions.split(";");
        for (String instruction : instructionList) {
            instruction = instruction.trim(); // Elimina espacios en blanco alrededor de la instrucción
            if (instruction.contains("<-")) {
                String[] parts = instruction.split("<-");
                if (parts.length == 2) {
                    String parentValue = parts[0].trim();
                    String childValue = parts[1].trim();
                    NodeN childNode = tn.search(childValue);
                    NodeN parentNode = tn.search(parentValue);
                    if (parentNode != null && childNode != null && !tn.isDescendant(parentNode, childNode)) {
                        tn.removeChild(childNode);
                        parentNode.addChild(childNode);
                    }
                    if (parentNode != null && childNode == null) {
                        childNode = new NodeN<>(childValue);
                        parentNode.addChild(childNode);
                    }
                }
            } else {
                if (tn.getRoot()==null) {
                    tn.setRootValue(instruction);
                }
            }
        }
        repaint();
    }

    private String obtenerInstrucciones(NodeN<String> root) {
        if (root == null) {
            return null;
        }

        StringBuilder instructions = new StringBuilder();
        obtenerInstrucciones(root, instructions);

        return instructions.toString();
    }

    private void obtenerInstrucciones(NodeN<String> node, StringBuilder instructions) {
        if (node != null) {
            String value = node.getValue();
            if (node == tn.getRoot()) {
                instructions.append(value).append("\n");
            }
            for (NodeN<String> child : node.getChildren()) {
                instructions.append(value).append(" <- ").append(child.getValue()).append("\n");
                obtenerInstrucciones(child, instructions);
            }
        }
    }


    public void graficarNodos(NodeN node, Graphics g2d, int x, int y, int nivel, int widthNode, int distancia) {
        if (node == null)
            return;

        g2d.setColor(Color.CYAN);
        g2d.fillOval(x, y, widthNode, widthNode);
        g2d.setColor(Color.BLACK);
        g2d.drawOval(x, y, widthNode, widthNode);
        g2d.drawString(String.valueOf(node.getValue()), x + widthNode / 3, y + widthNode / 2 + 5);

        List<NodeN> children = node.getChildren();
        int numChildren = children.size();
        int totalSpacing = (numChildren - 1) * distancia; // Cambio en el cálculo de totalSpacing
        int childX = x - totalSpacing / 2;

        for (NodeN child : children) {
            g2d.setColor(Color.BLACK);
            int childY = y + 2 * widthNode;
            g2d.drawLine(x + widthNode / 2, y + widthNode, childX + widthNode / 2, childY);
            graficarNodos(child, g2d, childX, childY, nivel + 1, widthNode, distancia);
            childX += distancia; // Incrementa la posición para el siguiente hijo
        }
    }




    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) pnlArbol.getGraphics();
        graficarNodos(tn.getRoot(), g2d, (getWidth() / 2)-150, 20, 0, WIDTH_NODE, distanciasEntreNodos);
    }
//    10;
//10 <- 20;
//10 <- 40;
//20 <- 25;
//20 <- 30;
//30 <- 35;
//10 <- 60;
//60 <- 70;
//
//10;
//10 <- 90;
//10 <- 20;
//10 <- 30;
//90 <- 80;
//90 <- 4;
//90 <- 1;
//20 <- 40;
//20 <- 50;
//30 <- 60;
//40 <- 15;
//15 <- 65;
//1<- 5;
//1<-9;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainForm());
    }
}
