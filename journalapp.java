import java.awt.event.*;
import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.swing.*;


public class journalapp {
    List<journalentry> pages=new ArrayList<>();
    DateTimeFormatter formatter=DateTimeFormatter.ofPattern("MMMM d, yyyy",Locale.ENGLISH);
    private int page;
    private String newpagetext;
    public journalapp(){
        //initialises data from files
        try{
            File g=new File("dates.txt");
            File f=new File("entrys.txt");
            f.createNewFile();
            g.createNewFile();
            Scanner myreader=new Scanner(f);
            Scanner myreader1=new Scanner(g);
            
            while (myreader.hasNextLine()) { 
                String temp=myreader.nextLine();
                String temp1=myreader1.nextLine();
                pages.add(new journalentry(temp1,temp));
            }
            myreader.close();
            myreader1.close();
            System.out.println(pages);

        
    
        } catch(Exception e){
            System.out.println("error");
        }
        //sets up gui
        page=pages.size();
        JLabel pagenum=new JLabel(String.valueOf(page+1)+"/"+String.valueOf(pages.size()+1));
        JFrame frame=new JFrame("Journal App");
        JTextField textField=new JTextField();
        Date date=new Date();
        JLabel title=new JLabel(String.valueOf(date));
        JButton backButton=new JButton("Back");
        JButton nextButton=new JButton("Next");
        JButton saveButton=new JButton("Save");
        JButton editButton=new JButton("Edit");
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(200,200,600,900);

        pagenum.setBounds(10,50,100,50);
        title.setBounds(10,10,580,80);
        textField.setBounds(10,110,560,580);
        backButton.setBounds(10,700,200,100);
        saveButton.setBounds(370,700,200,100);
        nextButton.setBounds(370,700,200,100);
        editButton.setBounds(470,60,100,50);
        nextButton.setVisible(false);
        editButton.setVisible(false);
        frame.add(pagenum);
        frame.add(editButton);
        frame.add(saveButton);
        frame.add(backButton);
        frame.add(textField);
        frame.add(nextButton);
        frame.add(title);
        System.out.println(pages.size());
        if(pages.size()==0){
            backButton.setVisible(false);
        }
        frame.setVisible(true);
        saveButton.addActionListener(new ActionListener(){
            //saves changes after editing entrys
            @Override
            public void actionPerformed(ActionEvent e) {
                textField.setEditable(false);
                if(page==pages.size()){
                    
                    Date date=new Date();
                    pages.add(new journalentry(String.valueOf(date), textField.getText()));
                    
                    newpagetext="";
                    
                    
                }else{
                    pages.get(page).setEntry(textField.getText());
                }
                saveButton.setVisible(false);
                nextButton.setVisible(true);

            }

        });
        backButton.addActionListener(new ActionListener(){
            //lets users go back and see old entrys
            @Override
            public void actionPerformed(ActionEvent e) {
                //saves new page text tempporarily
                saveButton.setVisible(false);
                nextButton.setVisible(true);
                if(page==pages.size()){
                    newpagetext=textField.getText();
                }
                page--;
                pagenum.setText(String.valueOf(page+1)+"/"+String.valueOf(pages.size()+1));
                textField.setEditable(false);
                textField.setText(pages.get(page).getEntry());
                title.setText(pages.get(page).getDate());
                if(page==0){
                    backButton.setVisible(false);
                }
                System.out.println("pages: "+pages.size()+" page number: "+page);
                editButton.setVisible(true);
            }

        });

        nextButton.addActionListener(new ActionListener(){
            //lets users go foward and see entries
            @Override
            public void actionPerformed(ActionEvent e) {
                backButton.setVisible(true);
                page++;
                pagenum.setText(String.valueOf(page+1)+"/"+String.valueOf(pages.size()+1));
                if(page==pages.size()){
                    textField.setEditable(true);
                    textField.setText(newpagetext);
                    nextButton.setVisible(false);
                    saveButton.setVisible(true);
                    editButton.setVisible(false);
                }else{
                    textField.setEditable(false);
                    textField.setText(pages.get(page).getEntry());
                    title.setText(pages.get(page).getDate());
                }
                System.out.println("pages: "+pages.size()+" page number: "+page);
                
            }

        });
        editButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                //lets users edit old entries
                textField.setEditable(true);
                nextButton.setVisible(false);
                saveButton.setVisible(true);
            }

        });
        frame.addWindowListener(new WindowAdapter() {
            //when window is closed save all data to text files
            public void windowClosing(WindowEvent d){
                try{
                    System.out.println("writeing data to files");
                    FileWriter myWriter1=new FileWriter("dates.txt");
                    FileWriter myWriter2=new FileWriter("entrys.txt");
                    

                    //clear habits and values
                    //f.clear();
                    for(journalentry entry:pages){
                        myWriter1.write(entry.getDate()+"\n");
                        myWriter2.write(entry.getEntry()+"\n");

                    }
                    myWriter1.close();
                    myWriter2.close();
                }catch(Exception f){
                    System.out.println("error writing to file");
                }
            }
        });


    }
    public static void main(String[] args) {
        new journalapp();
    }   
}
