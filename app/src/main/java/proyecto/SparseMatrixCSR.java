package proyecto;

import javax.naming.OperationNotSupportedException;
import lombok.Getter;
import lombok.Setter;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.stream.IntStream;

public class SparseMatrixCSR {
    private LoadFile loader = LoadFile.getInstance();
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

        //Load data
        loader.loadFile(inputFile);
        matrix = loader.getMatrix();

        // Cantidad de columnas y filas de la matriz
        int columnCuantity = matrix[0].length;
        int rowCuantity = matrix.length;

        // Arreglo el cual almacena el numero de elementos no nulos por cada fila
        int[] notZeroElements = new int[rowCuantity];
        for (int i = 0; i < rowCuantity; i++) {
            for (int j = 0; j < columnCuantity; j++) {
                if (matrix[i][j] != 0) {
                    notZeroElements[i]++;
                }
            }
        }

        // Arreglos para almacenar la representación en formato de coordenadas dispersas
        int[] valores = new int[IntStream.of(notZeroElements).sum()];
        int[] columnas = new int[valores.length];
        int[] filas = new int[rowCuantity + 1];
        System.out.println(Arrays.toString(valores));

        // indice actual en valores y columnas
        int index = 0;

        for (int i = 0; i < rowCuantity; i++) {
            // Actualiza filas para indicar dónde comienza la fila actual
            filas[i] = index;
            for (int j = 0; j < columnCuantity; j++) {
                if (matrix[i][j] != 0) {
                    valores[index] = matrix[i][j];
                    columnas[index] = j;
                    index++;
                }
            }
        }

        // Asigna los valores de los arreglos a los atributos de la clase
        filas[rowCuantity] = index;
        this.rows = filas;
        this.columns = columnas;
        this.values = valores;
    }

    public int getElement(int i, int j) throws OperationNotSupportedException
    {
        // Indice del primer elemento de la fila i
        int firstElement = rows[i];

        // Indice del último elemento de la fila i
        int lastElement = rows[i + 1] - 1;

        // Si el índice de fila coincide, devuelve el valor del elemento
        for (int iterator = firstElement; iterator <= lastElement; iterator++)
        {
            if (columns[iterator] == j)
            {
                return values[iterator];
            }
        }

        // Si no hay elemento en la posición i, j, devuelve el valor por defecto
        return 0;
    }

    public int[] getRow(int i) throws OperationNotSupportedException {

        // Cantidad de columnas de la matriz
        int columnCuantity = 0;

        // Obtener la cantidad máxima de columnas de la matriz
        for (int column : columns) columnCuantity = Math.max(columnCuantity, column);

        // Fila actual
        int row = rows[i];

        // Arreglo final que se va a devolver
        int[] finalRow = new int[columnCuantity + 1];

        // Recorrer la matriz y asignar los valores de la fila actual al arreglo final
        for (int fila = row; fila < rows[i + 1]; fila++) {
            finalRow[columns[fila]] = values[fila];
        }

        return finalRow;
    }


    public int[] getColumn(int j) throws OperationNotSupportedException
    {

        // Calcula la cantidad de columnas de la matriz original
        int numColumns = rows.length - 1;
        // Crea el arreglo para almacenar la columna j
        int[] column = new int[numColumns];

        // Recorre cada fila de la matriz y obtiene el elemento de la columna j
        for (int i = 0; i < column.length; i++) {
            column[i] = getElement(i, j);
        }

        // Devuelve la columna j
        return column;
    }

    public void setValue(int i, int j, int value) throws OperationNotSupportedException
    {
        throw new OperationNotSupportedException();
    }

    /*
     * This method returns a representation of the Squared matrix
     * @return object that contests the squared matrix;
     */
    public SparseMatrixCSR getSquareMatrix() throws OperationNotSupportedException
    {

        // Crea la matriz resultado
        SparseMatrixCSR squaredMatrix = new SparseMatrixCSR();

        // Eleva al cuadrado cada elemento de la matriz original
        for (int i = 0; i < this.values.length; i++) {
            this.values[i] = (int) Math.pow(this.values[i], 2);
        }

        // Asigna los valores de los arreglos a la matriz resultado
        squaredMatrix.setRows(this.rows);
        squaredMatrix.setColumns(this.columns);
        squaredMatrix.setValues(this.values);

        // Devuelve la matriz resultado
        return squaredMatrix;
    }

    /*
     * This method returns a representation of the transposed matrix
     * @return object that contests the transposed matrix;
     */
    public SparseMatrixCSR getTransposedMatrix() throws OperationNotSupportedException
    {
        SparseMatrixCSR squaredMatrix = new SparseMatrixCSR();
        throw new OperationNotSupportedException();
    }

}
