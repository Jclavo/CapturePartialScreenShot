package com.jclavo.capturePartialScreen;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class App {

    private JPanel panelMain;
    private JButton btnSelect;
    private JTextArea textArea1;
    private JButton btnOpenWindow;
    private JButton btnCapture;
    private JTextField txtCantidad;
    private JLabel lblPages;

    //objetos
    private Robot robot =  new Robot();

    //
    private int contador_screenShots = 1;
    private int limit_screenShots;
    //variables
    private int contador_click = 1;
    private int valor_X = 0;
    private int valor_Y = 0;
    private int valor_width = 0;
    private int valor_height = 0;

    private int x = 0;
    private int y = 0;

    private int flag_capture_points = 0;

    TransparentWindow tw = null;
    //private static JFrame frame = new JFrame("App");


    public App() throws AWTException {

        btnOpenWindow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                tw = new TransparentWindow();
                // Set the window to 55% opaque (45% translucent).
                tw.setOpacity(0.55f);

                //Resizable.
                tw.addMouseListener(new MouseListener() {
                    public void mousePressed(MouseEvent me) { }
                    public void mouseReleased(MouseEvent me) {
                    }
                    public void mouseEntered(MouseEvent me) { }
                    public void mouseExited(MouseEvent me) { }
                    public void mouseClicked(MouseEvent me) {

                        if(flag_capture_points == 1) {

                            x = me.getLocationOnScreen().x;
                            y = me.getLocationOnScreen().y;

                            switch (contador_click) {
                                case 1:
                                    valor_X = x;
                                    valor_Y = y;
                                    break;
                                case 2:
                                    valor_width = x - valor_X;
                                    break;
                                case 3:
                                    valor_height = y - valor_Y;
                                    break;
                                case 4:
                                    contador_click = 0;
                                    break;
                            }

                            textArea1.append("X:" + x + " Y:" + y + "\n");

                            contador_click++;
                        }

                    }
                });

                // Display the window.
                tw.setVisible(true);

                //(String.valueOf(tw.getLocation())
            }
        });
        btnSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flag_capture_points = 1;
            }
        });
        btnCapture.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                if (valor_X > 0)

                tw.dispose();

                limit_screenShots = Integer.parseInt(txtCantidad.getText());
                //textArea1.append("ScreenShots will start in 5 seg. " + "\n");
                //textArea1.append("Focus on the selected area. " + "\n");
                robot.delay(5000);
                while (contador_screenShots<=limit_screenShots) {

                    //obtenemos nuestra pantalla como imagen
                    BufferedImage pantalla = robot.createScreenCapture(new Rectangle(valor_X, valor_Y, valor_width, valor_height));

                    //creamos un archivo de extensión .JPG en el directorio home del usuario del sistema
                    //File file = new File(System.getProperty("user.home") + File.separator + "imagen_" + contador_screenShots + ".jpg");
                    File file = new File("C:\\Users\\jclavota\\Documents\\books" + File.separator + "imagen_" + contador_screenShots + ".jpg");
                    //guardamos el contenido de la imagen en el archivo .JPG
                    try {
                        ImageIO.write(pantalla, "jpg", file);
                        contador_screenShots++;
                        textArea1.append(file + "\n");
                    } catch (IOException e1) {
                        textArea1.append("Error al crear el fichero " + "\n");
                        e1.printStackTrace();
                    }

                    robot.keyPress(KeyEvent.VK_CONTROL);
                    robot.keyPress(KeyEvent.VK_RIGHT);
                    robot.delay(1);
                    robot.keyRelease(KeyEvent.VK_RIGHT);
                    robot.keyRelease(KeyEvent.VK_CONTROL);
                    robot.delay(15000);
                }
            }
        });
    }

    public static void main(String[] args) throws AWTException {

        //obtenemos tamaño total de la pantalla
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        //JFrame frame = new JFrame("App");
        JFrame frame = new TransparentWindow();
        frame.setContentPane(new App().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.pack();
        frame.setUndecorated(true);
        frame.setDefaultLookAndFeelDecorated(true);
        frame.setOpacity(0.80f);
        frame.setSize(200,300);
        frame.setLocation(screenSize.width - 300,0);
        frame.setVisible(true);

        /*JFrame frame = new JFrame("App");
        frame.setContentPane(new App().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(200,300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);*/
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
