
public class Sudoku_Ver2 {
    /** Löst ein Sudoku
     *
     * @param sudoku ein Sudoku. Der Wert 0 bedeutet "leeres Feld".
     * @return das ausgefüllte Sudoku
     */
    static int[][] solveSudoku(int[][] sudoku) {
        int[][] sudo_solved = new int[sudoku.length][sudoku.length];

        boolean numberFound = true;
        int numbersMissing = 0;

        Integer numTemp;
        for (int i = 0; i < sudoku.length; i++) {
            for (int j = 0; j < sudoku.length; j++) {
                sudo_solved[i][j] = sudoku[i][j];
                if (sudoku[i][j] == 0) numbersMissing++;
            }
        }
        System.out.println("Fehlende Zahlen: " + numbersMissing);

        // Brich ab, wenn das Sudoku ausgefüllt ist; oder wenn keine
        // weitere Zahl mehr bestimmt werden konnte.
        while (numberFound && numbersMissing > 0) {
            numberFound = false;

            for (int i = 0; i < sudoku.length; i++) {
                for (int j = 0; j < sudoku.length; j++) {
                    if (sudoku[i][j] == 0) {

                        // Suche nach Feldern, wo die Zahl eindeutig ist
                        numTemp = getUniqueNumberFor(sudo_solved, i,j);	// wie geht das?
                        if (numTemp != null) {
                            sudo_solved[i][j] = numTemp;
                            // Umwandlung Integer -> int geht automatisch
                            numbersMissing--;
                            numberFound = true;
                            System.out.println(
                                    String.format("Zahl gefunden: Spalte %d, Zeile %d ist %d",
                                            j, i, numTemp));
                        }

                        // Gehe die Zeilen und Spalten durch, ob es für eine Zahl nur ein
                        // Feld gibt, wo sie hin könnte


                    } // Ende IF (Feld ist leer)
                } // Ende FOR (j / Spalte)
            }// Ende FOR (i / Zeile)
        } // Ende WHILE (Sudoku noch nicht gelöst und Zahl gefunden)
        return sudo_solved;
    }


    private static Integer getUniquePositionInColumn(int[][] sudoku, int row, int number) {
        // TODO Auto-generated method stub
        return null;
    }

    private static Integer getUniquePositionInRow(int[][] sudoku, int column, int number) {
        // TODO Auto-generated method stub
        return null;
    }

    private static Integer getUniqueNumberFor(int[][] sudoku, int row, int column) {
        Integer unique = null;
        for (int number = 1; number <= 9; number++) {
            if (!isInRow(sudoku, row, number)
                    && !isInColumn(sudoku, column, number)
                    && !isInBlock(sudoku, row, column, number)) {
                if (unique == null) unique = number;
                else {
                    // findet eine zweite Zahl, die passt = kein eindeutiges Ergebnis
                    unique = null;
                    break;
                }
            }
        }
        return unique;
    }

    /** Prüft, ob eine Zahl bzw. ein Symbol in einer Zeile / Spalte bereits vorkommt
     *
     * @param row Die Nummer der Zeile
     * @param value der gesuchte Wert
     */
    static boolean isInRow(int[][] sudo, int row, int value) {
        boolean found = false;
        for (int i = 0; i < sudo.length; i++) {
            if (sudo[row][i] == value) {
                found = true;
                break;
            }
        }
        return found;
    }

    static boolean isInColumn(int[][] sudo, int column, int value) {
        boolean found = false;
        for (int i = 0; i < sudo.length; i++) {
            if (sudo[i][column] == value) {
                found = true;
                break;
            }
        }
        return found;
    }

