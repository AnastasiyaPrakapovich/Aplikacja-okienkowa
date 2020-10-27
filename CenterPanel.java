package app;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;
import org.freixas.jcalendar.JCalendarCombo;

/**
 * Program <code>app.MyWindow</code>.
 * The class <code>app.CenterPanel</code> defining the central panel
 * application containing the main functionality of the application.
 * @author Anastasiya Prakapovich
 * @version 1.0	01/05/2020
 */
public class CenterPanel extends JPanel implements ActionListener{

    private static final long serialVersionUID = 1L;
    private JPanel northPanel, southPanel,pan,bb,dane;
    /**
     * Variable that creates Text Field.
     */
    JTextField paramTextField;
    /**
     * Variable that creates Text Area.
     */
    JTextArea resultTextArea;
    /**
     * Variable that creates Slider.
     */
    JSlider slider,slider1;
    /**
     * Variable that creates Table Model.
     */
    IntegerTableModel tableModel;
    private JLabel paramLabel,paramLabel1,paramLabel2,paramLabel3;
    private JButton submitButton,submitButton1,submitButton2,submitButton3,submitButton4;
    private JButton submitButton5;
   // deklaracja zmiennej typu JCalendarCombo o nazwie jccData
    private JCalendarCombo jccData;
    private TitledBorder titledBorder;
    private Border blackLine;
    private JTable table;
    private JList list;
    private String[] list1 = new String[] {"a.Suma elementów","b. Średnia elementów ","c. Wartość max i min "};
    private ListModelTest model;
    private Logger logger;
    /**
     * Constructor without parameters app.CenterPanel.
     */
    public CenterPanel() {
        logger = Logger.getLogger(this.getClass().getName());
        createGUI();
    }
    /**
     * The method that creates a graphical user interface.
     */
    public void createGUI() {
        this.setLayout(new GridLayout(2,1,5,5));

        // Utworzenie panelu z paramtrami i wynikiem
        northPanel = createNorthPanel();
        southPanel = createSouthPanel();
        // Utworzenie obiektow TextField
        this.add(northPanel);
        this.add(southPanel);
    }
    /**
     * The method creates a panel with parameters.
     * @return JPanel.
     */
    public JPanel createNorthPanel() {
        JPanel jp = new JPanel(new BorderLayout(10,10));
        blackLine = BorderFactory.createLineBorder(Color.gray);
        titledBorder = BorderFactory.createTitledBorder(blackLine,
               "Parametry wejsciowe");
        titledBorder.setTitleJustification(TitledBorder.CENTER);
        jp.setBorder(titledBorder);
        tableModel=new IntegerTableModel();
        table=new JTable(tableModel);
        table.getTableHeader().setReorderingAllowed(false);
        table.setBackground(Color.cyan);
        table.setForeground(Color.red);
        table.setSelectionBackground(Color.white);

        model=new ListModelTest(list1);
        list = new JList(model);
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);

