import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Font;

public class Traffic extends JPanel implements KeyListener
{
    // traffic variables
    private Car car;
    private boolean isFast = true;
    private boolean isStopped = false;
    private static int trafficCount = 0;
    private JLabel carCount = new JLabel();
    private JLabel resetCountLabel = new JLabel();
    private JButton resetCount = new JButton("Reset Count");

    public Traffic()
    {
        // setting up JFrame base
        this.car = new Car(0, 100, isFast, isStopped, resetCountLabel);
        JFrame frame = new JFrame("Traffic!");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        MouseTracker m1 = new MouseTracker(); // creates mouse listener to detect when you clickk

        this.setBackground(Color.BLACK); 
        this.setPreferredSize(new Dimension(800,600));
        this.addMouseListener(m1);
        this.setLayout(null); // removes the default layout manager so components can be placed manually with setBounds()

        this.addKeyListener(this);
        this.setFocusable(true); // allows for the panel to receive the keyboard input

        // configuring a label to display the number of cars that pass by
        carCount.setText("Count of Cars: 0");
        carCount.setBounds(10, 10, 200, 30);
        carCount.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 28));
        carCount.setForeground(Color.WHITE);
        this.add(carCount);

        // configure a label that displays the status of the car (go, stop, or slow)
        resetCountLabel.setText("Go!");
        resetCountLabel.setBounds(430, 170, 200, 30);
        resetCountLabel.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 28));
        resetCountLabel.setForeground(Color.GREEN);
        this.add(resetCountLabel);

        // configure a button that lets you reset the count of cars
        resetCount.setBounds(10, 50, 160, 35);
        resetCount.setFont(new Font("Serif", Font.BOLD, 18));
        this.add(resetCount);

        // attach the listener to the button
        resetCount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                trafficCount = 0;
                carCount.setText("Count of Cars: " + trafficCount);
                requestFocusInWindow(); // fix the focus so the keyboard can still be used
            }
        });

        // final frame set up
        frame.add(this);
        frame.pack();
        frame.setVisible(true); 
        this.requestFocusInWindow(); // gives the panel focus for the keyboard
    }
    
    public static void updateTrafficCount() {
        trafficCount++;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 

        // draws the grass
        g.setColor(new Color(34, 139, 34)); 
        g.fillRect(0, 0, getWidth(), getHeight());

        // draws the road
        g.setColor(Color.GRAY);
        g.fillRect(0, 240, getWidth(), 220);
        g.setColor(Color.BLACK);
        g.fillRect(0, 250, getWidth(), 200);

        //draws the dashes on road
        g.setColor(Color.YELLOW);
        g.fillRect(0, 340, 50, 20);

        g.setColor(Color.YELLOW);
        g.fillRect(150, 340, 70, 20);

        g.setColor(Color.YELLOW);
        g.fillRect(300, 340, 70, 20);

        g.setColor(Color.YELLOW);
        g.fillRect(450, 340, 70, 20);

        g.setColor(Color.YELLOW);
        g.fillRect(600, 340, 70, 20);

        g.setColor(Color.YELLOW);
        g.fillRect(750, 340, 70, 20);

        // draws the traffic box
        g.setColor(Color.BLACK);
        g.fillRect(320, 5, 105, 215);
        g.setColor(Color.GRAY);
        g.fillRect(325, 10, 100, 210);
        
        // draws the red light
        g.setColor(Color.RED);
        g.fillOval(350, 20, 50, 50);

        // draws the yellow light
        g.setColor(Color.YELLOW);
        g.fillOval(350, 90, 50, 50);

        // draws the green light
        g.setColor(Color.GREEN);
        g.fillOval(350, 160, 50, 50);

        car.draw(g);
        carCount.setText("Count of Cars: " + trafficCount);
    }

    public void animate() // animating the car to move accross the screen
    {
        while(true) { // true always to keep the car always going forever
            try {
                Thread.sleep(10);
            }
            catch(InterruptedException ex) {
                Thread.currentThread().interrupt(); // handles interruptions
            }
            this.repaint(); // updates the car's position every loop/redraws panel
        }
    }

    class MouseTracker implements MouseListener
    {
        public void mouseClicked(MouseEvent m)
        {
            // coordinates for the mouse when you click
            int mx = m.getX();
            int my = m.getY();

            // GREEN LIGHT
            // approx area for the GREEN LIGHT button
            double cx = 375;
            double cy = 160;
            double rx = 50;
            double ry = 50;
            if (checkLight(mx, my, cx, cy, rx, ry) <= 1) // if GREEN LIGHT was clicked
            {   
                car.setStopped(false); // car no longer stopped (if it was)
                car.setFast(true); // car should be going fast 
            }

            // YELLOW LIGHT
            // approx area for the YELLOW LIGHT button
            cx = 375;
            cy = 115;
            rx = 25;
            ry = 25;
            if (checkLight(mx, my, cx, cy, rx, ry) <= 1) // if Yellow LIGHT was clicked
            {
                car.setStopped(false); // car no longer stopped (if it was)
                car.setFast(false); // car should not be going fast 
            }

            // RED LIGHT
            // approx area for the RED LIGHT button
            cx = 375;
            cy = 45;
            rx = 25;
            ry = 25;
            if (checkLight(mx, my, cx, cy, rx, ry) <= 1)
            {
                car.setStopped(true); // car should stop
            }

        }

        // empty methods since they were unused but needed to be implemented
        public void mouseEntered(MouseEvent m){}
        public void mouseExited(MouseEvent m){}
        public void mousePressed(MouseEvent m){}
        public void mouseReleased(MouseEvent m){}

        public static double checkLight(int mx, int my, double cx, double cy, double rx, double ry) {
            return ((mx - cx)*(mx - cx))/(rx*rx) + ((my - cy)*(my - cy))/(ry*ry);
        }
    }

    public void keyPressed(KeyEvent e) { // deals with buttons pressed on the keyboard
        if (e.getKeyChar() == 's') { // RED LIGHT: when s is pressed, car should stop
            isStopped = true;
        }

        if (e.getKeyChar() == 'y') { // YELLOW LIGHT: when y is pressed, car should go slow
            isStopped = false;
            isFast = false;
        }

        if (e.getKeyChar() == 'g') { // GREEN LIGHT: when g is pressed, car should go fast
            isStopped = false;
            isFast = true;
        }

        if (e.getKeyChar() == 'r') { // RESET: when r is pressed, car counter should reset to 0
            trafficCount = 0;
        }
    }

    // empty methods since they were unused but needed to be implemented
    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
}


