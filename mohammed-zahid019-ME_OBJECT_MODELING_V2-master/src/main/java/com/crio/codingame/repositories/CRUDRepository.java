package com.crio.codingame.repositories;

import java.util.List;
import java.util.Optional;

public interface CRUDRepository<T,ID> {
    public T save(T entity);
    public List<T> findAll();
    public Optional<T> findById(ID id);
    boolean existsById(ID id);
    public void delete(T entity);
    public void deleteById(ID id);
    public long count();
}
private IDeckService deckService;
private IPlayerService playerService;
private Stack<Card> dicardPile;
private int currentPlayerIndex;
private boolean isReverse;
private GameStatus gameState;

public GameService(IDeckService deckService, IPlayerService playerService) {
    this.deckService = deckService;
    this.playerService = playerService;
    dicardPile = new Stack<>();
    currentPlayerIndex = 0;
    isReverse = false;
}

@Override
public void startGame(List<Player> players) {


}

@Override
public void playCard() {

}

@Override
public void drawCard() {

}

@Override
public void getNextPlayer() {

}

@Override
public void changePlayOrder() {

}

@Override
public void skipNextPlayer() {

}

@Override
public void drawTwoCards() {

}