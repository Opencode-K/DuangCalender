package curriculum_design;

import java.awt.BorderLayout;					
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

//����������CalendarPad
public class CalendarPad extends JFrame implements MouseListener{
	
   int year,month,day;
   Hashtable hashtable;             
   File file;                       
   static JTextField showDay[];             
   JLabel title[];                   
   Calendar calendar;
   int week; 
   NotePad notepad=null;             
   Month changemonth;
   Year  changeyear;
   String ����[]={"��","һ","��","��","��","��","��"};
   JPanel leftPanel,rightPanel;    
 
   //�����Ƕ���CalendarPad����
   public  CalendarPad(int year,int month,int day)
   { 
	  super("Duang�������±�");				//���ô������
     leftPanel=new JPanel();
     JPanel leftCenter=new JPanel();
     JPanel leftNorth=new JPanel();
     leftCenter.setLayout(new GridLayout(7,7));   
                                                  
     rightPanel=new JPanel();
     this.year=year;
     this.month=month;
     this.day=day;
     changeyear=new Year(this);
     changeyear.setYear(year);
     changemonth=new Month(this);
     changemonth.setMonth(month);
  
     title=new JLabel[7];                         //������ʾ���ڱ�ǩ
     showDay=new JTextField[42];                   
     for(int j=0;j<7;j++)                         
       {
         title[j]=new JLabel();
         title[j].setText(����[j]);				//������ʾ���ڱ�ǩ
         title[j].setBorder(BorderFactory.createRaisedBevelBorder());				//����߿�Ϊб��߿�͹��
         leftCenter.add(title[j]);				//��ʾ���ڱ�ǩ
       } 
     title[0].setForeground(Color.red);				//��������ʾΪ��ɫ
     title[6].setForeground(Color.red);				//��������ʾΪ��ɫ

     for(int i=0;i<42;i++)                        
       {
         showDay[i]=new JTextField();
         showDay[i].addMouseListener(this);			//���������
         showDay[i].setEditable(false);				//����Ϊ���ɱ༭��ǩ
         leftCenter.add(showDay[i]);
       }
         
     calendar=Calendar.getInstance();
     Box box=Box.createHorizontalBox();          
     box.add(changeyear);				//��Ӹı���ؼ�
     box.add(changemonth);				//��Ӹı��¿ؼ�
     leftNorth.add(box);					//��λ�ı����±�ǩ
     leftPanel.setLayout(new BorderLayout());
     leftPanel.add(leftNorth,BorderLayout.NORTH);
     leftPanel.add(leftCenter,BorderLayout.CENTER);
     leftPanel.add(new Label("201403041020   XXX   201403041050   XXX"),
                  BorderLayout.SOUTH) ;
     leftPanel.validate();
     Container con=getContentPane();
     JSplitPane split=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                                     leftPanel,rightPanel);
     
     con.add(split,BorderLayout.CENTER);
     con.validate();
    
     hashtable=new Hashtable();
     file=new File("�������±�.txt");				//�½�"�������±�.txt"�ļ�
      if(!file.exists())						//�ж��ļ��Ƿ����
      {
       try{
           FileOutputStream out=new FileOutputStream(file);
           ObjectOutputStream objectOut=new ObjectOutputStream(out);
           objectOut.writeObject(hashtable);
           objectOut.close();
           out.close();
          }
       catch(IOException e)
          {
          }
      } 
    
