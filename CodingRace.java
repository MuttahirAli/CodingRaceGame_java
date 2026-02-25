import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class CodingRace {

    static int pn = 0;
    static int[] scores = {0, 0};

    static long startTime;
    static long[][] times = new long[2][3];
    static boolean[][] solved = new boolean[2][3];

    static JTextArea pl1, pl2, puzzleLabel;
    static JLabel puzzleResultLabel;
    static JButton startButton, da, db;

    static Timer player2Timer;

    static String[] puzzles = {
        "int temp=a;\na=b;\nb=temp;",
        "for(int i=1;i<=10;i++){\nSystem.out.println(i);\n}",
        "for(int i=1;i<=10;i+=2){\nSystem.out.println(i);\n}"
    };

    public static void main(String[] args) {
        JWindow splash = new JWindow();
        splash.setSize(800, 600);
        splash.setLocationRelativeTo(null);
        splash.getContentPane().setBackground(new Color(5, 10, 25));
        splash.setLayout(null);

        JLabel title = new JLabel("WELCOME TO CODING RACE GAME");
        title.setBounds(40, 180, 720, 60);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Verdana", Font.BOLD, 36));
        title.setForeground(new Color(0, 255, 200));

        JLabel slogan = new JLabel("Hi Coders");
        slogan.setBounds(40, 240, 720, 40);
        slogan.setHorizontalAlignment(SwingConstants.CENTER);
        slogan.setFont(new Font("Verdana", Font.BOLD, 24));
        slogan.setForeground(new Color(0, 255, 200));

        JProgressBar bar = new JProgressBar();
        bar.setBounds(250, 350, 300, 25);
        bar.setIndeterminate(true);
        bar.setForeground(new Color(0, 255, 200));

        splash.add(title);
        splash.add(slogan);
        splash.add(bar);
        splash.setVisible(true);

        new Timer().schedule(new TimerTask() {
            public void run() {
                splash.dispose();
                SwingUtilities.invokeLater(() -> startGame());
            }
        }, 3000);
    }

    public static void startGame() {
        JFrame f = new JFrame("Coding Race Game");
        f.setSize(900, 650);
        f.setLayout(null);
        f.setLocationRelativeTo(null);
        f.getContentPane().setBackground(new Color(5, 10, 25));

        JLabel title = new JLabel("CODING RACE GAME");
        title.setBounds(250, 10, 400, 40);
        title.setForeground(new Color(0, 255, 200));
        title.setFont(new Font("Verdana", Font.BOLD, 28));

        JLabel p1 = new JLabel("PLAYER 1");
        p1.setBounds(130, 220, 200, 30);
        p1.setForeground(new Color(0, 255, 200));
        p1.setFont(new Font("Verdana", Font.BOLD, 18));

        JLabel p2 = new JLabel("PLAYER 2");
        p2.setBounds(650, 220, 200, 30);
        p2.setForeground(new Color(255, 100, 130));
        p2.setFont(new Font("Verdana", Font.BOLD, 18));

        puzzleLabel = new JTextArea();
        puzzleLabel.setBounds(300, 90, 300, 90);
        puzzleLabel.setEditable(false);
        puzzleLabel.setFont(new Font("Consolas", Font.BOLD, 15));
        puzzleLabel.setBackground(new Color(10, 20, 40));
        puzzleLabel.setForeground(Color.WHITE);
        puzzleLabel.setBorder(new LineBorder(new Color(0, 255, 200), 3, true));

        puzzleResultLabel = new JLabel("", SwingConstants.CENTER);
        puzzleResultLabel.setBounds(300, 185, 300, 30);
        puzzleResultLabel.setForeground(Color.ORANGE);
        puzzleResultLabel.setFont(new Font("Verdana", Font.BOLD, 14));

        pl1 = new JTextArea();
        pl1.setBounds(70, 260, 300, 230);
        pl1.setEnabled(false);
        pl1.setFont(new Font("Consolas", Font.BOLD, 14));
        pl1.setBackground(new Color(10, 20, 40));
        pl1.setForeground(Color.WHITE);
        pl1.setBorder(new LineBorder(new Color(0, 255, 200), 3, true));

        pl2 = new JTextArea();
        pl2.setBounds(530, 260, 300, 230);
        pl2.setEnabled(false);
        pl2.setFont(new Font("Consolas", Font.BOLD, 14));
        pl2.setBackground(new Color(20, 10, 30));
        pl2.setForeground(Color.WHITE);
        pl2.setBorder(new LineBorder(new Color(255, 80, 120), 3, true));

        startButton = new JButton("START PUZZLE");
        startButton.setBounds(360, 50, 180, 30);
        startButton.setBackground(new Color(0, 255, 200));
        startButton.setFont(new Font("Verdana", Font.BOLD, 14));

        startButton.addActionListener(e -> {
            startButton.setEnabled(false);
            nextPuzzle(f);
        });

        da = new JButton("DONE");
        da.setBounds(150, 520, 150, 35);
        da.setEnabled(false);
        da.setBackground(new Color(0, 255, 200));
        da.setFont(new Font("Verdana", Font.BOLD, 14));
        da.addActionListener(e -> checkAnswer(f, pl1, 0));

        db = new JButton("DONE");
        db.setBounds(600, 520, 150, 35);
        db.setEnabled(false);
        db.setBackground(new Color(255, 100, 130));
        db.setFont(new Font("Verdana", Font.BOLD, 14));
        db.addActionListener(e -> checkAnswer(f, pl2, 1));

        f.add(title);
        f.add(p1);
        f.add(p2);
        f.add(puzzleLabel);
        f.add(puzzleResultLabel);
        f.add(pl1);
        f.add(pl2);
        f.add(startButton);
        f.add(da);
        f.add(db);

        f.setVisible(true);
    }

    public static void nextPuzzle(JFrame f) {
        if (pn >= puzzles.length) return;

        puzzleResultLabel.setText("");
        pl1.setText("");
        pl2.setText("");

        pl1.setEnabled(true);
        pl2.setEnabled(true);

        da.setEnabled(true);
        db.setEnabled(true);

        puzzleLabel.setText(
                pn == 0 ? "Swap two numbers a and b"
                        : pn == 1 ? "Print 1-10 using for loop"
                        : "Print odd numbers 1-10 using for loop"
        );

        startTime = System.currentTimeMillis();
        simulatePlayer2(pl2, db, puzzles[pn], 1000);
    }

    public static void checkAnswer(JFrame f, JTextArea player, int index) {
        String ans = player.getText().replaceAll("\\r", "").trim();

        if (ans.length() == 0) {
            JOptionPane.showMessageDialog(f, "Enter your code");
            return;
        }

        String user = ans.replaceAll("\\s+", "");

        // fix: allow alternative syntax for puzzle 3
        String correct = puzzles[pn].replaceAll("\\s+", "");
        String altCorrect = "";
        if (pn == 2) { // Puzzle 3
            altCorrect = "for(inti=1;i<=10;i=i+2){System.out.println(i);}";
        }

        if (user.equals(correct) || user.equals(altCorrect)) {
            long time = (System.currentTimeMillis() - startTime) / 1000;

            scores[index]++;
            times[index][pn] = time;
            solved[index][pn] = true;

            puzzleResultLabel.setText("Player " + (index + 1) + " solved in " + time + " sec");

            pl1.setEnabled(false);
            pl2.setEnabled(false);
            da.setEnabled(false);
            db.setEnabled(false);

            if (player2Timer != null) player2Timer.cancel();

            pn++;

            if (pn < 3) startButton.setEnabled(true);

            if (pn == 3) {
                new Timer().schedule(new TimerTask() {
                    public void run() {
                        showFinalResult();
                    }
                }, 1500);
            }
        } else {
            JOptionPane.showMessageDialog(f, "Wrong Answer");
        }
    }

    public static void simulatePlayer2(JTextArea pl2, JButton db, String correct, int speed) {
        player2Timer = new Timer();
        char c[] = correct.toCharArray();
        pl2.setText("");

        player2Timer.scheduleAtFixedRate(new TimerTask() {
            int i = 0;

            public void run() {
                if (i < c.length) {
                    pl2.append(String.valueOf(c[i]));
                    i++;
                } else {
                    player2Timer.cancel();
                    SwingUtilities.invokeLater(() -> {
                        if (db.isEnabled()) db.doClick();
                    });
                }
            }
        }, 1000, speed);
    }

    public static void showFinalResult() {
        JFrame res = new JFrame("Analysis");
        res.setSize(900, 650);
        res.setLayout(null);
        res.setLocationRelativeTo(null);
        res.getContentPane().setBackground(new Color(5, 10, 25));

        JLabel title = new JLabel("FINAL ANALYSIS", SwingConstants.CENTER);
        title.setBounds(200, 20, 500, 50);
        title.setFont(new Font("Verdana", Font.BOLD, 34));
        title.setForeground(new Color(0, 255, 200));

        JLabel h1 = new JLabel("PLAYER 1");
        h1.setBounds(130, 80, 200, 30);
        h1.setForeground(new Color(0, 255, 200));
        h1.setFont(new Font("Verdana", Font.BOLD, 20));

        JLabel h2 = new JLabel("PLAYER 2");
        h2.setBounds(580, 80, 200, 30);
        h2.setForeground(new Color(255, 100, 130));
        h2.setFont(new Font("Verdana", Font.BOLD, 20));

        JTextArea p1 = new JTextArea();
        p1.setBounds(100, 120, 250, 350);
        p1.setBackground(new Color(10, 20, 40));
        p1.setForeground(Color.WHITE);
        p1.setFont(new Font("Consolas", Font.BOLD, 16));
        p1.setBorder(new LineBorder(new Color(0, 255, 200), 3, true));
        p1.setEditable(false);

        JTextArea p2 = new JTextArea();
        p2.setBounds(550, 120, 250, 350);
        p2.setBackground(new Color(10, 20, 40));
        p2.setForeground(Color.WHITE);
        p2.setFont(new Font("Consolas", Font.BOLD, 16));
        p2.setBorder(new LineBorder(new Color(255, 100, 130), 3, true));
        p2.setEditable(false);

        String s1 = "", s2 = "";
        for (int i = 0; i < 3; i++) {
            s1 += "Puzzle " + (i + 1) + "\n";
            s1 += solved[0][i] ? "Time : " + times[0][i] + " sec\n\n" : "Not Solved\n\n";

            s2 += "Puzzle " + (i + 1) + "\n";
            s2 += solved[1][i] ? "Time : " + times[1][i] + " sec\n\n" : "Not Solved\n\n";
        }

        s1 += "Solved : " + scores[0];
        s2 += "Solved : " + scores[1];

        p1.setText(s1);
        p2.setText(s2);

        JLabel winner = new JLabel("", SwingConstants.CENTER);
        winner.setBounds(200, 520, 500, 50);
        winner.setFont(new Font("Verdana", Font.BOLD, 32));
        winner.setForeground(Color.ORANGE);

        if (scores[0] > scores[1]) {
            winner.setText("Player 1 is CHAMP!");
        } else if (scores[1] > scores[0]) {
            winner.setText("Player 2 is CHAMP!");
        } else {
            winner.setText("MATCH DRAW");
        }

        res.add(title);
        res.add(h1);
        res.add(h2);
        res.add(p1);
        res.add(p2);
        res.add(winner);

        res.setVisible(true);
    }
}