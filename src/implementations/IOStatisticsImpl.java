/*
 * Nome: Rui Alexandre Borba Vitorino
 * Número: 8190479
 * Turma: LSIRC12T1
 *
 * Nome: João Pedro Silva Oliveira
 * Número: 8210291
 * Turma: LSIRC11T2
 */

package implementations;

import estg.ipp.pt.tp02_conferencesystem.dashboards.Dashboard;
import interfaces.IOStatistics;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/** Class responsible for the the IOStatistics */
public class IOStatisticsImpl implements IOStatistics {

    /**
     * Show the Dashboard given the JSON files parsed as the arguments (CLI)
     * @param args - JSON files with data of the conference (generated with export() method)
     * @throws IOException
     * When there were no files indicated
     * When there was an error reading a file
     * When the program does not have permissions to read a file
     */
    @Override
    public void showDashboard(String[] args) throws IOException {
        if ( args.length == 0 ) throw new IOException("There were no files specified.");
        
        String[] jsonFiles = new String[args.length];
        
        int nFiles = args.length;
        
        try {
            for ( int x = 0; x < nFiles; x++ ) {
                File file = new File(args[x]);
                if (! ( file.exists() )) {
                    throw new IOException("The file " + args[x] + " does not exist");
                } else if (! ( file.canRead() )) {
                    throw new IOException("Don't have permissions to read the file " + args[x]);    
                }
            }
        } catch (IOException ex) {
            throw new IOException(ex.getMessage());
        }
        
        for ( int file = 0; file < nFiles; file++ ) {
            try {
                FileReader jsonFile = new FileReader(args[file]);
                BufferedReader br = new BufferedReader(jsonFile);
                String s;
                while((s = br.readLine()) != null) {
                    System.out.println(s);
                    jsonFiles[file] = s;
                }
                jsonFile.close();
            } catch (IOException e) {
                throw new IOException(e.getMessage());
            }
        }
        
        Dashboard.render(jsonFiles);
        
    }
    
    /**
     * Main Class that receives the files in the CLI
     * @param args the JSON files
     */
    public static void main(String[] args) {
        IOStatistics ios = new IOStatisticsImpl();
        
        System.out.println("### Conference Report ###");
        try {
            ios.showDashboard(args);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
