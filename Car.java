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
        private boolean isFast; // the car's speed
        private boolean isStopped; // whether the car is moving
        private JLabel carMovementStatus;
        Random rand = new Random();

        // each car has a new random colour for its wheels, base and front 
        private Color wheelColour = new Color(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256));
        private Color baseColour = new Color(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256));
        private Color frontColour = new Color(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256));


        public Car(int x, int y, boolean f, boolean s, JLabel l) // Car constructor
        {   
            // sets up the car's x and y positions
            this.x = x;
            this.y = y;

            // car's speed, movement, and random colours
            this.isFast = f;
            this.isStopped = s;

            // traffic label 
            this.carMovementStatus = l;
        }

        // getters for coordinates
        public int getX(){ return x;}
        public int getY(){ return y;}

        // getters for movement of car
        public void setStopped(boolean status){
            this.isStopped = status;
        }
        public void setFast(boolean status){
            this.isFast = status;
        }
        //setters for movement of car


        public void draw(Graphics g) {
            if(isFast && !isStopped) // GREEN LIGHT
            {
                x += dx*4; // move very fast (4x speed)
                carMovementStatus.setText("Go!"); // update the status label
                carMovementStatus.setForeground(Color.GREEN); // update the colour of the status label
            }

            else if(!isFast && !isStopped) // YELLOW LIGHT
            {
                x += dx; // move slower (1x speed)
                carMovementStatus.setText("Slow Down!"); // update the status label
                carMovementStatus.setForeground(Color.YELLOW); // update the colour of the status label
            }
            else // RED LIGHT (dont move)
            {
                carMovementStatus.setText("Stop!"); // update the status label
                carMovementStatus.setForeground(Color.RED); // update the colour of the status label
            }
            
            if (x > 900) // for when the car moves off the screen (off right side), it should reappear at the start (left side)
            {
            x = -50;

            // since its a "new car" that is appearing, it will have new set colours
            wheelColour = new Color(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256));
            baseColour = new Color(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256));
            frontColour = new Color(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256));

            // new car seen, therefore we update the traffic count
            Traffic.updateTrafficCount();
            }

            Graphics2D g2d = (Graphics2D) g;

            // creating the wheels of the car
            g2d.setColor(wheelColour); // wheels both have the same colour
            g2d.fillOval(x,360,40,40);
            g2d.fillOval(x - 100,360,40,40);

            // creating the base of the car
            g2d.setColor(baseColour);
            g.fillRect(x-100, 300, 110, 80);

            // creatinig the front of the car (and the windshield)
            g2d.setColor(frontColour);
            g.fillRect(x, 310, 60, 65);
            g2d.setColor(wheelColour);
            g.fillRect(x + 10, 320, 50, 30);
        }
    }