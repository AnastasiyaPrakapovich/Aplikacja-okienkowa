package app;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Program <code>app.MyWindow</code>
 * The class  <code>app.Window</code> shows image of app.BarChart.
 * @author Anastasiya Prakapovich
 * @version 1.0	1/05/2020
 */
public class Window extends JDialog implements ActionListener {
    private JButton jBOk;
    private JLabel lIcon;
    /**
     * Class constructor without parameters app.Window.
     */
    public Window() {
        this.setTitle("Wykres JPEG");
        this.setModal(true);
        this.setSize(370,380);
        this.setResizable(false);

        // obsluga zdarzenia okna
        this.addWindowListener	(new WindowAdapter(){
            // obsluga wcisniecia przycisku zamkniecia okna
            public void windowClosing(WindowEvent e){
                setVisible(false);
            }
        });

        // pobranie rozmiarow aplikacji
        Dimension dialogSize = getSize();
        // pobranie rozdzielczosci pulpitu
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        if(dialogSize.height > screenSize.height)
            dialogSize.height = screenSize.height;
        if(dialogSize.width > screenSize.width)
            dialogSize.height = screenSize.width;

        // rozmieszczenie aplikacji na srodku ekranu
        setLocation((screenSize.width-dialogSize.width)/2,
                (screenSize.height-dialogSize.height)/2);

        Container contentPane = getContentPane();
        contentPane.setBackground(Color.white);
        contentPane.setLayout(null);

        try {
            lIcon = new JLabel(new ImageIcon(
                    getClass().getResource("grafika/PieChart.jpeg")));
        }
        catch(Exception e) {
            lIcon = new JLabel();
        }

        jBOk = new JButton("Ok");
        jBOk.addActionListener(this);
        lIcon.setBounds(10,15,300,300);
        jBOk.setBounds(dialogSize.width-75,290,60,25);
        contentPane.add(jBOk);
        contentPane.add(lIcon);
    }
    /**
     * Public interface method <code>ActionListener</code>
     * that handles the action event <code>ActionEvent</code>.
     */
    public void actionPerformed(ActionEvent eAE) {
        if(eAE.getSource() == jBOk) {
            setVisible(false);
        }
    }
}

