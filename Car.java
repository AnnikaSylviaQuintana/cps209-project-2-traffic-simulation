import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JLabel;
import java.util.Random;

public class Car
    {
        private int x; // the car's x position
        private int y; // the car's y position
        private int dx = 1; // the change in x (for when the car moves)
        private boolean fast; // the car's speed
        private boolean stopped; // whether the car is moving
        private JLabel stat;
        Random rand = new Random();

        // each car has a new random colour for its wheels, base and front 
        private Color wheelColour = new Color(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256));
        private Color base_colour = new Color(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256));
        private Color front_colour = new Color(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256));


        public Car(int x, int y, boolean fast, boolean stopped, JLabel s) // Car constructor
        {   
            // sets up the car's x and y positions
            this.x = x;
            this.y = y;

            // car's speed, movement, and random colours
            this.fast = fast;
            this.stopped = stopped;

            // traffic label 
            this.stat = s;
        }

        // getters for coordinates
        public int getX(){ return x;}
        public int getY(){ return y;}

        // getters for movement of car
        public void setStopped(boolean status){
            this.stopped = status;
        }
        public void setFast(boolean status){
            this.fast = status;
        }
        //setters for movement of car


        public void draw(Graphics g) {
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
            wheelColour = new Color(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256));
            base_colour = new Color(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256));
            front_colour = new Color(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256));

            // new car seen, therefore we update the traffic count
            Traffic.updateTrafficCount();
            }

            Graphics2D g2d = (Graphics2D) g;

            // creating the wheels of the car
            g2d.setColor(wheelColour); // wheels both have the same colour
            g2d.fillOval(x,360,40,40);
            g2d.fillOval(x - 100,360,40,40);

            // creating the base of the car
            g2d.setColor(base_colour);
            g.fillRect(x-100, 300, 110, 80);

            // creatinig the front of the car (and the windshield)
            g2d.setColor(front_colour);
            g.fillRect(x, 310, 60, 65);
            g2d.setColor(wheelColour);
            g.fillRect(x + 10, 320, 50, 30);
        }
    }