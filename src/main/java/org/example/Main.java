package org.example;

import java.util.*;

class Passenger {
    private double arrivalTime;
    private double boardingTime;

    public Passenger(double arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }

    public double getBoardingTime() {
        return boardingTime;
    }

    public void setBoardingTime(double boardingTime) {
        this.boardingTime = boardingTime;
    }
}

class Ferry {
    private double arrivalTime;
    private boolean isTerminal;
    private int availableSeats;

    public Ferry(double arrivalTime, boolean isTerminal, int availableSeats) {
        this.arrivalTime = arrivalTime;
        this.isTerminal = isTerminal;
        this.availableSeats = availableSeats;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }

    public boolean isTerminal() {
        return isTerminal;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }
}

class Pier {
    private Queue<Passenger> passengerQueue;
    private Queue<Ferry> ferryQueue;

    public Pier() {
        passengerQueue = new LinkedList<>();
        ferryQueue = new LinkedList<>();
    }

    public void addPassenger(Passenger passenger) {
        passengerQueue.offer(passenger);
    }

    public void addFerry(Ferry ferry) {
        ferryQueue.offer(ferry);
    }

    public Passenger getNextPassenger() {
        return passengerQueue.poll();
    }

    public Ferry getNextFerry() {
        return ferryQueue.poll();
    }

    public boolean isPassengerQueueEmpty() {
        return passengerQueue.isEmpty();
    }

    public boolean isFerryQueueEmpty() {
        return ferryQueue.isEmpty();
    }
}
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Завдання: ");
        int task = scanner.nextInt();
        switch (task){
            case 1:{
                double passengerArrivalRate = 0.1; // Середній час прибуття пасажирів (приклад: 0.1 пасажира на хвилину)
                double ferryArrivalRate = 0.05; // Середній час прибуття катерів (приклад: 0.05 катера на хвилину)
                int maxPassengersOnPier = 50; // Максимальна кількість пасажирів на зупинці
                int maxSeatsInFerry = 100; // Максимальна кількість місць у катері

                Pier pier = new Pier();
                Random random = new Random();
                double currentTime = 0.0;

                while (currentTime < 1440.0) { // 1440 хвилин у 24 годинах
                    // Генерація пасажирів
                    double passengerArrivalTime = -Math.log(1 - random.nextDouble()) / passengerArrivalRate;
                    currentTime += passengerArrivalTime;
                    pier.addPassenger(new Passenger(currentTime));

                    // Генерація катерів
                    double ferryArrivalTime = -Math.log(1 - random.nextDouble()) / ferryArrivalRate;
                    currentTime += ferryArrivalTime;
                    int availableSeats = random.nextInt(maxSeatsInFerry) + 1;
                    pier.addFerry(new Ferry(currentTime, random.nextBoolean(), availableSeats));

                    // Перевірка катерів та пасажирів
                    while (!pier.isPassengerQueueEmpty() && !pier.isFerryQueueEmpty()) {
                        Passenger passenger = pier.getNextPassenger();
                        Ferry ferry = pier.getNextFerry();

                        if (passenger != null && ferry != null) {
                            double waitingTime = currentTime - passenger.getArrivalTime();
                            passenger.setBoardingTime(waitingTime);

                            if (ferry.isTerminal() || pier.isPassengerQueueEmpty() || waitingTime >= 10) {
                                // Пасажир може сісти в катер
                                availableSeats = ferry.getAvailableSeats();
                                if (availableSeats > 0) {
                                    ferry.setAvailableSeats(availableSeats - 1);
                                }
                            } else {
                                // Відправити катер без пасажира через недостатній інтервал часу
                            }
                        }
                    }
                }
            }
            break;
            case 2: {
                Scanner scanner1 = new Scanner(System.in);
                Map<String, String> dictionary = new HashMap<>();
                Map<String, Integer> wordCounter = new LinkedHashMap<>();

                while (true) {
                    System.out.println("Оберіть опцію:");
                    System.out.println("1. Додати слово та його переклад");
                    System.out.println("2. Знайти переклад слова");
                    System.out.println("3. Замінити переклад слова");
                    System.out.println("4. Видалити переклад слова");
                    System.out.println("5. Додати слово");
                    System.out.println("6. Замінити слово");
                    System.out.println("7. Видалити слово");
                    System.out.println("8. Показати топ-10 популярних слів");
                    System.out.println("9. Показати топ-10 непопулярних слів");
                    System.out.println("0. Вийти");

                    int option = scanner1.nextInt();
                    scanner.nextLine(); // очистити буфер після введення числа

                    switch (option) {
                        case 1:
                            System.out.print("Введіть слово: ");
                            String word = scanner1.nextLine();
                            System.out.print("Введіть переклад слова: ");
                            String translation = scanner.nextLine();
                            dictionary.put(word, translation);
                            wordCounter.put(word, wordCounter.getOrDefault(word, 0) + 1);
                            break;

                        case 2:
                            System.out.print("Введіть слово для пошуку перекладу: ");
                            word = scanner1.nextLine();
                            if (dictionary.containsKey(word)) {
                                System.out.println("Переклад: " + dictionary.get(word));
                                wordCounter.put(word, wordCounter.getOrDefault(word, 0) + 1);
                            } else {
                                System.out.println("Слово не знайдено в словнику.");
                            }
                            break;

                        case 0: {
                            System.out.println("Програма завершена.");
                            return;
                        }
                        default:
                            System.out.println("Невідома опція. Спробуйте ще раз.");
                    }
                }
            }
            default:
                System.out.println("Error!");
        }
    }
}