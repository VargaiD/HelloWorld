/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pv254.parsing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classs that loads information about games from file
 * 
 * @author Šimon Baláž
 */
public class Load {
    
    /**
     * Loads games from csv file in given file path
     * @param path file path
     * @return list of game ids
     * @throws IOException 
     */
    public static List<Long> loadGames(String path) throws IOException {
        List<Long> games = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
             String line;
             
             while ((line = br.readLine()) != null) {                
                String[] game = line.split(",");               
                games.add(Long.valueOf(game[0].trim()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return games;
    }
    
//    /**
//     * Loads games from games.csv located in resources of this project
//     * @return list of game ids
//     * @throws IOException
//     */
//    public static List<Long> loadGames() throws IOException {
//        String fileName = "games.csv";
//        ClassLoader classLoader = new Load().getClass().getClassLoader();
//        File file = new File(classLoader.getResource(fileName).getFile());
//
//        List<Long> games = new ArrayList<>();
//
//        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
//             String line;
//
//             while ((line = br.readLine()) != null) {
//                String[] game = line.split(",");
//                games.add(Long.valueOf(game[0].trim()));
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return games;
//    }
}
