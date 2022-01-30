import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Button extends javax.swing.JButton implements ActionListener {
     public  Button(String text, Color textColor, Color backColor,  int x, int y, int width, int height) {
         this.setText(text);
         this.setForeground(textColor);
         this.setBackground(backColor);
         this.setBounds(x,y,width,height);
         this.setFocusable(false);
     }



    @Override
    public void actionPerformed(ActionEvent e) {}
}