        pan=North();
        bb=But();
        dane=Dane();
        jp.add(pan,BorderLayout.NORTH);
        jp.add(new JScrollPane(table.getTableHeader()),BorderLayout.CENTER);
        jp.add(new JScrollPane(table),BorderLayout.CENTER);
        jp.add(bb,BorderLayout.EAST);
        jp.add(dane,BorderLayout.SOUTH);
        return jp;
    }
    /**
     * The method that creates variables for north location.
     */
    private JPanel North(){
        paramLabel = new JLabel("Wprowadz liczbe");
        paramTextField = new JTextField(10);

        paramLabel1 = new JLabel("Numier wiersza");
        slider = new JSlider(0,4);
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(1);

        paramLabel2 = new JLabel("Numier kolumny");
        slider1 = new JSlider(0, 4);
        slider1.setPaintLabels(true);
        slider1.setMajorTickSpacing(1);

        JPanel jp = new JPanel();
        jp.setLayout(new FlowLayout());
        jp.add(paramLabel);
        jp.add(paramTextField);
        jp.add(paramLabel1);
        jp.add(slider);
        jp.add(paramLabel2);
        jp.add(slider1);
        return jp;
    }
    /**
     * The method that creates variables for east location.
     */
    private JPanel But(){
        submitButton1 = new JButton("Dodaj",new ImageIcon(this.getClass().getResource("/grafika/dodaj.jpg")));
        submitButton1.addActionListener(this);
        submitButton2 = new JButton("Wyzeruj",new ImageIcon(this.getClass().getResource("/grafika/wyzeruj.jpg")));
        submitButton2.addActionListener(this);
        submitButton3 = new JButton("Wypelnij",new ImageIcon(this.getClass().getResource("/grafika/wypelnij.jpg")));
        submitButton3.addActionListener(this);
        submitButton4 = new JButton("Zapisz",new ImageIcon(this.getClass().getResource("/grafika/zapis.jpg")));
        submitButton4.addActionListener(this);
        JPanel b = new JPanel();
        b.setLayout(new GridLayout(4,1));
        b.add(submitButton1);
        b.add(submitButton2);
        b.add(submitButton3);
        b.add(submitButton4);
        return b;
    }
    /**
     * The method that creates variables for south location.
     */
    private JPanel Dane(){
        paramLabel3 = new JLabel("Obliczenia");
        JPanel l = new JPanel();
        l.setLayout(new FlowLayout());
        l.add(paramLabel3);
        l.add(list);
        submitButton5 = new JButton("Oblicz",new ImageIcon(this.getClass().getResource("/grafika/oblicz.jpg")));
        submitButton5.addActionListener(this);
        l.add(submitButton5);

        // utworzenie instacji obiektu JCalendar
        jccData = new JCalendarCombo(
                Calendar.getInstance(),
                Locale.getDefault(),
                JCalendarCombo.DISPLAY_DATE,
                false
        );
        // ustawienie formatu daty
        jccData.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        l.add(jccData);
        l.add(submitButton);
      return l;
    }
    /**
     * The method that creates the results panel.
     * @return JPanel.
     */
    public JPanel createSouthPanel() {
        JPanel jp = new JPanel();
        blackLine = BorderFactory.createLineBorder(Color.gray);
        titledBorder = BorderFactory.createTitledBorder(blackLine,
                "Uzyskany rezultat");
        titledBorder.setTitleJustification(TitledBorder.CENTER);
        jp.setBorder(titledBorder);
        jp.setLayout(new BorderLayout());

        resultTextArea = new JTextArea();
        // zawijanie wierszy
        resultTextArea.setLineWrap(true);
        jp.add(new JScrollPane(resultTextArea),BorderLayout.CENTER);
        return jp;
    }
    /**
     * The method determines the value of the spacing from the panel edge
     * (top,left,bottom,right).
     */
    public Insets getInsets() {
        return new Insets(5,10,10,10);
    }

    /**
     * Public interface method <code>ActionListener</code>
     *that handles the action event <code>ActionEvent</code>.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton1) {
            try {
                int value = Integer.parseInt(paramTextField.getText());
                int row = (int) slider.getValue();
                int col = (int) slider1.getValue();
                tableModel.setValue(value, row, col);
                resultTextArea.append(" Value-" + value + "\n");
            } catch (NumberFormatException ex) {
                logger.error("Blad dodawania licby do tabeli");
                JOptionPane.showMessageDialog(this,"Blad dodawania licby do tabeli ","Uwaga",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == submitButton2) {
            tableModel.setZeroTable();
        } else if (e.getSource() == submitButton3) {
                tableModel.setRandomTable();
        }
        else if(e.getSource()==submitButton4){
            try {
                tableModel.WriteToTheFile();
            }
            catch(IOException ex){
                logger.error("Blad zapisu do pliku");
                JOptionPane.showMessageDialog(this,"Blad zapisu do pliku ","Uwaga",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        else if(e.getSource()==submitButton5){
            int index=list.getSelectedIndex();
            if(index==0){
                resultTextArea.append("Sum:="+ tableModel.calculateSum()+"\n");
            }
            if(index==1){
                resultTextArea.append(" Avg:="+tableModel.calculateAverage()+"\n");
            }
            if(index==2){
                resultTextArea.append(" Max:="+tableModel.max()+"\n");
                resultTextArea.append(" Min:="+tableModel.min()+"\n");
            }
        }
      else if(e.getSource() == submitButton) {
            // Pobranie daty do obiektu typu String
            // Miesiace liczone sa od 0 wiec trzeba dodac 1
            Calendar cal = jccData.getCalendar();
            String data = ""+cal.get(Calendar.YEAR)+"-";
            int miesiac = cal.get(Calendar.MONTH)+1;
            if(miesiac <= 9) data = data+"0"+String.valueOf(miesiac)+"-";
            else data = data+String.valueOf(miesiac)+"-";
            int dzien = cal.get(Calendar.DAY_OF_MONTH);
            if(dzien <= 9) data = data+"0"+String.valueOf(dzien);
            else data = data+String.valueOf(dzien);
            // zapisanie danych w polu TextArea
            resultTextArea.append("Data: "+data+"\n");
        }
    }
}
