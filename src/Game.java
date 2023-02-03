import java.util.Random;
import java.util.Scanner;

public class Game {
    private Deck deck;
    private int balance;

    public Game() {
        super();
        this.deck = new Deck();
        this.balance = 100;
    }

    public void setBalance(int value) {
        this.balance = value;
    }

    public int getBalance() {
        return this.balance;
    }

    public static void main(String[] args) {
        Game game = new Game();
        Hand userHand = new Hand();
        Hand dealerHand = new Hand();
        System.out.println("=====================================");
        System.out.println("Welcome to the Game.");
        System.out.println("Your initial balance is :: $"+game.getBalance());
        System.out.println("\nReady to play? Let's go!!!");
        System.out.println("+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+\n");
        int betValue = 0;
        String userInput = "";
        do {
            Scanner sc = new Scanner(System.in);
            System.out.println("How much do you want to bet?");
            System.out.println("Enter a value between 1 and "+game.getBalance());
            System.out.println("*** Type 'Quit' to exit the game.");
            userInput = sc.nextLine();
            userInput = userInput.trim().toLowerCase();
            if(!userInput.equals("quit")) {
                try {
                    betValue = Integer.valueOf(userInput);
                    if (betValue > game.getBalance())
                        throw new Exception("Bet value can not be greater than balance.");
                    boolean userWins = game.play(userHand, dealerHand);
                    if(userWins) {
                        System.out.println("******************************");
                        System.out.println("\t You WIN !");
                        System.out.println("******************************");
                    } else {
                        System.out.println("------------------------------");
                        System.out.println("\t You LOST");
                        System.out.println("------------------------------");
                    }
                    System.out.println("\t You: "+userHand.getValue() +" / Dealer: "+dealerHand.getValue());
                    game.setBalance(userWins ? game.getBalance() + betValue : game.getBalance() - betValue);
                    System.out.println("~~~~~~~~~~~~~~~~ Current balance: $"+game.getBalance()+" ~~~~~~~~~~~~~~~~");
                } catch (Exception e) {
                    System.out.println("Wrong input. Please enter an integer between 1 an "+game.getBalance());
                }
            } else break;
        } while (game.getBalance() >= 1);

        // sc.close();
        System.out.println("############### THE END ################");
        System.out.println("Your final balance is :: $" +game.getBalance());
    }

    public boolean play(Hand userHand, Hand dealerHand) {
        // Initial hands of 2 cards chosen randomly
        userHand.addCard(deck.hit());
        userHand.addCard(deck.hit());

        dealerHand.addCard(deck.hit());
        dealerHand.addCard(deck.hit());

        return this.verifyHands(userHand, dealerHand);
    }

    public boolean verifyHands(Hand userHand, Hand dealerHand) {
        if(dealerHand.isBlackjack() || userHand.getValue() > 21) {
            return false;
        } else if(userHand.isBlackjack() || dealerHand.getValue() > 21) {
            return true;
        } else {
            Random rand = new Random();
            System.out.println("You currently have a total value of "+userHand.getValue());
            System.out.println("Your cards are :");
            userHand.cards.forEach(c -> System.out.println(c.getTitle() +" of " + c.getType() + " = " +c.getValue()));
            Card oneCardFromDealerHand = dealerHand.cards.get(rand.nextInt(0, dealerHand.cards.size()));
            System.out.println("The dealer has one " + oneCardFromDealerHand.getTitle() +" of " + oneCardFromDealerHand.getType() + " = " + oneCardFromDealerHand.getValue());
            System.out.println("What next?");
            System.out.println("Type 'Hit' or 'Stand':");
            String userChoice = "";
            Scanner sc = new Scanner(System.in);
            do {
                userChoice = sc.next().trim().toLowerCase();
            } while (!(userChoice.equals("hit") || userChoice.equals("stand")));
            sc.close();
            if(userChoice.equals("hit")) {
                userHand.addCard(this.deck.hit());
                System.out.println("\n\n");
                return verifyHands(userHand, dealerHand);
            } else { // Stand
                if(dealerHand.getValue() <= 16) {
                    do {
                        System.out.println("\n\n");
                        System.out.println("It's up to the dealer to it!");
                        System.out.println("...");
                        dealerHand.addCard(this.deck.hit());
                        System.out.println("The dealer currently has a total value of "+dealerHand.getValue());
                        System.out.println("His cards are :");
                        dealerHand.cards.forEach(c -> System.out.println(c.getTitle() +" of " + c.getType() + " = " +c.getValue()));
                    } while(dealerHand.getValue() <= 16);
                }
                return dealerHand.getValue() > 21 || userHand.getValue() > dealerHand.getValue();
            }
        }
    }
}
