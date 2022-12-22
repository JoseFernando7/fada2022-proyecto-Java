package proyecto;

import javax.naming.OperationNotSupportedException;
import lombok.Getter;
import lombok.Setter;
import java.util.Arrays;


import java.io.FileNotFoundException;


public class SparseMatrixCoordinateFormat {

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

        // Inicializar arreglos para almacenar la representación de la matriz dispersa
        int n = matrix.length;
        int m = matrix[0].length;
        int size = 0;

        // Contar el número de elementos no nulos en la matriz
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (matrix[i][j] != 0) {
                    size++;
                }
            }
        }

        // Inicializar arreglos con el tamaño correcto
        rows = new int[size];
        columns = new int[size];
        values = new int[size];

        // Llenar los arreglos con la representación de la matriz dispersa
        int k = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (matrix[i][j] != 0) {
                    rows[k] = i;
                    columns[k] = j;
                    values[k] = matrix[i][j];
                    k++;
                }
            }
        }

    }



    public int getElement(int i, int j) throws OperationNotSupportedException
    {

        //No usar this.matrix aqui.

        // Recorre los elementos de la matriz
        for (int k = 0; k < rows.length; k++) {
            // Verifica si el elemento en la posición k es el elemento buscado
            if (rows[k] == i && columns[k] == j) {
                // Devuelve el valor del elemento encontrado
                return values[k];
            }
        }

        // Si no se encuentra el elemento, se devuelve 0
        return 0;

    }

    public int[] getRow(int i) throws OperationNotSupportedException
    {
        //No usar this.matrix aqui.

        // Crea un arreglo resultado con el tamaño de la fila i-ésima
        int m = matrix[0].length;
        int[] result = new int[m];

        // Inicializa el arreglo con ceros
        Arrays.fill(result, 0);

        // Recorre los elementos de la matriz
        for (int k = 0; k < rows.length; k++) {
            // Verifica si el elemento en la posición k es de la fila i-ésima
            if (rows[k] == i) {
                // Establece el valor del elemento en el arreglo resultado
                result[columns[k]] = values[k];
            }
        }

        // Devuelve el arreglo resultado
        return result;
    }

    public int[] getColumn(int j) throws OperationNotSupportedException
    {
        //No usar this.matrix aqui.

        // Crea un arreglo resultado con el tamaño de la columna j-ésima
        int[] column = new int[matrix.length];

        // Inicializa el arreglo con ceros
        Arrays.fill(column, 0);

        // Recorre los elementos de la matriz
        for (int i = 0; i < columns.length; i++) {
            // Verifica si el elemento en la posición i es de la columna j-ésima
            if (columns[i] == j) {
                // Establece el valor del elemento en el arreglo resultado
                column[rows[i]] = values[i];
            }
        }

        // Establece el valor del elemento en el arreglo resultado
        return column;
    }

    public void setValue(int i, int j, int value) throws OperationNotSupportedException
    {
        //Cambiar los atributos rows, cols, values y matrix aqui
        throw new OperationNotSupportedException();
    }

    /*
    * This method returns a representation of the Squared matrix
    * @return object that contests the squared matrix;
     */
    public SparseMatrixCoordinateFormat getSquareMatrix() throws OperationNotSupportedException
    {

        SparseMatrixCoordinateFormat squaredMatrix = new SparseMatrixCoordinateFormat();

        // Elevar al cuadrado cada elemento de la matriz actual
        for (int i = 0; i < this.rows.length; i++) {
            this.values[i] = (int) Math.pow(this.values[i], 2);
        }

        // Establecer los arreglos de filas, columnas y valores en la nueva matriz
        squaredMatrix.setRows(this.rows);
        squaredMatrix.setColumns(this.columns);
        squaredMatrix.setValues(this.values);


        //devuelve la matriz cuadrada resultante.
        return squaredMatrix;
    }

    /*
     * This method returns a representation of the transposed matrix
     * @return object that contests the transposed matrix;
     */
    public SparseMatrixCoordinateFormat getTransposedMatrix() throws OperationNotSupportedException
    {
        SparseMatrixCoordinateFormat squaredMatrix = new SparseMatrixCoordinateFormat();
        //Usar los metodos Set aqui de los atributos
        throw new OperationNotSupportedException();
    }

}
