package interfaces;

import classes.TransportChain;

import java.io.IOException;
import java.nio.file.Path;

public interface IImporter {
    TransportChain importPrimaryCSV(Path path) throws IOException;

    void importSecondaryCSV(TransportChain chain, Path path) throws IOException;

}