     notepad=new NotePad(this);                                      
     rightPanel.add(notepad);
     set_calendar(year,month);
     addWindowListener(new WindowAdapter()				//ע�������
                    { public void windowClosing(WindowEvent e)
                       {
                         System.exit(0);
                       }
                    });
    setVisible(true);				//���������ɼ�
    setBounds(500,100,600,285);				//����������С��λ��
    validate();
   }
   
   /*����Ϊset_calendar����
    * ʵ�ֲ�ͬ�·ݵ�������ʾ
    */
  public void set_calendar(int year,int month)
   {
     calendar.set(year,month-1,1);              
     
     week=calendar.get(Calendar.DAY_OF_WEEK)-1;
     if(month==1||month==2||month==3||month==5||month==7
                        ||month==8||month==10||month==12)
        {   arraynum(week,31);         
        }
     else if(month==4||month==6||month==9||month==11)
        {  arraynum(week,30);
        }
     else if(month==2)
        {   if((year%4==0&&year%100!=0)||(year%400==0))  
           {   arraynum(week,29);
           }
         else
           {   arraynum(week,28);
           }
       }
   } 
 
  /*������arraynum����
   * ��ʾ��������ǩ�е���ɫ��ʾ
   */
 public void arraynum(int week,int month)
   {
      for(int i=week,n=1;i<week+month;i++)
             {   showDay[i].setText(""+n);
           
               if(n==day)
                 {  
            	   showDay[i].setForeground(Color.black); 
            	   showDay[i].setBackground(Color.blue);
                   showDay[i].setFont(new Font("TimesRoman",Font.BOLD,20));
                 }
               else
                 {   
            	   showDay[i].setFont(new Font("TimesRoman",Font.BOLD,12));
                   showDay[i].setBackground(Color.white);
                   showDay[i].setForeground(Color.black);
                 }
               if(i%7==6)				//��������Ϊ������ʾΪ��ɫ
                 {   showDay[i].setForeground(Color.red);  
                 }
               if(i%7==0)				//��������Ϊ������ʾΪ��ɫ
                 {  
            	   showDay[i].setForeground(Color.red);  
                 }
               n++; 
             }
       for(int i=0;i<week;i++)
             {  showDay[i].setText("");
             }
       for(int i=week+month;i<42;i++)
             {   
    	   showDay[i].setText("");
             }
   }

 //����getYear��setYear��getMonth��setMonth��getDay��setDay��getHashtable��getFile�������
 public int getYear()				
   {   
	 return year;
   }
 
 public void setYear(int y)
   {   
	 year=y;
     notepad.setYear(year);
   }
 
 public int getMonth()
   {  
	 return month;
   }
 
 public void setMonth(int m)
   {  
	 month=m;
      notepad.setMonth(month); 
   }
 
 public int getDay()
   {  
	 return day;
   }
 
 public void setDay(int d)
   {   
	 day=d;
       notepad.setDay(day);
   }
 
 public Hashtable getHashtable()
   {   
	 return hashtable;
   }
 
 public File getFile()
   {  
	 return file;
   }
 
 /*������mouseClicked�������� Javadoc��
  * ���ö����ǩ
  * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
  */
  public void mouseClicked(MouseEvent e)
   {
	 JTextField source=(JTextField)e.getSource();
     try{
        day=Integer.parseInt(source.getText());
        notepad.setDay(day);
        notepad.setIFLine(year,month,day);
        notepad.setText(null);
        notepad.lookCalendar(year,month,day);
       } 
     catch(Exception ee)
     {
     }
   }
 
 //������������¼�

 public void mousePressed(MouseEvent e)             
   { 
   }

 public void mouseReleased(MouseEvent e)
   {
   }
 
 public void mouseEntered(MouseEvent e)
   {
   }
 
 public void mouseExited(MouseEvent e)
   {
   }
 
/*����NotePad��
 * ʵ�ֶԼ��±�������������
 * �½������ť�ͱ�ǩ�Ա���ɶԼ��±��ı����ɾ���Լ����½��ļ��±��ı���
 */
class NotePad extends JPanel implements ActionListener
{ 
	JTextArea text;              
	JButton save_text,rm_text;
    Hashtable table;             
    JLabel IFLine;               
    int year,month,day;          
    File file;                   
    CalendarPad calendar;
    
