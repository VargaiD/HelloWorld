/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pv254;

import cz.muni.fi.pv254.dao.GameDao;
import cz.muni.fi.pv254.dao.GenreDao;
import cz.muni.fi.pv254.dao.RecommendationDao;
import cz.muni.fi.pv254.entity.Game;
import cz.muni.fi.pv254.entity.Genre;
import cz.muni.fi.pv254.entity.Recommendation;
import cz.muni.fi.pv254.spring.PersistenceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Šimon Baláž
 */
@ContextConfiguration(classes = PersistenceConfig.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class GameDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private GameDao gameDao;

    @Autowired
    private GenreDao genreDao;

    private Game game1;
    private Game game2;
    
    @BeforeMethod
    public void init() {
        game1 = new Game();
        game1.setName("Stellaris");
        
        game2 = new Game();
        game2.setName("The Elder Scrolls V: Skyrim");
        game2.setSteamId(123L);

        Genre genre = new Genre("asd");
        genre = genreDao.add(genre);
        game2.addGenre(genre);
    }
    
//    @Test
//    public void testAddOneGame() {
//        //Assert.assertTrue(gameDao.findAll().isEmpty());
//        game2 = gameDao.add(game2);
//        Assert.assertTrue(gameDao.findAll().contains(game2));
//        Assert.assertTrue(true);
//    }
//
//    @Test
//    public void testAddTwoGames() {
//        Assert.assertTrue(gameDao.findAll().isEmpty());
//        gameDao.add(game1);
//        Assert.assertEquals(gameDao.findAll().size(), 1);
//        gameDao.add(game2);
//        Assert.assertEquals(gameDao.findAll().size(), 2);
//    }
//
//    @Test
//    public void testAddGameTwice() {
//        Assert.assertTrue(gameDao.findAll().isEmpty());
//        gameDao.add(game1);
//        Assert.assertEquals(gameDao.findAll().size(), 1);
//        gameDao.add(game1);
//        Assert.assertEquals(gameDao.findAll().size(), 1);
//    }
//
//    @Test
//    public void testFindAll() {
//        gameDao.add(game1);
//        gameDao.add(game2);
//        Assert.assertTrue(gameDao.findAll().contains(game1));
//        Assert.assertTrue(gameDao.findAll().contains(game2));
//    }
//
//    @Test
//    public void testFindById() {
//        gameDao.add(game1);
//        Assert.assertTrue(game1.equals(gameDao.findById(game1.getId())));
//    }
//
//    @Test
//    public void testFindByName() {
//        gameDao.add(game1);
//        Assert.assertTrue(game1.equals(gameDao.findByName(game1.getName())));
//    }
//
//    @Test
//    public void testUpdate() {
//        gameDao.add(game1);
//        Assert.assertEquals(game1.getName(), "Stellaris");
//        game1.setName("Age of Empires");
//        gameDao.update(game1);
//        Assert.assertEquals(gameDao.findById(game1.getId()).getName(), "Age of Empires");
//    }
//
//    @Test
//    public void testRemove() {
//        gameDao.add(game1);
//        Assert.assertEquals(gameDao.findAll().size(), 1);
//        gameDao.remove(game1);
//        Assert.assertTrue(gameDao.findAll().isEmpty());
//    }
}
