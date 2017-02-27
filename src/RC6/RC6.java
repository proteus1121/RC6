/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RC6;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

/**
 *
 * @author Артем
 */
public class RC6 extends JFrame{

    /**
     * @param args the command line arguments
     */
    public static String dopoln(String text){
    int summ = text.length()%32;
        if(summ!=0){
        int kol = 32 - summ;
        for(int f = 0;f< kol;f++){
        text = text + " ";
        }
        }
        return text;
    }
   static JTextArea isch = new JTextArea();
   static JTextArea rez = new JTextArea();
   static JTextArea keyPane = new JTextArea();
   //static String[] petStrings = { "Cript", "Decript" };
   static JButton apply = new JButton("Apply");
   static JButton rebfresh = new JButton("Reply");
   static JRadioButton petList1;
   static JRadioButton petList2;
    public RC6(){
        
       super("RC6");
       petList1 = new JRadioButton ("Cript");
       petList2 = new JRadioButton ("Decript");
       ButtonGroup group = new ButtonGroup();
       group.add(petList1);
       group.add(petList2);
    setBounds(100, 100, 400, 400); 
               this.rootPane. setLayout(new BoxLayout(this.rootPane, BoxLayout.PAGE_AXIS));                   
        this.rootPane.add(isch);
        this.rootPane.add(keyPane);
        JPanel p = new JPanel();
        p.add(petList1);
        p.add(petList2);
        this.rootPane.add(p);
        this.rootPane.add(apply);
        this.rootPane.add(rebfresh);
        this.rootPane.add(rez);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
                                                   
                                                    
  }
   static String text = "artem ishchenko artem ishchenko ";
   static String key = "qwerdfgdfgfdgfgdgdgfgdgfdgfdgfdg";
                   static   byte[] textbytes;
    public static void main(String[] args) {
        RC6 a = new RC6();
        a.setVisible(true);
        isch.setText(text);
        keyPane.setText(key);
        rebfresh.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                   String time;
                    if(petList1.isSelected()){
                        time = isch.getText();
                        isch.setText(rez.getText());
                        rez.setText("");
                    }
                     if(petList2.isSelected()){
                        time = isch.getText();
                        isch.setText(rez.getText());
                        rez.setText("");
                    }
               }
        });
        apply.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
              text= isch.getText();
              key = keyPane.getText();
              text =  dopoln(text);
              key =  dopoln(key);
              
              

        ArrayList keyLast = new ArrayList();
        for (int i = 0; i < key.getBytes().length; i++) {
            keyLast.add((byte) (key.getBytes()[i]));
        }
        ArrayList k = new ArrayList();
        for (int i = 0; i < 44; i++) {
            k.add((byte) (Math.E + i * ((1 + Math.sqrt(5)) / 2)));
        }

        byte A1 = 0;
        byte B1 = 0;
        int I = 0;
        int j = 0;
        for (int x = 0; x < 44 * 3; x++) {
            k.set(I, (byte) (((byte) k.get(I) + A1 + B1) << 3));
            B1 = (byte) k.get(I);
            I = (I + 1) % 44;
            keyLast.set(j, (byte) ((((byte) keyLast.get(j)) + A1 + B1) << (A1 + B1)));
            A1 = (byte) k.get(j);
            j = (j + 1) % keyLast.size();
        }
            //System.err.println("Index "+petList.getSelectedIndex());
              if(petList1.isSelected()){
                   textbytes = text.getBytes();
                  String a = new String(cript(textbytes, keyLast), StandardCharsets.UTF_8);
                  textbytes = cript(text.getBytes(), keyLast);
                  System.err.println("Cript");
              rez.setText(a);}
              if(petList2.isSelected()){
                  
                  String a = new String(decript(textbytes, keyLast), StandardCharsets.UTF_8);
                  textbytes = decript(textbytes, keyLast);
                   System.err.println("Deript");
              rez.setText(a);
              
              }
               }
          });
       
        //System.err.println(text.length()%32);
       
