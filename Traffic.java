import java.awt.Dimension ;
import java.awt.Color ;
import java.awt.Graphics ;
import java.awt.Graphics2D ;
import java.awt.event.MouseEvent ;
import java.awt.event.MouseListener ;
import javax.swing.JFrame ;
import javax.swing.JLabel;
import javax.swing.JPanel ;
import javax.swing.WindowConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Random;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Font;

public class Traffic extends JPanel implements KeyListener
{
    // traffic variables
    Random rand = new Random();
    Car car;
    private boolean fast = true;
    private boolean stopped = false;
    private int traffic_count = 0;
    private JLabel car_num = new JLabel();
    private JLabel stat = new JLabel();
    private JButton reset_count = new JButton("Reset Count");

    public Traffic()
    {
        // setting up JFrame base
        car = new Car(0,100);
        JFrame frame = new JFrame("Traffic!");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        MouseShaper m1 = new MouseShaper(); // creates mouse listener to detect when you clickk

        this.setBackground(Color.BLACK); 
        this.setPreferredSize(new Dimension(800,600)); // setting the size of the panel
        this.addMouseListener(m1); // allows this panel to listen to mouse clicks
        this.setLayout(null);

        this.addKeyListener(this); // allows the panel to listen for keyboard input
        this.setFocusable(true); // allows for the panel to receive the keyboard input

        // creating a label to display the number of cars that pass by
        car_num.setText("Count of Cars: 0");
        car_num.setBounds(10, 10, 200, 30);
        car_num.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 28));
        car_num.setForeground(Color.WHITE);
        this.add(car_num);

        // creating a label that displays the status of the car (go, stop, or slow)
        stat.setText("Go!");
        stat.setBounds(430, 170, 200, 30);
        stat.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 28));
        stat.setForeground(Color.GREEN);
        this.add(stat);

        // creating a button that lets you reset the count of cars
        reset_count.setBounds(10, 50, 160, 35);
        reset_count.setFont(new Font("Serif", Font.BOLD, 18));
        this.add(reset_count);

        //attaches the listener to the button
        reset_count.addActionListener(new ActionListener()
        {
        public void actionPerformed(ActionEvent e) //when the traffic count button is reset
        {
            traffic_count = 0; // the count is reset
            car_num.setText("Count of Cars: " + traffic_count); // update the label displaying the count
            requestFocusInWindow(); // fix the focus so the keyboard can still be used
        }
        });

        // final frame set up
        frame.add(this);
        frame.pack();
        frame.setVisible(true); 
        this.requestFocusInWindow(); // gives the panel focus for the keyboard
    }

    @Override
    protected void paintComponent(Graphics g)
    {
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

        // draws the car
        car.draw(g);

        // update the count of cars label
        car_num.setText("Count of Cars: " + traffic_count);
    }

    public class Car
    {
        private int x; // the car's x position
        private int y; // the car's y position
        private int dx = 1; // the change in x (for when it moves)

        // each car has a new random colour for its wheels, base and front 
        private Color wheel_colour = new Color(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256));
        private Color base_colour = new Color(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256));
        private Color front_colour = new Color(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256));


        public Car(int x, int y) // Car constructor
        {   
            // sets up the car's x and y positions
            this.x = x;
            this.y = y;
        }

        // getters for coordinates
        public int getX(){ return x;}
        public int getY(){ return y;}

        public void draw(Graphics g)
        {
            if(fast && !stopped) // GREEN LIGHT
            {
                x += dx*4; // move very fast (4x speed)
                stat.setText("Go!"); // update the status label
                stat.setForeground(Color.GREEN); // update the colour of the status label
            }

            else if(!fast && !stopped) // YELLOW LIGHT
            {
                x += dx; // move slower (1x speed)
                stat.setText("Slow Down!"); // update the status label
                stat.setForeground(Color.YELLOW); // update the colour of the status label
            }
            else // RED LIGHT (dont move)
            {
                stat.setText("Stop!"); // update the status label
                stat.setForeground(Color.RED); // update the colour of the status label
            }
            
            if (x > 900) // for when the car moves off the screen (off right side), it should reappear at the start (left side)
            {
            x = -50;

            // since its a "new car" that is appearing, it will have new set colours
            wheel_colour = new Color(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256));
            base_colour = new Color(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256));
            front_colour = new Color(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256));

            // new car seen, therefore we update the traffic count
            traffic_count++;
            }

            Graphics2D g2d = (Graphics2D) g;

            // creating the wheels of the car
            g2d.setColor(wheel_colour); // wheels both have the same colour
            g2d.fillOval(x,360,40,40);
            g2d.fillOval(x - 100,360,40,40);

            // creating the base of the car
            g2d.setColor(base_colour);
            g.fillRect(x-100, 300, 110, 80);

            // creatinig the front of the car (and the windshield)
            g2d.setColor(front_colour);
            g.fillRect(x, 310, 60, 65);
            g2d.setColor(wheel_colour);
            g.fillRect(x + 10, 320, 50, 30);
        }
    }

    public void animate() // animating the car to move accross the screen
    {
        while(true) // true always to keep the car always going forever
        {
            try
            {
                Thread.sleep(10);
            }
            catch(InterruptedException ex)
            {
                Thread.currentThread().interrupt(); // handles interruptions
            }
            this.repaint(); // updates the car's position every loop/redraws panel
        }
    }

    class MouseShaper implements MouseListener
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
        // calculate if the GREEN LIGHT button was clicked
        double check_green = ((mx - cx)*(mx - cx))/(rx*rx) + ((my - cy)*(my - cy))/(ry*ry);
        if (check_green <= 1) // if GREEN LIGHT was clicked
        {   
            stopped = false; // car no longer stopped (if it was)
            fast = true; // car should be going fast 
        }

        // YELLOW LIGHT
        // approx area for the YELLOW LIGHT button
        cx = 375;
        cy = 115;
        rx = 25;
        ry = 25;
        // calculate if the Yellow LIGHT button was clicked
        double check_yellow = ((mx - cx)*(mx - cx))/(rx*rx) + ((my - cy)*(my - cy))/(ry*ry);
        if (check_yellow <= 1) // if Yellow LIGHT was clicked
        {
            stopped = false; // car no longer stopped (if it was)
            fast = false; // car should not be going fast 
        }

        // RED LIGHT
        // approx area for the RED LIGHT button
        cx = 375;
        cy = 45;
        rx = 25;
        ry = 25;
        // calculate if the RED LIGHT button was clicked
        double check_red = ((mx - cx)*(mx - cx))/(rx*rx) + ((my - cy)*(my - cy))/(ry*ry);
        if (check_red <= 1)
        {
            stopped = true; // car should stop
        }

    }
    // empty methods since they were unused but needed to be implemented
    public void mouseEntered(MouseEvent m){}
    public void mouseExited(MouseEvent m){}
    public void mousePressed(MouseEvent m){}
    public void mouseReleased(MouseEvent m){}


    }
    public void keyPressed(KeyEvent e) // deals with buttons pressed on the keyboard
    {
        if (e.getKeyChar() == 's') // RED LIGHT: when s is pressed, car should stop
        {
            stopped = true;
        }

        if (e.getKeyChar() == 'y') // YELLOW LIGHT: when y is pressed, car should go slow
        {
            stopped = false;
            fast = false;
        }

        if (e.getKeyChar() == 'g') // GREEN LIGHT: when g is pressed, car should go fast
        {
            stopped = false;
            fast = true;
        }
        if (e.getKeyChar() == 'r') // RESET: when r is pressed, car counter should reset to 0
        {
            traffic_count = 0;
        }
    }

    // empty methods since they were unused but needed to be implemented
    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
}


