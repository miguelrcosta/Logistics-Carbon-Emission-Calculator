package interfaces;

import java.io.IOException;

public interface IReport {
    /**
     * Apresenta o relatório de emissões na consola.
     */
    void printReportToConsole();

    /**
     * Gera um ficheiro de relatório com os resultados das emissões.
     *
     * @param filePath O caminho do ficheiro onde o relatório será salvo.
     * @throws IOException Se ocorrer um erro ao escrever o ficheiro.
     */
    void generateReportFile(String filePath) throws IOException;
}