//DECRIPT
//cript(textbytes, keyLast);
       

        System.err.println(" ");
    }

    public static byte[] T(byte[] X) {
        byte[] z = new byte[X.length];
        for (int i = 0; i < X.length; i++) {
            z[i] = (byte) (((X[i]) * (2 * (X[i]) + 1)) % (Math.pow(2, 32)));
        }
        return z;
    }

    public static byte [] decript(byte [] shifrotext, ArrayList keyLast) {
        System.err.println("");
        int cur = 0;
        byte shifr[] = shifrotext;
        ArrayList decripttext = new ArrayList();
        byte deshifr[] = null;
        for (int i22 = 0; i22 < shifrotext.length * 8 / 128; i22++) {
            byte[] A = new byte[4];
            byte[] B = new byte[4];
            byte[] C = new byte[4];
            byte[] D = new byte[4];
            System.arraycopy(shifr, cur, A, 0, 4);
            cur = cur + 4;
            System.arraycopy(shifr, cur, B, 0, 4);
            cur = cur + 4;
            System.arraycopy(shifr, cur, C, 0, 4);
            cur = cur + 4;
            System.arraycopy(shifr, cur, D, 0, 4);
            cur = cur + 4;
            
            for (int i = 19; i >= 0; i--) {
      // System.err.println(i); 

                byte[] Bnew = new byte[A.length];
                for (int l = 0; l < A.length; l++) {
                    Bnew[l] = (byte) (A[l]);
                }
                byte[] Dnew = new byte[C.length];
                for (int l = 0; l < C.length; l++) {
                    Dnew[l] = (byte) (C[l]);
                }

                int z = byteArrayToInt(T(Bnew)) << 5;
                int z1 = byteArrayToInt(T(Dnew)) << 5;

    //ArrayList alast = new ArrayList();
  //
                // byte [] l1 = {(byte)keyLast.get(i-1)};
                byte[] l2 = {(byte) keyLast.get(i)};
                int a = (byteArrayToInt(D) - byteArrayToInt(l2)) >> z1 //-   byteArrayToInt(l1)
                        ;
                a = (a ^ (z));
  // alast.add(a);

//byte [] l2 = {(byte)keyLast.get(i)};
                int a23 = (byteArrayToInt(B) - byteArrayToInt(l2)) >> z;
                a23 = (a23 ^ (z1));
          //-    byteArrayToInt(l2)

                A = intToByteArray(a);
                B = Bnew;
                C = intToByteArray(a23);
                D = Dnew;
    //System.err.println(i+" "+result2.length);

//System.err.println(i+" "+A.length);
            }
            for (int l = 0; l < 4; l++) {
                decripttext.add(A[l]);
            }
            for (int l = 0; l < 4; l++) {
                decripttext.add(B[l]);
            }
            for (int l = 0; l < 4; l++) {
                decripttext.add(C[l]);
            }
            for (int l = 0; l < 4; l++) {
                decripttext.add(D[l]);
            }
        }
        deshifr = new byte[decripttext.size()];

        for (int i = 0; i < decripttext.size(); i = i + 4) {
            deshifr[i] = (byte) decripttext.get(i);
            deshifr[i + 1] = (byte) decripttext.get(i + 1);
            deshifr[i + 2] = (byte) decripttext.get(i + 2);
            deshifr[i + 3] = (byte) decripttext.get(i + 3);
        }
        System.err.println("Lenght decript "+deshifr.length);
        String str = new String(deshifr, StandardCharsets.UTF_8);

        System.err.print(str);
        return deshifr;
    }

    public static byte [] cript(byte[] textbytes, ArrayList keyLast) {
        int cur = 0;
        byte shifr[] = null;
        ArrayList shifrotext = new ArrayList();

        System.err.println("Lenght " + textbytes.length * 8);
        for (int i22 = 0; i22 < textbytes.length * 8 / 128; i22++) {

            byte[] A = new byte[4];
            byte[] B = new byte[4];
            byte[] C = new byte[4];
            byte[] D = new byte[4];

            System.arraycopy(textbytes, cur, A, 0, 4);
            cur = cur + 4;
            System.arraycopy(textbytes, cur, B, 0, 4);
            cur = cur + 4;
            System.arraycopy(textbytes, cur, C, 0, 4);
            cur = cur + 4;
            System.arraycopy(textbytes, cur, D, 0, 4);
            cur = cur + 4;

            for (int i = 0; i < 20; i++) {
      //System.err.println(i); 

                byte[] Anew = new byte[B.length];
                for (int l = 0; l < B.length; l++) {
                    Anew[l] = (byte) (B[l]);
                }
                byte[] Cnew = new byte[D.length];
                for (int l = 0; l < D.length; l++) {
                    Cnew[l] = (byte) (D[l]);
                }

                int z = byteArrayToInt(T(Anew)) << 5;
                int z1 = byteArrayToInt(T(Cnew)) << 5;

    //ArrayList alast = new ArrayList();
                int a = (byteArrayToInt(A) ^ (z));

                //
                byte[] l1 = {(byte) keyLast.get(i)};
                a = (a << z1);
                a = a + byteArrayToInt(l1) // +  byteArrayToInt(l1) 
                        ;
  // alast.add(a);

                int a23 = (byteArrayToInt(C) ^ (z1));
                byte[] l2 = {(byte) keyLast.get(i)};
                a23 = (a23 << z);
                a23 = a23 + byteArrayToInt(l2) //+  byteArrayToInt(l2)
                        ;

                A = Anew;
                B = intToByteArray(a23);
                C = Cnew;
                D = intToByteArray(a);

            }
            for (int l = 0; l < 4; l++) {
                shifrotext.add(A[l]);
            }
            for (int l = 0; l < 4; l++) {
                shifrotext.add(B[l]);
            }
            for (int l = 0; l < 4; l++) {
                shifrotext.add(C[l]);
            }
            for (int l = 0; l < 4; l++) {
                shifrotext.add(D[l]);
            }
        }
        shifr = new byte[shifrotext.size() ];

        for (int i = 0; i < shifrotext.size(); i = i + 4) {
            shifr[i] = (byte) shifrotext.get(i);
            shifr[i + 1] = (byte) shifrotext.get(i + 1);
            shifr[i + 2] = (byte) shifrotext.get(i + 2);
            shifr[i + 3] = (byte) shifrotext.get(i + 3);
        }
        String str2 = new String(shifr, StandardCharsets.UTF_8);

        System.err.print(str2);
        return shifr;
    }

    public static int byteArrayToInt(byte[] bytes) {
        int result = 0;
        int l = bytes.length - 1;
        for (int i = 0; i < bytes.length; i++) {
            if (i == l) {
                result += bytes[i] << i * 8;
            } else {
                result += (bytes[i] & 0xFF) << i * 8;
            }
        }
        return result;
    }

    public static byte[] intToByteArray(int value) {
        byte[] result = new byte[4];
        for (int i = 0; i < 4; i++) {
            result[i] = (byte) (value & 0xFF);
            value >>>= 8;
        }
        return result;
    }
}