    public  NotePad(CalendarPad calendar)
   {
     this.calendar=calendar;
     year=calendar.getYear();
     month=calendar.getMonth();
     day=calendar.getDay();
     table=calendar.getHashtable();
     file=calendar.getFile();
     IFLine=new JLabel(""+year+"��"+month+"��"+day+"��",JLabel.CENTER);
     IFLine.setFont(new Font("TimesRoman",Font.BOLD,16));
     IFLine.setForeground(Color.blue);
     text=new JTextArea(5,10);				//�����ı��ռ�Ĵ�С
     save_text=new JButton("������־") ;				//�½���������־����ť
     rm_text=new JButton("ɾ����־") ;					//�½���ɾ����־����ť
     save_text.addActionListener(this);					//ע�������
     rm_text.addActionListener(this);						
     setLayout(new BorderLayout());
     JPanel pSouth=new JPanel();       
     add(IFLine,BorderLayout.NORTH);
     pSouth.add(save_text);
     pSouth.add(rm_text);
     add(pSouth,BorderLayout.SOUTH);
     add(new JScrollPane(text),BorderLayout.CENTER);
   }
    
    /*������actionPerformed����
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * ��õ����������־���͡�ɾ����־��������ť���¼�
     */
 public void actionPerformed(ActionEvent e)
   {
     if(e.getSource()==save_text)
      {   
    	 save_text(year,month,day);
      }
     else
    	 if(e.getSource()==rm_text)
      { 
    		 rm_text(year,month,day);
      }
   }
 
  public void setYear(int year)
  {   
	  this.year=year;
  }
  
 public int getYear()
  {    
	 return year;
  }
 
 public void setMonth(int month)
  {   
	 this.month=month;
  } 
 
  public int getMonth()
  {  
	  return month;
  } 
  
  public void setDay(int day)
  {   
	  this.day=day;
  }
  
  public int getDay()
   {   
	  return day;
   }
  
  /*������setIFLine����
   * �趨��Ϣ����ʾѡ�е�����
   */
 public void setIFLine(int year,int month,int day)
   {  
	 IFLine.setText(""+year+"��"+month+"��"+day+"��");
   }
 
  public void setText(String s)
   {  
	  text.setText(s);
   }
  
  /*������lookCalendar����
   * ͨ��FileInputStream��ObjectInputStream��������ı����г־ô洢
   * ObjectInputStream���ڻָ���ǰ���л��Ķ���
   */
  public void lookCalendar(int year,int month,int day)
   {  
	  String key=""+year+""+month+""+day;
       try
             {
              FileInputStream   inOne=new FileInputStream(file);
              ObjectInputStream inTwo=new ObjectInputStream(inOne);
              table=(Hashtable)inTwo.readObject();         
              inOne.close();
              inTwo.close();
             }
       catch(Exception ee)
             {
             }
       
       /*�������жϵ�ǰ�����Ƿ��м����ı����ж����
        * ͨ��containsKey��֤��ǰ�����Ƿ��м����ı�
        * ���������ʾ"��һ������־���أ��Ƿ�鿴?"��ͨ�������ťѡ���Ƿ�鿴
        * ���û���ı������ڼ��±���ֱ����ʾ�޼�¼
        */
       if(table.containsKey(key))
          {  String m=""+year+"��"+month+"��"+day+"��һ������־���أ��Ƿ�鿴?";
             int ok=JOptionPane.showConfirmDialog(this,m,"ѯ��",JOptionPane.YES_NO_OPTION,
                                               JOptionPane.QUESTION_MESSAGE);
                if(ok==JOptionPane.YES_OPTION)
                  {  text.setText((String)table.get(key));
                  }
                else
                  {   text.setText(""); 
                  }
          } 
       else
          {   text.setText("�޼�¼");
          } 
   }
 
