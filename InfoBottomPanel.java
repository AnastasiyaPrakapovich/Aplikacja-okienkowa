package app;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Color;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
/**
 * Program <code>app.MyWindow</code>
 * The class <code>app.InfoBottomPanel</code> that defines the bottom panel
 * application containing information about the tasks being carried out.
 * @author Anastasiya Prakapovich
 * @version 1.0	01/05/2020
 */
public class InfoBottomPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private static JTextField infoTF,dbInfoTF;
    private JLabel infoLabel,dbLabel;
    /**
     * Constructor without parameters app.InfoBottomPanel.
     */
    public InfoBottomPanel() {
        createGUI();
    }
    /**
     * The method that creates a graphical user interface.
     */
    public void createGUI() {
        this.setBackground(new Color(210,210,210));
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        // Utworzenie etykiet tekstowych
        infoLabel = new JLabel("Info:");
        dbLabel = new JLabel("Status:");

        // Utworzenie obiektow TextField
        infoTF = new JTextField("Start aplikacji");
        infoTF.setMinimumSize(new Dimension(200,20));
        infoTF.putClientProperty("JComponent.sizeVariant", "small");
        dbInfoTF = new JTextField("ON");
        dbInfoTF.putClientProperty("JComponent.sizeVariant", "small");
        dbInfoTF.setMinimumSize(new Dimension(30,20));

        this.add(infoLabel);
        this.add(Box.createHorizontalStrut(10));
        this.add(infoTF);
        this.add(Box.createHorizontalStrut(20));
        this.add(dbLabel);
        this.add(Box.createHorizontalStrut(10));
        this.add(dbInfoTF);
        this.add(Box.createHorizontalStrut(20));
    }
    /**
     * Public method sets variable infoString.
     * @param infoString new variable value infoString.
     */
    public static void setInfoString(String infoString) {
        infoTF.setText(infoString);
    }
    /**
     * Public method sets variable dbStatus.
     * @param dbStatus new variable value dbStatus.
     */
    public static void setDbStatus(boolean dbStatus) {
        if(dbStatus) dbInfoTF.setText("ON");
        else dbInfoTF.setText("OFF");
    }
    /**
     * The method determines the value of the spacing from the panel edge
     * (top,left,bottom,right).
     */
    public Insets getInsets() {
        return new Insets(5,5,3,5);
    }
}