    /** Prüft, ob ein Symbol in dem gleichen 3x3-Block bereits vorkommt.
     *
     * @param sudo das zu prüfende Sudoku
     * @param row die Zeile des Feldes, für das geprüft werden soll
     * @param column die Spalte des Feldes, für das geprüft werden soll
     * @param value der gesuchte Wert
     * @return
     */
    static boolean isInBlock(int[][] sudo, int row, int column, int value) {
        boolean found = false;
        // erste Zeile / Spalte dieses 3x3-Blocks
        int startRow = row - (row % 3), startCol = column - (column % 3);
        for (int r = startRow; r <= startRow + 2; r++) {
            for (int c = startCol; c <= startCol + 2; c++) {
                if (sudo[r][c] == value) found = true;
            }
        }
        return found;
    }

    /** gibt ein Sudoku in der Konsole aus */
    static void print(int[][] sudoku) {
        int rowWidth = 3;
        String strHoriLine, strCross, strCrossThick, strStart, strEnd;

        StringBuilder buffer = new StringBuilder();
        buffer.append("┏");
        for (int i = 1; i <= sudoku.length - 1; i++) {
            buffer.append("━".repeat(rowWidth) + ( i % 3 == 0 ? "┳" : "┯"));
        }
        buffer.append("━".repeat(rowWidth) + "┓\n");

        for (int row = 0; row < sudoku.length; row++) {
            // Reihe mit den Ziffern ausgeben
            buffer.append("┃");
            for (int column = 0; column < sudoku.length; column++) {
                buffer.append(" " + (sudoku[row][column] == 0 ? " " : sudoku[row][column]) + " ");
                buffer.append( ((column + 1) % 3 == 0 ? "┃" : "│"));
            }
            buffer.append("\n");

            if (row < sudoku.length - 1) {
                // Horizontaler Trennstrich
                if ((row + 1) % 3 == 0) {
                    // Block-Trennlinie (dick)
                    strHoriLine = "━";
                    strCross = "┿";
                    strCrossThick = "╋";
                    strStart = "┣";
                    strEnd = "┫";
                } else {
                    // normale Trennlinie
                    strHoriLine = "─";
                    strCross = "┼";
                    strCrossThick = "╂";
                    strStart = "┠";
                    strEnd = "┨";
                }

                buffer.append(strStart);
                for (int i = 1; i <= sudoku.length - 1; i++) {
                    buffer.append(strHoriLine.repeat(rowWidth) + ( i % 3 == 0 ? strCrossThick : strCross));
                }
                buffer.append(strHoriLine.repeat(rowWidth) + strEnd + "\n");
            }
        }
        // Unterer Rand:
        buffer.append("┗");
        for (int i = 1; i <= sudoku.length - 1; i++) {
            buffer.append("━".repeat(rowWidth) + ( i % 3 == 0 ? "┻" : "┷"));
        }
        buffer.append("━".repeat(rowWidth) + "┛\n");

        System.out.println(buffer);
    }

