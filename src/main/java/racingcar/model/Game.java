package racingcar.model;

import racingcar.util.MessageFactory;
import racingcar.util.MessageProcessor;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final List<Car> cars;
    private final int tryCount;
    private final MessageProcessor messageProcessor;

    public Game(List<Car> cars, int tryCount, MessageProcessor messageProcessor) {
        if (tryCount <= 0) {
            throw new IllegalArgumentException(MessageFactory.invalidTryCountMessage);
        }
        this.cars = cars;
        this.tryCount = tryCount;
        this.messageProcessor = messageProcessor;
    }

    public void play() {
        messageProcessor.print(MessageFactory.gameEndingMessage);
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < tryCount; i++) {
            playOneRound(result);
        }
        result.append(MessageFactory.getWinnerMessage(getWinners()));
        messageProcessor.print(result.toString());
    }

    private void playOneRound(StringBuilder result) {
        for (Car car : cars) {
            car.move();
            result.append(car.getName()).append(" : ").append(car.getMoveResult()).append("\n");
        }
        result.append("\n");
    }

    public List<String> getWinners() {
        int maxPosition = getMaxPosition();
        return findWinners(maxPosition);
    }

    private int getMaxPosition() {
        int maxPosition = 0;
        for (Car car : cars) {
            maxPosition = Math.max(maxPosition, car.getPosition());
        }
        return maxPosition;
    }

    private List<String> findWinners(int maxPosition) {
        List<String> winners = new ArrayList<>();

        for (Car car : cars) {
            if (isWinner(car, maxPosition)) {
                winners.add(car.getName());
            }
        }
        return winners;
    }

    private boolean isWinner(Car car, int maxPosition) {
        return car.getPosition() == maxPosition;
    }
}