  /*������save_text����
   * �����������־����ͨ��showConfirmDialog�����ᵯ��һ��ѯ��ȷ������
   * ͨ�����������ťʵ�ֱ���Ͳ������ı�
   */
  public void save_text(int year,int month,int day)
   {    
	  String Calendar_text=text.getText();
        String key=""+year+""+month+""+day;
        String m=""+year+"��"+month+"��"+day+"�Ƿ񱣴���־?";
        int ok=JOptionPane.showConfirmDialog(this,m,"ѯ��",JOptionPane.YES_NO_OPTION,
                                               JOptionPane.QUESTION_MESSAGE);
        if(ok==JOptionPane.YES_OPTION)
          {
           try
             {
              FileInputStream   inOne=new FileInputStream(file);
              ObjectInputStream inTwo=new ObjectInputStream(inOne);
              table=(Hashtable)inTwo.readObject();
              inOne.close();
              inTwo.close();
              table.put(key,Calendar_text);                                  
              FileOutputStream out=new FileOutputStream(file);
              ObjectOutputStream objectOut=new ObjectOutputStream(out);
              objectOut.writeObject(table);
              objectOut.close();
              out.close();
             }
           catch(Exception ee)
             {
             }
         }
   }
  
  /*������rm_text����
   *  �����ɾ����־����ͨ��showConfirmDialog�����ᵯ��һ��ѯ��ȷ������
   *  ͨ�����������ť��ʵ��ɾ���Ͳ�ɾ���ı�
   */
  public void rm_text(int year,int month,int day)
   {   
	  String key=""+year+""+month+""+day;
         if(table.containsKey(key))
          {    
        	 String m="ɾ��"+year+"��"+month+"��"+day+"�յ���־��?";
             int ok=JOptionPane.showConfirmDialog(this,m,"ѯ��",JOptionPane.YES_NO_OPTION,
                                               JOptionPane.QUESTION_MESSAGE);
              if(ok==JOptionPane.YES_OPTION)
              { 
              try
                {
                 FileInputStream   inOne=new FileInputStream(file);				
                 ObjectInputStream inTwo=new ObjectInputStream(inOne);
                 table=(Hashtable)inTwo.readObject();
                 inOne.close();
                 inTwo.close();
                 table.remove(key);                                        
                 FileOutputStream out=new FileOutputStream(file);
                 ObjectOutputStream objectOut=new ObjectOutputStream(out);
                 objectOut.writeObject(table);
                 objectOut.close();
                 out.close();
                 text.setText(null);
                }
               catch(Exception ee)
                {
                }
              }
          }
        else
          {  
        	String m=""+year+"��"+month+"��"+day+"����־��¼";
            JOptionPane.showMessageDialog(this,m,"��ʾ",JOptionPane.WARNING_MESSAGE);
          }
   }
}

/*����Year��
 * �½����µı�ǩ�����á����ꡱ�͡����ꡱ������
 * ͨ��actionPerformed������ö԰�ť�ĵ���¼���������Ӧ��ʾЧ���ı�
 */
class Year extends Box implements ActionListener
{  
	int year;                           
    JTextField showYear=null;           
    JButton nextyear,lastyear;
    CalendarPad calendar_2;
    
    /*������Year����
     * �½��ı���ǩ�������������
     * �½������������ꡱ�İ�ť
     */
    public Year(CalendarPad calendar_2)
    {  
    	super(BoxLayout.X_AXIS);        
        showYear=new JTextField(4);				//�½��ı���ǩ
        showYear.setForeground(Color.blue);				//�����ı���ǩ
        showYear.setFont(new Font("TimesRomn",Font.BOLD,14)); 				
        this.calendar_2=calendar_2;
        year=calendar_2.getYear();
        nextyear=new JButton("����");				//�½���ť
        lastyear=new JButton("����");
        add(lastyear);						//��ӱ�ǩ�Ͱ�ť
        add(showYear);
        add(nextyear);
        showYear.addActionListener(this);				//ע�������
        lastyear.addActionListener(this);
        nextyear.addActionListener(this);
  }
    
 public void setYear(int year)
  {  this.year=year;
     showYear.setText(""+year);
  }
 
