package app;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import javax.swing.table.AbstractTableModel;

/**
 * Program <code>app.MyWindow</code>
 * The class <code>app.IntegerTableModel</code> describes
 * a table creation method.
 * @author Anastasiya Prakapovich
 * @version 1.0	1/05/2020
 */
public class IntegerTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 1L;
    private final int countRowTable = 5;
    private final int countColumnTable = 5;
    private Integer[][] data = new Integer[countRowTable][countColumnTable];
    private String[] colName = {"1","2","3","4","5"};

    /**
     * Constructor without parameters.
     */
    public IntegerTableModel() {
        super();
        setZeroTable();
    }

    /**
     * Method returns column length.
     * @return int column length.
     */
    public int getColumnCount() {
        return countColumnTable;
    }
    /**
     * Method returns row length.
     * @return int row length.
     */
    public int getRowCount() {
        return countColumnTable;
    }

    /**
     * Method returns value from given position.
     * @param row item position in row.
     * @param col item position in col.
     * @return value from table.
     */
    public Object getValueAt(int row, int col) {
        Object object = (Object) data[row][col];
        return object;
    }
    /*public Integer[][] getIntegerValuesTable() {
        return data;
    }
    public String getStringValuesTable() {
        String str = "";
        for(int i=0; i<countRowTable; i++)
            for(int j=0; j<countColumnTable; j++) {
                str = str + data[i][j] +" ";
            }
        return str;
    }*/

    /**
     * Method returns name of the column.
     * @param col column number.
     * @return name of the column.
     */
    public String getColumnName(int col) {
        return colName[col];
    }
    /*public String[] getColumnNames() {
        return colName;
    }*/

    /**
     *  Method sets value in given position.
     * @param value entered value.
     * @param row item position in row.
     * @param col item position in col.
     */
    public void setValue(Integer value, int row, int col) {
        data[row][col] = value;
        fireTableDataChanged();
    }
    /**
     * Method fills table with random values.
     */
    public void setRandomTable() {
        Random random = new Random();
        for(int i=0; i<countRowTable; i++)
            for(int j=0; j<countColumnTable; j++) {
                // ograniczenie znaku liczby i zakresu do 10000
                data[i][j] = Math.abs(random.nextInt()) % 10000;
            }
        fireTableDataChanged();
    }
    /**
     * Method fills table with zero.
     */
    public void setZeroTable() {
        for(int i=0; i<countRowTable; i++)
            for(int j=0; j<countColumnTable; j++) {
                data[i][j] = new Integer(0);
            }
        fireTableDataChanged();
    }

    /**
     * Method calculates sum of the values.
     * @return int sum.
     */
    public Integer calculateSum() {
        Integer sum = new Integer(0);
        for(int i=0; i<countRowTable; i++)
            for(int j=0; j<countColumnTable; j++) {
                sum = sum + data[i][j];
            }
        return sum;
    }
    /**
     * Method calculates avg of the values.
     * @return float avg.
     */
    public Float calculateAverage() {
        Float avg = new Float(0.0);
        Integer sum = calculateSum();
        if(sum > 0) avg = (sum.floatValue())/(countRowTable*countColumnTable);
        return avg;
    }

    /**
     * Method finds max value in the table.
     * @return max value.
     */
    public Integer  max(){
        int max=data[0][0];
        for(int i=0; i<countRowTable; i++)
            for(int j=0; j<countColumnTable; j++) {
                if(data[i][j]>max){
                    max=data[i][j];
                }
            }
        return max;
    }
    /**
     * Method finds min value in the table.
     * @return min value.
     */
    public Integer min(){
        int min=data[0][0];
        for(int i=0; i<countRowTable; i++)
            for(int j=0; j<countColumnTable; j++) {
                if(data[i][j]<min){
                    min=data[i][j];
                }
            }
        return min;
    }
    /**
     * Method writes table to the file.
     * @throws IOException if there are some errors with IO.
     */
    public void WriteToTheFile() throws IOException {
        File file=new File("Dane.txt");
        FileWriter writer=new FileWriter(file,true);
        for(int i=0; i<countRowTable; i++){
            for(int j=0; j<countColumnTable; j++) {
                writer.write(data[i][j]+" ");
            }
        writer.write("\n");
        }
        writer.write("\n");
        writer.close();
    }
}

