package app;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
/**
 * Program <code>app.MyWindow</code>
 * The class <code>app.AboutWindow</code> defining the window
 * with information about the author.
 * @author Anastasiya Prakapovich
 * @version 1.0	1/05/2020
 */
public class AboutWindow extends JDialog implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JLabel l0, l1, l2, l3, l4, l5;
    private JLabel lBorder, lIcon;
    private JButton jBOk;
    private Font font1 = new Font("TimesRoman", Font.PLAIN, 22);
    private Font font2 = new Font("TimesRoman", Font.PLAIN, 12);
    private Font font3 = new Font("TimesRoman", Font.BOLD, 12);
    private Font font4 = new Font("TimesRoman", Font.PLAIN, 12);
    private Border line = null;

    /**
     * Constructor without parameters app.AboutWindow.
     */
    public AboutWindow() {
        this.setTitle("Informacje o programie");
        this.setModal(true);
        this.setSize(360,240);
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
                    getClass().getResource("/grafika/author_logo.jpg")));
        }
        catch(Exception e) {
            lIcon = new JLabel();
        }
        l0 = new JLabel("Aplikacja GUI");
        l0.setFont(font1);
        l0.setHorizontalAlignment(SwingConstants.CENTER);
        l1 = new JLabel("wersja 1.0");
        l1.setFont(font1);
        l1.setHorizontalAlignment(SwingConstants.CENTER);
        l2 = new JLabel("Copyright (C) by 2020");
        l2.setFont(font2);
        l2.setHorizontalAlignment(SwingConstants.CENTER);
        l3 = new JLabel("Anastasiya Prakapovich");
        l3.setFont(font3);
        l3.setHorizontalAlignment(SwingConstants.CENTER);
        l4 = new JLabel("Politechnika Koszalinska - WEiI");
        l4.setFont(font3);
        l4.setHorizontalAlignment(SwingConstants.CENTER);
        l5 = new JLabel("e-mail: nastya.prkopovich@mail.ru");
        l5.setFont(font4);
        lBorder = new JLabel("");
        jBOk = new JButton("Ok");
        jBOk.addActionListener(this);
        line = new EtchedBorder(EtchedBorder.LOWERED);

        lIcon.setBounds(10,15,120,150);
        l0.setBounds(135,20,210,30);
        l1.setBounds(135,50,210,30);
        l2.setBounds(135,100,210,20);
        l3.setBounds(135,120,210,20);
        l4.setBounds(135,140,210,20);
        lBorder.setBounds(5,175,dialogSize.width-14,2);
        l5.setBounds(10,184,200,20);
        jBOk.setBounds(dialogSize.width-75,182,60,25);

        lBorder.setBorder(line);
        contentPane.add(l0);
        contentPane.add(l1);
        contentPane.add(l2);
        contentPane.add(l3);
        contentPane.add(l4);
        contentPane.add(l5);
        contentPane.add(lBorder);
        contentPane.add(jBOk);
        contentPane.add(lIcon);
    }
    /**
     * A public interface method <code>ActionListener</code>
     * that handles the action event <code>ActionEvent</code> .
     */
    public void actionPerformed(ActionEvent eAE) {
        if(eAE.getSource() == jBOk) {
            setVisible(false);
        }
    }
}
