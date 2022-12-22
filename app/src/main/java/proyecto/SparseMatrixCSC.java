package proyecto;

import javax.naming.OperationNotSupportedException;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;


import java.io.FileNotFoundException;
import java.util.stream.IntStream;

public class SparseMatrixCSC {
    private final LoadFile loader = LoadFile.getInstance();
    @Setter
    private int[][] matrix;
    @Getter
    @Setter
    private int[] rows;
    @Getter
    @Setter
    private int[] columns;
    @Getter
    @Setter
    private int[] values;


    public void createRepresentation(String inputFile) throws OperationNotSupportedException, FileNotFoundException {
         // Load data
        loader.loadFile(inputFile);
        matrix = loader.getMatrix();

        // Intercambiar filas y columnas de la matriz original
        int rowCuantity = matrix[0].length;
        int columnCuantity = matrix.length;

        // Arreglo el cual almacena el número de elementos no nulos por cada columna
        int[] notZeroElements = new int[rowCuantity];
        for (int i = 0; i < rowCuantity; i++) {
            for (int j = 0; j < columnCuantity; j++) {
                if (matrix[j][i] != 0) {
                    notZeroElements[i]++;
                }
            }
        }

        // Arreglos para almacenar la representación en formato de coordenadas dispersas
        int[] values = new int[IntStream.of(notZeroElements).sum()];
        int[] rows = new int[values.length];
        int[] columns = new int[rowCuantity + 1];
        System.out.println(Arrays.toString(values));

        // indice actual en values y rows
        int index = 0;

        for (int i = 0; i < rowCuantity; i++) {
            // Actualiza columns para indicar dónde comienza la columna actual
            columns[i] = index;
            for (int j = 0; j < columnCuantity; j++) {
                if (matrix[j][i] != 0) {
                    values[index] = matrix[j][i];
                    rows[index] = j;
                    index++;
                }
            }
        }

        // Asigna los valores de los arreglos a los atributos de la clase
        columns[rowCuantity] = index;
        this.columns = columns;
        this.rows = rows;
        this.values = values;
    }

    public int getElement(int i, int j) throws OperationNotSupportedException
    {
        // Valor por defecto para elementos nulos de la matriz
        int defaultValue = 0;

        // Recorre los elementos de la columna j-ésima
        for (int index = columns[j]; index < columns[j + 1]; index++) {
            // Si el índice de fila coincide, devuelve el valor del elemento
            if (rows[index] == i) {
                return values[index];
            }
        }

        // Si no hay elemento en la posición i, j, devuelve el valor por defecto
        return defaultValue;
    }

    public int[] getRow(int i) throws OperationNotSupportedException
    {
        // Crea un arreglo para almacenar los valores de la fila
        int[] row = new int[matrix[0].length];

        // Valor por defecto para elementos nulos de la matriz cuadrada
        int defaultValue = 0;

        // Inicializa el arreglo con el valor por defecto
        Arrays.fill(row, defaultValue);

        // Recorre todas las columnas de la matriz
        for (int columna = 0; columna < matrix[0].length; columna++) {
            // Verifica si hay un elemento en la fila i-ésima y columna col
            int index = -1;
            for (int j = columns[columna]; j < columns[columna + 1]; j++) {
                if (rows[j] == i) {
                    index = j;
                    break;
                }
            }
            if (index >= 0) {
                // Asigna el valor del elemento a la posición correspondiente en el arreglo
                row[columna] = values[index];
            }
        }
        // Devuelve el arreglo row
        return row;
    }

    public int[] getColumn(int j) throws OperationNotSupportedException
    {
        // Crea un arreglo para almacenar los valores de la columna
        int[] columna = new int[matrix.length];

        // Verifica si hay elementos en la columna j-ésima
        if (columns[j] != columns[j + 1]) {
            // Recorre los elementos de la columna
            for (int row = columns[j]; row < columns[j + 1]; row++) {
                // Asigna el valor del elemento a la posición correspondiente en el arreglo
                columna[rows[row]] = values[row];
            }
        }

        // Devuelve el arreglo columna
        return columna;
    }

    public void setValue(int i, int j, int value) throws OperationNotSupportedException
    {
        throw new OperationNotSupportedException();
    }

    /*
     * This method returns a representation of the Squared matrix
     * @return object that contests the squared matrix;
     */
    public SparseMatrixCSC getSquareMatrix() throws OperationNotSupportedException
    {

        // Valor por defecto para elementos nulos de la matriz cuadrada
        int defaultValue = 0;

        // Crea una nueva matriz cuadrada en formato CSC
        SparseMatrixCSC squaredMatrix = new SparseMatrixCSC();

        // Eleva al cuadrado los valores de la matriz dispersa
        for (int i = 0; i < this.values.length; i++) {
            this.values[i] = (int) Math.pow(this.values[i], 2);
        }

        // Asigna los valores y índices de fila y columna a la matriz cuadrada en formato CSC
        squaredMatrix.setRows(this.rows);
        squaredMatrix.setColumns(this.columns);
        squaredMatrix.setValues(this.values);

        // Devuelve la matriz cuadrada en formato CSC
        return squaredMatrix;
    }

    /*
     * This method returns a representation of the transposed matrix
     * @return object that contests the transposed matrix;
     */
    public SparseMatrixCSC getTransposedMatrix() throws OperationNotSupportedException
    {
        SparseMatrixCSC squaredMatrix = new SparseMatrixCSC();
        throw new OperationNotSupportedException();
    }
}