 public int getYear()
  { 
	 return year;
  } 

/*��ʼ������actionPerformed
 * ��õ�������ꡱ�͡����ꡱ��ť����
 * �ı��������ʾ���
 *  ���� Javadoc��
 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
 */
 public void actionPerformed(ActionEvent e)
  {  
	 if(e.getSource()==lastyear)
      {  
		 year=year-1;
         showYear.setText(""+year);
         calendar_2.setYear(year);
         calendar_2.set_calendar(year,calendar_2.getMonth());
      }
    else 
    	if(e.getSource()==nextyear)
      { 
    		year=year+1;
    		showYear.setText(""+year);
    		calendar_2.setYear(year);
    		calendar_2.set_calendar(year,calendar_2.getMonth());
      }
    else 
    	if(e.getSource()==showYear)
      { 
    		try
            { 
    			year=Integer.parseInt(showYear.getText());
    			showYear.setText(""+year);
    			calendar_2.setYear(year);
    			calendar_2.set_calendar(year,calendar_2.getMonth());
            }
         catch(NumberFormatException ee)
            { 
        	 showYear.setText(""+year);
             calendar_2.setYear(year);
             calendar_2.set_calendar(year,calendar_2.getMonth());
            }
      }
  }   
}

/*����Month��
 * �½����¡���壬��ӡ����¡��͡����¡���ť
 */
 class Month extends Box implements ActionListener
{ 
	 int month;                           
	 JTextField showMonth=null;           
	 JButton nextmonth,lastmonth;
	 CalendarPad calendar_3;
	 
/*������Month����
 *�½���ǩ�Ͱ�ť
 *ʵ�ֶԡ��¡����л��������������ʾ
 *ͨ��actionPerformed������õ����ť�¼�
 *�ı���Ӧ��ʾЧ��
 */
	 public Month(CalendarPad calendar_3)
  {  
     super(BoxLayout.X_AXIS);        
     this.calendar_3=calendar_3;
     showMonth=new JTextField(2);
     month=calendar_3.getMonth();
     showMonth.setEditable(false);
     showMonth.setForeground(Color.blue);
     showMonth.setFont(new Font("TimesRomn",Font.BOLD,16)); 				//�����������ʾЧ��
     nextmonth=new JButton("����");				//�½���ť
     lastmonth=new JButton("����");
     add(lastmonth);								//��Ӱ�ť
     add(showMonth);
     add(nextmonth);
     lastmonth.addActionListener(this);
     nextmonth.addActionListener(this);
     showMonth.setText(""+month);
  }
 
 public void setMonth(int month)
  {  
	 if(month<=12&&month>=1)
      {  
	  this.month=month;
      }
    else
      {  
    	this.month=1;
      }
    showMonth.setText(""+month);
  }
 
 public int getMonth()
  {   
	 return month;
  } 
 
 /*��ʼ��actionPerformed����
  *��õ����ť����
  *������Ӧ�·ݸı䲢���������ʾ
  * ���� Javadoc��
  * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
  */
 public void actionPerformed(ActionEvent e)
  {  
	 if(e.getSource()==lastmonth)
      {  
		 if(month>=2)
         {  
			 month=month-1;
            calendar_3.setMonth(month);
            calendar_3.set_calendar(calendar_3.getYear(),month);
         }
        else 
        	if(month==1)
         {  
        		month=12;
            calendar_3.setMonth(month);
            calendar_3.set_calendar(calendar_3.getYear(),month);
         }
       showMonth.setText(""+month);
      }
    else if(e.getSource()==nextmonth)
      {  if(month<12)
          {  month=month+1;
            calendar_3.setMonth(month);
            calendar_3.set_calendar(calendar_3.getYear(),month);
          }
        else if(month==12)
          {  month=1;
            calendar_3.setMonth(month);
            calendar_3.set_calendar(calendar_3.getYear(),month);
          }
        showMonth.setText(""+month);
      }
  }   
}
 
 //�����ǳ����main������
 public static void main(String args[])
   {   
	 Calendar calendar=Calendar.getInstance();    
     int y=calendar.get(Calendar.YEAR);           
     int m=calendar.get(Calendar.MONTH)+1;        
     int d=calendar.get(Calendar.DAY_OF_MONTH);
     CalendarPad  calendarpad= new CalendarPad(y,m,d);
   }
}



