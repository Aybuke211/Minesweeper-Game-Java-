package MayinTarlasiGui.Src;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.Math;

public class MayinTarlasi implements MouseListener {
    int titleSize = 70;
    boolean cheat = true;
    JFrame frame;

    Btn[][] board = new Btn[9][9]; // Grid size ile aynı olmak zorunda
    int openButton;
    public MayinTarlasi(){
        openButton = 0;
        frame = new JFrame("Mayın Tarlası");
        frame.setSize(500,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(new GridLayout(9,9));
        
        for (int row = 0; row<board.length; row++){
            for(int col = 0; col<board[0].length; col++){
                Btn b = new Btn(row, col);
                frame.add(b);
                b.addMouseListener(this);
                board[row][col] = b;
            }
        }
        generateMine();
        updateCount();
        //print();
        //printMine();
        

        frame.setVisible(true);
    }
    public void generateMine(){
        int i = 0;
        while(i<10){ //10 tane mayın üretilecek
            int randRow = (int) (Math.random() * board.length);
            int randCol = (int) (Math.random() * board[0].length);
            while(board[randRow][randCol].isMine()){ // Mayın var mı yok mu diye bakılır yoksa üretilir
                randRow = (int) (Math.random() * board.length);
                randCol = (int) (Math.random() * board[0].length);
            }
            board[randRow][randCol].setMine(true);
            i++;

        }
    }

    public void print(){
        for (int row = 0; row<board.length; row++){
            for(int col = 0; col<board[0].length; col++){
                if(board[row][col].isMine()){
                    board[row][col].setIcon(new ImageIcon("mine.png"));
                }
                else{
                    board[row][col].setText(board[row][col].getCount()+""); // burdaki +"" int olan count'u string'e çevirir.
                    board[row][col].setEnabled(false);
                }
            }
        }
    }

    public void printMine() {
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[0].length; col++) {
				if (board[row][col].isMine()) {
					board[row][col].setIcon(new ImageIcon("mine.png"));
				}
			}
		}
	}

    

    public void updateCount(){
        for (int row = 0; row<board.length; row++){
            for(int col = 0; col<board[0].length; col++){
               if(board[row][col].isMine()){
                counting(row,col);
               }
            }
        }
    }

    public void counting(int row,int col){
        for(int i = row-1; i<= row+1; i++){
            for(int k = col-1; k<=col+1; k++){
                try{
                    int value = board[i][k].getCount(); //getCount:Etraftaki mayın sayısı
                    board[i][k].setCount(++value);
                }
                catch(Exception e){

                }
            }
        }
    }

    public void open(int row, int col){
        if (row < 0 || row >= board.length || col < 0 || col >= board[0].length || board[row][col].getText().length() > 0 // getText.lenght: içinde yazı yoksa yani butona tıklanmamışsa
				|| board[row][col].isEnabled() == false) {  // tıklanan yerler enable yapılır.
                    return; //içine bir şey yazmaz(void)
        }
        else if(board[row][col].getCount() != 0){ // tıklanan butonun etrafındaki mayın sayısı 0'dan farklı ise 
            board[row][col].setText(board[row][col].getCount()+"");
            board[row][col].setEnabled(false);
            openButton++;
        }
        else{
            openButton++;
            board[row][col].setEnabled(false);
            open(row-1,col);
            open(row+1,col);
            open(row,col-1);
            open(row,col+1);
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        Btn b = (Btn) e.getComponent();
        if(e.getButton() == 1){
            //System.out.println("sol tık");
            if(b.isMine()){
                JOptionPane.showMessageDialog(frame,"Mayına bastınız oyun bitti!!");
                print();
            }
            else{
                open(b.getRow(),b.getCol());
                if(openButton == (board.length * board[0].length)-10){
                    JOptionPane.showMessageDialog(frame,"Tebrikler oyunu kazandınız :)");
                    print();
                }
            }

        }
        else if(e.getButton() == 3){
            //System.out.println("sağ tık");
            if(!b.isFlag()){
                b.setIcon(new ImageIcon("flag.png"));
                b.setFlag(true); // true yap
            }
            
            else{
                b.setIcon(null);
                b.setFlag(false);
            }
            
        }
    }

    
    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'mousePressed'");
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'mouseReleased'");
    }
    @Override
    public void mouseEntered(MouseEvent e) {
        Btn b = (Btn) e.getComponent();
        if(cheat == true){
            b.setEnabled(false);
            b.setText(b.getCount()+"");
            if(b.isMine()){
                b.setIcon(new ImageIcon("mine.png"));
            }
        }

        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'mouseEntered'");

    }
    @Override
    public void mouseExited(MouseEvent e) {
         Btn b = (Btn) e.getComponent();
         if(cheat== true){
            b.setEnabled(true);
            b.setText("");
            if(b.isMine()){
                b.setIcon(null);
            }
         }

        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'mouseExited'");
    }
    
    
}