    /** Gibt beide Sudoku's (Anfangszustand und Lösung) nebeneinander in der Konsole aus */
    static void print(int[][] sudoku, int[][] solvedSudoku) {
        int rowWidth = 3;
        int spacing = 7;
        String strHoriLine, strCross, strCrossThick, strStart, strEnd;

        StringBuilder buffer = new StringBuilder();

        // Obere Randlinie für beide Sudoku's:
        buffer.append("┏");
        for (int i = 1; i <= sudoku.length - 1; i++) {
            buffer.append("━".repeat(rowWidth) + ( i % 3 == 0 ? "┳" : "┯"));
        }
        buffer.append("━".repeat(rowWidth) + "┓");
        buffer.append(" ".repeat(spacing));
        buffer.append("┏");
        for (int i = 1; i <= sudoku.length - 1; i++) {
            buffer.append("━".repeat(rowWidth) + ( i % 3 == 0 ? "┳" : "┯"));
        }
        buffer.append("━".repeat(rowWidth) + "┓\n");

        for (int row = 0; row < sudoku.length; row++) {
            // Reihe mit den Ziffern ausgeben
            buffer.append("┃");
            for (int column = 0; column < sudoku.length; column++) {
                buffer.append(" " + (sudoku[row][column] == 0 ? " " : sudoku[row][column]) + " ");
                buffer.append( ((column + 1) % 3 == 0 ? "┃" : "│"));
            }

            buffer.append(" ".repeat(spacing));

            buffer.append("┃");
            for (int column = 0; column < solvedSudoku.length; column++) {
                buffer.append(" " + (solvedSudoku[row][column] == 0 ? " " : solvedSudoku[row][column]) + " ");
                buffer.append( ((column + 1) % 3 == 0 ? "┃" : "│"));
            }
            buffer.append("\n");

            if (row < sudoku.length - 1) {
                // Horizontalen Trennstrich zeichnen
                if ((row + 1) % 3 == 0) {
                    // Block-Trennlinie (dick)
                    strHoriLine = "━";
                    strCross = "┿";
                    strCrossThick = "╋";
                    strStart = "┣";
                    strEnd = "┫";
                } else {
                    // normale Trennlinie
                    strHoriLine = "─";
                    strCross = "┼";
                    strCrossThick = "╂";
                    strStart = "┠";
                    strEnd = "┨";
                }

                buffer.append(strStart);
                for (int i = 1; i <= sudoku.length - 1; i++) {
                    buffer.append(strHoriLine.repeat(rowWidth) + ( i % 3 == 0 ? strCrossThick : strCross));
                }
                buffer.append(strHoriLine.repeat(rowWidth) + strEnd);
                buffer.append(" ".repeat(spacing));
                buffer.append(strStart);
                for (int i = 1; i <= sudoku.length - 1; i++) {
                    buffer.append(strHoriLine.repeat(rowWidth) + ( i % 3 == 0 ? strCrossThick : strCross));
                }
                buffer.append(strHoriLine.repeat(rowWidth) + strEnd + "\n");
            } // END IF (nach jeder dritten Zeile einen breiteren Trennstrich)
        } // Ende FOR (durchläuft die Zeilen)

        // Unterer Rand:
        buffer.append("┗");
        for (int i = 1; i <= sudoku.length - 1; i++) {
            buffer.append("━".repeat(rowWidth) + ( i % 3 == 0 ? "┻" : "┷"));
        }
        buffer.append("━".repeat(rowWidth) + "┛");
        buffer.append(" ".repeat(spacing));
        buffer.append("┗");
        for (int i = 1; i <= sudoku.length - 1; i++) {
            buffer.append("━".repeat(rowWidth) + ( i % 3 == 0 ? "┻" : "┷"));
        }
        buffer.append("━".repeat(rowWidth) + "┛\n");
        System.out.println(buffer);
    }

    public static void main(String[] args) {
        int[][] sudokuLeicht = {
                {5,2,0,7,1,8,0,3,0},
                {0,3,0,0,0,0,0,0,0},
                {6,0,0,3,0,5,0,2,8},
                {0,0,0,0,3,9,0,6,4},
                {0,0,3,0,8,0,0,0,0},
                {8,6,5,0,7,0,9,1,0},
                {0,0,1,0,0,3,0,5,2},
                {3,5,0,0,9,0,0,8,0},
                {0,0,6,2,0,7,0,9,1}
        };

        int[][] sudokuSchwierig = {
                {0,0,1,0,0,9,0,0,5},
                {7,0,9,0,0,0,0,0,1},
                {0,0,0,0,5,6,4,0,0},
                {1,2,0,5,0,0,0,4,0},
                {5,0,0,0,8,0,3,0,0},
                {0,0,0,0,0,0,0,2,7},
                {8,0,0,0,0,0,0,0,0},
                {6,0,7,0,0,1,0,0,0},
                {0,0,0,0,0,4,0,0,0}
        };
        int[][] solved = solveSudoku(sudokuSchwierig);
        print(sudokuSchwierig, solved);
    }

}
