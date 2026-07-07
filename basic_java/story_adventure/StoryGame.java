import java.util.Scanner;

/**
 * Interactive Story Adventure Game
 * A terminal-based choose-your-own-adventure with 20+ decision points.
 * Demonstrates: methods, Scanner, if-else, switch-case, loops, recursion.
 */
public class StoryGame {

    private static Scanner sc = new Scanner(System.in);
    private static int choicesMade = 0;
    private static int totalPossiblePaths = 1024; // 2^10 rough estimate

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("  THE FORGOTTEN TEMPLE");
        System.out.println("  An Interactive Story Adventure");
        System.out.println("========================================");
        System.out.println("\nYou wake up in a dark jungle with no memory...");
        System.out.println("Every choice shapes your destiny.\n");
        pause();

        scene1_clearing();
    }

    // Helper: pause and wait for Enter
    private static void pause() {
        System.out.print("\nPress Enter to continue...");
        sc.nextLine();
    }

    // Helper: get 1/2 choice
    private static int getChoice(String option1, String option2) {
        System.out.println("\n[1] " + option1);
        System.out.println("[2] " + option2);
        System.out.print("> ");
        int c = sc.nextInt();
        sc.nextLine(); // consume newline
        choicesMade++;
        return c;
    }

    // Helper: get 1/2/3 choice
    private static int getChoice3(String opt1, String opt2, String opt3) {
        System.out.println("\n[1] " + opt1);
        System.out.println("[2] " + opt2);
        System.out.println("[3] " + opt3);
        System.out.print("> ");
        int c = sc.nextInt();
        sc.nextLine();
        choicesMade++;
        return c;
    }

    // === SCENES ===

    static void scene1_clearing() {
        System.out.println("\n--- Scene 1: The Clearing ---");
        System.out.println("You're in a jungle clearing. Sunlight pierces the canopy.");
        System.out.println("To the north is an ancient temple entrance.");
        System.out.println("To the east, a narrow path leads deeper into the jungle.");

        int c = getChoice("Enter the temple", "Follow the jungle path");
        if (c == 1) scene2_temple_entrance();
        else scene3_jungle_path();
    }

    static void scene2_temple_entrance() {
        System.out.println("\n--- Scene 2: Temple Entrance ---");
        System.out.println("Massive stone doors loom before you, covered in glowing runes.");
        System.out.println("A pedestal stands nearby with two slots.");

        int c = getChoice("Push the doors open", "Examine the pedestal");
        if (c == 1) scene4_great_hall();
        else scene5_pedestal();
    }

    static void scene3_jungle_path() {
        System.out.println("\n--- Scene 3: Jungle Path ---");
        System.out.println("The path leads to a crumbling bridge over a ravine.");
        System.out.println("Below, a river rages. An old rope hangs nearby.");

        int c = getChoice("Cross the bridge carefully", "Swing across on the rope");
        if (c == 1) scene6_bridge_collapse();
        else scene7_rope_swing();
    }

    static void scene4_great_hall() {
        System.out.println("\n--- Scene 4: The Great Hall ---");
        System.out.println("You enter a vast hall. Three statues stand in a row.");
        System.out.println("Each holds a different colored gem.");

        int c = getChoice3("Take the red gem", "Take the blue gem", "Take the green gem");
        if (c == 1) scene8_gem_red();
        else if (c == 2) scene8_gem_blue();
        else scene8_gem_green();
    }

    static void scene5_pedestal() {
        System.out.println("\n--- Scene 5: The Pedestal ---");
        System.out.println("The pedestal has a riddle carved into it:");
        System.out.println("\"I speak without a mouth and hear without ears. What am I?\"");
        System.out.println("(a) An echo  (b) A book  (c) A shadow");

        System.out.print("> ");
        String ans = sc.nextLine().toLowerCase();
        choicesMade++;

        if (ans.equals("a") || ans.equals("an echo")) {
            System.out.println("The pedestal glows! A secret passage opens.");
            scene9_secret_passage();
        } else {
            System.out.println("Wrong answer. The floor gives way!");
            scene10_trap_room();
        }
    }

    static void scene6_bridge_collapse() {
        System.out.println("\n--- Scene 6: The Bridge ---");
        System.out.println("Halfway across, the bridge starts to collapse!");

        int c = getChoice("Run forward", "Grab the edge and climb back");
        if (c == 1) {
            System.out.println("You leap and barely make it to the other side!");
            scene11_river_bank();
        } else {
            System.out.println("You climb back but the path is blocked by a rockslide.");
            scene12_cave_entrance();
        }
    }

    static void scene7_rope_swing() {
        System.out.println("\n--- Scene 7: Rope Swing ---");
        System.out.println("You grab the rope and swing across the ravine!");
        System.out.println("You land on a small ledge with a cave entrance.");

        int c = getChoice("Enter the cave", "Climb up the ledge");
        if (c == 1) scene12_cave_entrance();
        else scene13_cliff_top();
    }

    static void scene8_gem_red() {
        System.out.println("\n--- The Red Gem ---");
        System.out.println("The red gem burns with fire. The floor rumbles...");
        System.out.println("A fiery guardian awakens!");

        int c = getChoice("Fight the guardian", "Throw the gem at it");
        if (c == 1) {
            System.out.println("You fight bravely but the guardian is too strong.");
            gameOver("consumed by flames");
        } else {
            System.out.println("The gem shatters and creates a smokescreen! You escape.");
            scene14_treasure_chamber();
        }
    }

    static void scene8_gem_blue() {
        System.out.println("\n--- The Blue Gem ---");
        System.out.println("The blue gem glows with water energy. A flood begins!");
        System.out.println("Water rushes into the hall from all sides.");

        int c = getChoice("Swim through the water", "Climb onto a statue");
        if (c == 1) {
            System.out.println("You swim through an underwater tunnel and emerge in a new chamber!");
            scene14_treasure_chamber();
        } else {
            System.out.println("The water rises too high. You're trapped.");
            gameOver("drowned in the hall");
        }
    }

    static void scene8_gem_green() {
        System.out.println("\n--- The Green Gem ---");
        System.out.println("The green gem pulses with life. Vines grow rapidly!");

        int c = getChoice("Follow the vines upward", "Cut through the vines");
        if (c == 1) {
            System.out.println("The vines lift you to a high balcony!");
            scene14_treasure_chamber();
        } else {
            System.out.println("Cutting the vines angers the temple spirit.");
            gameOver("crushed by falling debris");
        }
    }

    static void scene9_secret_passage() {
        System.out.println("\n--- Scene 9: Secret Passage ---");
        System.out.println("The passage is narrow and dark. You see two tunnels.");

        int c = getChoice("Take the left tunnel", "Take the right tunnel");
        if (c == 1) {
            System.out.println("The left tunnel leads to a library of ancient scrolls!");
            scene14_treasure_chamber();
        } else {
            System.out.println("The right tunnel ends in a dead end with a puzzle.");
            scene15_puzzle_room();
        }
    }

    static void scene10_trap_room() {
        System.out.println("\n--- Scene 10: Trap Room ---");
        System.out.println("You fall into a pit filled with snakes!");

        int c = getChoice("Stay completely still", "Fight the snakes");
        if (c == 1) {
            System.out.println("The snakes slither past you. A ladder is on the far wall.");
            System.out.println("You climb out and find yourself in the treasure chamber!");
            scene14_treasure_chamber();
        } else {
            System.out.println("The snakes bite. Your vision fades.");
            gameOver("succumbed to snake venom");
        }
    }

    static void scene11_river_bank() {
        System.out.println("\n--- Scene 11: River Bank ---");
        System.out.println("You're on a sandy river bank. An old boat is tied up.");
        System.out.println("Downstream, you see temple spires.");

        int c = getChoice("Take the boat downstream", "Walk along the bank");
        if (c == 1) {
            System.out.println("The boat takes you directly to the temple's back entrance!");
            scene14_treasure_chamber();
        } else {
            System.out.println("You walk for hours and find a small village.");
            System.out.println("The villagers tell you the temple is cursed.");
            gameOver("gave up the quest");
        }
    }

    static void scene12_cave_entrance() {
        System.out.println("\n--- Scene 12: Cave Entrance ---");
        System.out.println("The cave is damp and echoey. Bats flutter overhead.");
        System.out.println("You see ancient wall paintings telling a story.");

        int c = getChoice("Study the paintings", "Follow the sound of dripping water");
        if (c == 1) {
            System.out.println("The paintings reveal how to open the temple's inner sanctum!");
            System.out.println("You memorize the sequence and move forward.");
            scene14_treasure_chamber();
        } else {
            System.out.println("The dripping leads to an underground spring.");
            System.out.println("You drink the magical water and gain clarity.");
            scene13_cliff_top();
        }
    }

    static void scene13_cliff_top() {
        System.out.println("\n--- Scene 13: Cliff Top ---");
        System.out.println("You're on a cliff overlooking the entire jungle.");
        System.out.println("The temple entrance is visible below.");
        System.out.println("A rope ladder hangs down the cliff face.");

        int c = getChoice("Climb down the ladder", "Look for another way");
        if (c == 1) {
            System.out.println("You climb down safely and reach the temple entrance!");
            scene2_temple_entrance();
        } else {
            System.out.println("You find a hidden staircase carved into the rock!");
            scene14_treasure_chamber();
        }
    }

    static void scene14_treasure_chamber() {
        System.out.println("\n=== Scene 14: The Treasure Chamber ===");
        System.out.println("You enter a magnificent chamber filled with gold and jewels!");
        System.out.println("In the center, on a pedestal, lies the legendary Crown of Wisdom.");
        System.out.println("Inscriptions on the wall read:");
        System.out.println("\"Only the worthy may take the crown.\"");
        System.out.println("\"Choose wisely, for greed has a price.\"");

        int c = getChoice("Take the crown", "Leave everything and walk away");
        if (c == 1) {
            System.out.println("As you lift the crown, the temple begins to collapse!");
            ending_escape();
        } else {
            ending_wise();
        }
    }

    static void scene15_puzzle_room() {
        System.out.println("\n--- Scene 15: Puzzle Room ---");
        System.out.println("A room with tiles on the floor. Each tile has a number.");
        System.out.println("You must step on tiles that form a prime number sequence.");

        int c = getChoice3("Step on 2, 3, 5, 7", "Step on 1, 4, 6, 8", "Step on 2, 4, 6, 8");
        if (c == 1) {
            System.out.println("The tiles glow and a door opens!");
            scene14_treasure_chamber();
        } else {
            System.out.println("Wrong sequence! The floor shoots darts.");
            gameOver("hit by poison darts");
        }
    }

    // === ENDINGS ===

    static void ending_escape() {
        System.out.println("\n==============================");
        System.out.println("  ESCAPE FROM THE TEMPLE");
        System.out.println("==============================");
        System.out.println("You run as the temple crumbles around you!");
        System.out.println("You burst through the entrance just as it collapses.");
        System.out.println("The crown glows in your hands. You survived with riches beyond imagination!");
        System.out.println("\nBut you wonder... was the crown worth the destruction of history?");
        printEnding();
    }

    static void ending_wise() {
        System.out.println("\n==============================");
        System.out.println("  THE WISE DECISION");
        System.out.println("==============================");
        System.out.println("You leave the treasure untouched.");
        System.out.println("As you exit, the temple seals itself, preserving its secrets.");
        System.out.println("You return to civilization with something more valuable: wisdom.");
        System.out.println("\nYou realize that some treasures are meant to remain undiscovered.");
        printEnding();
    }

    static void gameOver(String reason) {
        System.out.println("\n=====================================");
        System.out.println("      GAME OVER");
        System.out.println("=====================================");
        System.out.println("Your journey ends tragically...");
        System.out.println("You were " + reason + ".");
        System.out.println("Choices you made: " + choicesMade);
        System.out.println("Paths not taken: " + (totalPossiblePaths - choicesMade));
        printAsciiArt();
        System.exit(0);
    }

    static void printEnding() {
        System.out.println("\nTotal choices made: " + choicesMade);
        System.out.println("Possible story paths: " + totalPossiblePaths);
        System.out.println("Paths you missed: " + (totalPossiblePaths - choicesMade));
        printAsciiArt();
        System.exit(0);
    }

    static void printAsciiArt() {
        System.out.println("\n");
        System.out.println("   ╔══════════════════════════════════╗");
        System.out.println("   ║     CONGRATULATIONS!            ║");
        System.out.println("   ║  You completed the adventure!   ║");
        System.out.println("   ╚══════════════════════════════════╝");
        System.out.println();
        System.out.println("          \\   |   /");
        System.out.println("           \\  |  /");
        System.out.println("            \\ | /");
        System.out.println("        ╔═══════════╗");
        System.out.println("        ║  THE END  ║");
        System.out.println("        ╚═══════════╝");
        System.out.println("            / | \\");
        System.out.println("           /  |  \\");
        System.out.println("          /   |   \\");
        System.out.println();
        System.out.println("   \"Not all who wander are lost\"");
        System.out.println("           - J.R.R. Tolkien");
    }
}
