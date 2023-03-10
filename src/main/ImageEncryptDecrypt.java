package src.main.imageencryptdecrypt;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Label;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ImageEncryptDecrypt {
    static File file = null;

    static boolean isEncrypted(File file) {
        try {
            BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            image = ImageIO.read(file);
            if (image == null)
                return true;
        } catch (Exception e) {
            return true;
        }
        return false;
    }
    
    static void operation(int key, File file, boolean doEncrypt) {
        try {
            // encryption & decryption logic
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[fis.available()];
            fis.read(data);

            int i = 0;
            for (byte b : data) {
                data[i] = (byte) (b ^ key);
                i++;
            }

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data);
            fos.close();
            fis.close();

            String message;
            if (doEncrypt)
                message = "file Encrypted successfully. Go and Check your selected image path!";
            else
                message = "file Decrypted successfully. Go and Check your selected image path!";
            JOptionPane.showMessageDialog(null, message);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
		
	JFrame f=new JFrame();
        JPanel p1=new JPanel();
        JPanel p2=new JPanel();
        JPanel p3=new JPanel();
        JPanel p4=new JPanel();
        JPanel p5=new JPanel();
        JPanel p6=new JPanel();

        f.setTitle("Image Encryption/Decryption Tool");
        f.setSize(390,400);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
        Font fonts=new Font("Roboto",Font.ITALIC,18);
        JButton button1=new JButton();
        button1.setText("Enter Key To Encrypt/ Decrypt Image");
        button1.setFont(fonts);
        
        JTextField tf=new JTextField(15);
        tf.setHorizontalAlignment(JTextField.CENTER);
        tf.setFont(fonts);
        
        Font font=new Font("Roboto",Font.ITALIC,18);
        JButton cf=new JButton();
        cf.setText("Select Image");
        cf.setFont(font);
        
        // Encryption button
        JButton enc = new JButton();
        enc.setText("Encrypt");
        enc.setFont(font);

        // Decryption button
        JButton dec = new JButton();
        dec.setText("Decrypt");
        dec.setFont(font);
        
        Label l1,l2;  
        l1=new Label("To Decrypt image give the same key as given on Encryption time!");  
        l1.setBounds(50,130, 100,30);  
        l2=new Label("And select the same image!");  
        l2.setBounds(50,180, 100,30);  
        
        // button listeners
        cf.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            fc.addChoosableFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png"));
            fc.setAcceptAllFileFilterUsed(false);
            fc.showOpenDialog(null);
            file = fc.getSelectedFile();
        });

        enc.addActionListener(e -> {
            try {
                String val = tf.getText();
                int key = Integer.parseInt(val);
                if (file == null) {
                    JOptionPane.showMessageDialog(null, "please select a file!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (isEncrypted(file)) {
                        JOptionPane.showMessageDialog(null, "File is already Encrypted!");
                    } else {
                        operation(key, file, true);
                    }
                }

            } catch (NumberFormatException no_key) {
                JOptionPane.showMessageDialog(null, "key cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        dec.addActionListener(e -> {
            try {
                String val = tf.getText();
                int key = Integer.parseInt(val);
                if (file == null) {
                    JOptionPane.showMessageDialog(null, "please select a file!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (isEncrypted(file)) {
                        operation(key, file, false);
                    } else {
                        JOptionPane.showMessageDialog(null, "File is already Decrypted!");
                    }
                }

            } catch (NumberFormatException no_key) {
                JOptionPane.showMessageDialog(null, "key cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            }

        });
        
        p1.add(button1, BorderLayout.CENTER);
        p2.add(tf, BorderLayout.CENTER);
        p3.add(cf, BorderLayout.CENTER);
        p4.add(enc, BorderLayout.CENTER);
        p4.add(dec, BorderLayout.CENTER);
        p6.add(l1, BorderLayout.CENTER); 
        p6.add(l2, BorderLayout.CENTER);
        f.setLayout(new BoxLayout(f.getContentPane(), BoxLayout.Y_AXIS));
        
        f.add(p1);
        f.add(p2);
        f.add(p3);
        f.add(p4);
        f.add(p5);
        f.add(p6);
        f.setVisible(true);
    }
}
